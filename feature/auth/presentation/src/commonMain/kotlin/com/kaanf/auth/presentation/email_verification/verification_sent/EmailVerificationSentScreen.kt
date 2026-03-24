package com.kaanf.auth.presentation.email_verification.verification_sent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.auth.presentation.email_verification.component.icon.VerificationMailIcon
import com.kaanf.auth.presentation.email_verification.layout.SimpleActivationLayout
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.CustomSnackbarVariant
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.layout.showSnackbar
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.util.ObserveAsEvents
import com.kaanf.core.presentation.util.UIText
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_primary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_secondary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_title
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_resend
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_resending
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationSentRoot(
    viewModel: EmailVerificationSentViewModel = koinViewModel(),
    onLoginClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is EmailVerificationSentEvent.Message -> {
                snackbarHostState.showSnackbar(
                    message = event.message.asStringAsync(),
                    variant = CustomSnackbarVariant.Success,
                    title = UIText.Resource(Res.string.email_signal_dispatched_title).asStringAsync(),
                )
            }

            EmailVerificationSentEvent.NavigateToTerminal -> {}
        }
    }

    SnackbarScaffold(snackbarHostState) { innerPadding ->
        EmailVerificationSentScreen(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
            state = state,
            onAction = viewModel::onAction,
        )
    }
}

@Composable
fun EmailVerificationSentScreen(
    modifier: Modifier = Modifier,
    state: EmailVerificationSentState,
    onAction: (EmailVerificationSentAction) -> Unit,
) {
        SimpleActivationLayout(
            title = stringResource(Res.string.email_signal_dispatched_title),
            signalColor = AccessDefaults.AlertLine,
            signalTitle = stringResource(
                Res.string.email_signal_dispatched_primary,
                state.registeredEmail,
            ),
            signalDescription = stringResource(Res.string.email_signal_dispatched_secondary),
            icon = {
                VerificationMailIcon()
            },
            onTextClick = {

            },
            button = {
                BaseButton(
                    text = stringResource(Res.string.email_signal_resend),
                    loadingText = stringResource(Res.string.email_signal_resending),
                    onClick = {
                        onAction(EmailVerificationSentAction.OnResendSignalClick)
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                    isLoading = state.isResending,
                    enabled = state.isResendEnabled,
                )
            },
        )
}

@Preview
@Composable
private fun EmailVerificationSentScreenPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        EmailVerificationSentScreen(
            modifier = Modifier.background(AccessDefaults.PanelBackground),
            state = EmailVerificationSentState(),
            onAction = {},
        )
    }
}
