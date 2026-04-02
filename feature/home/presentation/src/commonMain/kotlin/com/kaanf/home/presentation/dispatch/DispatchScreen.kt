package com.kaanf.home.presentation.dispatch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.domain.model.user.User
import com.kaanf.home.domain.model.Bounty
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.CaseDifficulty
import com.kaanf.home.domain.model.CaseStatus
import com.kaanf.home.domain.model.Cost
import com.kaanf.home.domain.model.TemporaryCase
import com.kaanf.home.presentation.dashboard.DashboardScreen
import com.kaanf.home.presentation.dashboard.DashboardState
import com.kaanf.home.presentation.dispatch.component.DispatchCaseCard
import com.kaanf.home.presentation.dispatch.component.DispatchCountdownTimer
import com.kaanf.home.presentation.dispatch.component.DispatchHeader
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DispatchRoot(
    viewModel: DispatchViewModel = koinViewModel(),
    onLoadingChanged: (Boolean) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    androidx.compose.runtime.LaunchedEffect(state.isLoading) {
        onLoadingChanged(state.isLoading)
    }

    DispatchScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun DispatchScreen(
    state: DispatchState,
    onAction: (DispatchAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AccessDefaults.LoadingScreenBackground),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            item {
                DispatchHeader()
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    DispatchCountdownTimer(
                        remainingSeconds = state.remainingSeconds,
                    )
                }
            }

            items(
                items = state.cases,
                key = { it.id },
            ) { case ->
                DispatchCaseCard(
                    case = case,
                    onPickCase = {
                        onAction(DispatchAction.OnPickCase(case.id))
                    },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    DetectiveAiStoriesTheme {
        DispatchScreen(
            state = DispatchState(
                cases = listOf(
                    TemporaryCase(
                        id = "1",
                        title = "Gasp ve Darb",
                        difficulty = CaseDifficulty.MEDIUM,
                        type = "Sanayi Bölgesi",
                        status = CaseStatus.OPEN,
                        cost = Cost(energy = 20),
                        bounty = Bounty(gold = 113.0),
                    ),
                    TemporaryCase(
                        id = "2",
                        title = "Kayıp Şahıs",
                        difficulty = CaseDifficulty.HARD,
                        type = "Çürük Diş Sokağı",
                        status = CaseStatus.OPEN,
                        cost = Cost(energy = 25),
                        bounty = Bounty(gold = 119.0),
                    ),
                    TemporaryCase(
                        id = "3",
                        title = "Cinayet",
                        difficulty = CaseDifficulty.HARD,
                        type = "Doğu Rıhtımı",
                        status = CaseStatus.OPEN,
                        cost = Cost(energy = 30),
                        bounty = Bounty(gold = 99.0),
                    ),
                ),
                remainingSeconds = 1376,
            ),
            onAction = {},
        )
    }
}
