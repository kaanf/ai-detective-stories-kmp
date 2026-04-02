package com.kaanf.home.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.domain.model.user.User
import com.kaanf.home.presentation.dashboard.component.DashboardCaseMain
import com.kaanf.home.presentation.dashboard.component.DashboardEmptyCard
import com.kaanf.home.presentation.dashboard.component.DashboardHeader
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardRoot(
    viewModel: DashboardViewModel = koinViewModel(),
    onLoadingChanged: (Boolean) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoading) {
        onLoadingChanged(state.isLoading)
    }

    DashboardScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AccessDefaults.PanelAlternativeBackground)
    ) {
        state.user?.let { user ->
            DashboardHeader(user = user)
        }

        if (state.cases.isNotEmpty()) {
            DashboardCaseMain(state.cases)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                contentAlignment = Alignment.Center,
            ) {
                DashboardEmptyCard()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DetectiveAiStoriesTheme {
        DashboardScreen(
            state = DashboardState(
                user = User(
                    id = "1",
                    email = "test@test.com",
                    fullName = "RENO, V.",
                    profileImageUrl = "",
                    gold = 0,
                    energy = 100,
                ),
                cases = listOf(

                ),
            ),
            onAction = {},
        )
    }
}
