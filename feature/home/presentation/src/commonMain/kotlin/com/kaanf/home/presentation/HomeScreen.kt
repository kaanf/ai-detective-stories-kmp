package com.kaanf.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kaanf.core.designsystem.component.layout.LoadingOverlayLayout
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.Inter
import com.kaanf.home.presentation.component.UserTopPanel
import com.kaanf.home.presentation.dashboard.DashboardRoot
import com.kaanf.home.presentation.dashboard.component.DashboardHeader
import com.kaanf.home.presentation.dispatch.DispatchRoot
import com.kaanf.home.presentation.navigation.BarRoute
import com.kaanf.home.presentation.navigation.BottomNavBar
import com.kaanf.home.presentation.navigation.BottomNavTab
import com.kaanf.home.presentation.navigation.DashboardRoute
import com.kaanf.home.presentation.navigation.InformantsRoute
import com.kaanf.home.presentation.navigation.ProfileRoute
import com.kaanf.home.presentation.navigation.SecretaryRoute
import com.kaanf.home.presentation.pub.PubRoot
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object HomeScreenRoute

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
) {
    val homeNavController = rememberNavController()
    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry?.destination?.route
    val currentTab = BottomNavTab.entries.firstOrNull { tab ->
        tab.route::class.qualifiedName == currentRoute
    } ?: BottomNavTab.Dashboard

    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsStateWithLifecycle()

    SnackbarScaffold(snackbarHostState = snackbarHostState) { innerPadding ->
        LoadingOverlayLayout(
            isLoading = isLoading,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
            ) {
                state.user?.let { user ->
                    UserTopPanel(user = user)
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    NavHost(
                        navController = homeNavController,
                        startDestination = DashboardRoute,
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        composable<DashboardRoute> {
                            DashboardRoot(
                                onLoadingChanged = { isLoading = it },
                            )
                        }
                        composable<InformantsRoute> {
                            PlaceholderScreen("MURDERS")
                        }
                        composable<SecretaryRoute> {
                            DispatchRoot(
                                onLoadingChanged = { isLoading = it },
                            )
                        }
                        composable<BarRoute> {
                            PubRoot(
                                onLoadingChanged = { isLoading = it },
                            )
                        }
                        composable<ProfileRoute> {
                            PlaceholderScreen("PROFILE")
                        }
                    }
                }

                BottomNavBar(
                    currentTab = currentTab,
                    onTabSelected = { tab ->
                        homeNavController.navigate(tab.route) {
                            popUpTo(homeNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun PlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AccessDefaults.PanelAlternativeBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = AccessDefaults.FooterText,
                letterSpacing = 2.sp,
            ),
        )
    }
}
