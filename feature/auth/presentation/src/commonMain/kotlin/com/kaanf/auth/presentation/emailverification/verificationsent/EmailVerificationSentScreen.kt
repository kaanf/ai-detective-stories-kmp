package com.kaanf.auth.presentation.emailverification.verificationsent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.auth.presentation.emailverification.component.icon.VerificationMailIcon
import com.kaanf.auth.presentation.emailverification.layout.SimpleActivationLayout
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.layout.showSnackbar
import com.kaanf.core.presentation.base.BaseEvent
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.util.ObserveAsEvents
import com.kaanf.core.presentation.util.TestTags
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_mail_sent_message_line_1
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_mail_sent_message_line_2
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_mail_sent_primary_action_resend_signal
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_mail_sent_primary_action_resend_signal_with_countdown
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_mail_sent_status_resending
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_mail_sent_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationSentRoot(
    viewModel: EmailVerificationSentViewModel = koinViewModel(),
    onReturnToLoginClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is BaseEvent.ShowSnackbar -> {
                snackbarHostState.showSnackbar(event.snackbarMessage)
            }

            EmailVerificationSentEvent.NavigateToLogin -> {
                onReturnToLoginClick.invoke()
            }
        }
    }

    SnackbarScaffold(snackbarHostState) { innerPadding ->
        EmailVerificationSentScreen(
            modifier =
                Modifier
                    .fillMaxSize()
                    .testTag(TestTags.VERIFICATION_SENT_SCREEN)
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
        modifier = modifier,
        title = stringResource(Res.string.verification_mail_sent_title),
        panelColor = AccessDefaults.AlertLine,
        panelTitle =
            stringResource(
                Res.string.verification_mail_sent_message_line_1,
                state.registeredEmail,
            ),
        panelDescription = stringResource(Res.string.verification_mail_sent_message_line_2),
        icon = {
            VerificationMailIcon()
        },
        button = {
            val resendButtonText =
                if (state.isResendEnabled) {
                    stringResource(Res.string.verification_mail_sent_primary_action_resend_signal)
                } else {
                    stringResource(
                        Res.string.verification_mail_sent_primary_action_resend_signal_with_countdown,
                        state.resendCountdownSeconds,
                    )
                }

            BaseButton(
                text = resendButtonText,
                loadingText = stringResource(Res.string.verification_mail_sent_status_resending),
                onClick = {
                    onAction(EmailVerificationSentAction.OnResendSignalClicked)
                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                isLoading = state.isResending,
                enabled = state.isResendEnabled,
            )
        },
        onTextClick = {
            onAction(EmailVerificationSentAction.OnReturnToLoginClicked)
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
