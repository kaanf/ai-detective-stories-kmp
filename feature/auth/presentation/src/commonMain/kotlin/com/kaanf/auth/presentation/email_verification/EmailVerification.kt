package com.kaanf.auth.presentation.email_verification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EmailVerificationRoot(
    viewModel: EmailVerificationViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EmailVerificationScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun EmailVerificationScreen(
    state: EmailVerificationState,
    onAction: (EmailVerificationAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    DetectiveAiStoriesTheme {
        EmailVerificationScreen(
            state = EmailVerificationState(),
            onAction = {},
        )
    }
}
