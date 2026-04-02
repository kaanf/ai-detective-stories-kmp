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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.component.layout.LoadingOverlayLayout
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.Inter
import com.kaanf.home.presentation.dashboard.DashboardRoot
import com.kaanf.home.presentation.dispatch.DispatchRoot
import com.kaanf.home.presentation.navigation.BottomNavBar
import com.kaanf.home.presentation.navigation.BottomNavTab
import com.kaanf.home.presentation.pub.PubRoot
import kotlinx.serialization.Serializable

@Serializable
data object HomeScreenRoute

@Composable
fun HomeScreenRoot() {
    var currentTab by rememberSaveable { mutableStateOf(BottomNavTab.Dashboard) }
    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

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
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) {
                    when (currentTab) {
                        BottomNavTab.Dashboard -> DashboardRoot(
                            onLoadingChanged = { isLoading = it },
                        )
                        BottomNavTab.Informants -> PlaceholderScreen("MURDERS")
                        BottomNavTab.Secretary -> DispatchRoot(
                            onLoadingChanged = { isLoading = it },
                        )
                        BottomNavTab.Bar -> PubRoot(
                            onLoadingChanged = { isLoading = it },
                        )
                        BottomNavTab.Profile -> PlaceholderScreen("PROFILE")
                    }
                }

                BottomNavBar(
                    currentTab = currentTab,
                    onTabSelected = { currentTab = it },
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
