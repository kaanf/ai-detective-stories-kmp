package com.kaanf.auth.presentation.email_verification.verification_result

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaanf.auth.presentation.email_verification.component.icon.VerificationMailIcon
import com.kaanf.auth.presentation.email_verification.component.icon.VerificationSuccessIcon
import com.kaanf.auth.presentation.email_verification.layout.SimpleActivationLayout
import com.kaanf.auth.presentation.email_verification.verification_sent.EmailVerificationSentAction
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.util.ObserveAsEvents
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_primary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_secondary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_title
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_resend
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_resending
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_create_persona
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_primary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_secondary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmailVerificationResultRoot(
    viewModel: EmailVerificationResultViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            else -> {}
        }
    }

    SnackbarScaffold(snackbarHostState) { innerPading ->
        EmailVerificationResultScreen(
            modifier = Modifier
                .padding(innerPading)
                .consumeWindowInsets(innerPading),
            state = state,
            onAction = viewModel::onAction,
        )
    }
}

@Composable
fun EmailVerificationResultScreen(
    modifier: Modifier,
    state: EmailVerificationResultState,
    onAction: (EmailVerificationResultAction) -> Unit,
) {
    SimpleActivationLayout(
        modifier = modifier,
        title = stringResource(Res.string.email_signal_verified_title),
        signalColor = AccessDefaults.SuccessLine,
        signalTitle = stringResource(Res.string.email_signal_verified_primary),
        signalDescription = stringResource(Res.string.email_signal_verified_secondary),
        icon = {
            VerificationSuccessIcon()
        },
        button = {
            BaseButton(
                text = stringResource(Res.string.email_signal_verified_create_persona),
                onClick = {

                },
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
            )
        },
    )
}

@Preview
@Composable
private fun Preview() {
    DetectiveAiStoriesTheme {
        EmailVerificationResultRoot()
    }
}
