package com.kaanf.auth.presentation.emailverification.verificationresult

import androidx.compose.foundation.layout.consumeWindowInsets
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
import com.kaanf.auth.presentation.emailverification.component.icon.VerificationFailedIcon
import com.kaanf.auth.presentation.emailverification.component.icon.VerificationSuccessIcon
import com.kaanf.auth.presentation.emailverification.layout.SimpleActivationLayout
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.LoadingOverlayLayout
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.layout.showSnackbar
import com.kaanf.core.presentation.base.BaseEvent
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.presentation.util.ObserveAsEvents
import com.kaanf.core.presentation.util.TestTags
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_failed_message_line_1
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_failed_message_line_2
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_failed_primary_action_request_new_link
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_failed_title
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_success_message_line_1
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_success_message_line_2
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_success_primary_action_return_to_login
import detective_ai_stories.feature.auth.presentation.generated.resources.verification_success_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationResultRoot(
    viewModel: EmailVerificationResultViewModel = koinViewModel(),
    onLoginClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is BaseEvent.ShowSnackbar -> {
                snackbarHostState.showSnackbar(event.snackbarMessage)
            }

            EmailVerificationResultEvent.NavigateToResult -> {
                onLoginClick.invoke()
            }
        }
    }

    SnackbarScaffold(snackbarHostState = snackbarHostState) { innerPadding ->
        when (state.phase) {
            EmailVerificationPhase.Verifying -> {
                LoadingOverlayLayout(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    isLoading = true,
                ) {}
            }

            EmailVerificationPhase.Failed -> {
                SimpleActivationLayout(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    title = stringResource(Res.string.verification_failed_title),
                    panelColor = AccessDefaults.AlertLine,
                    panelTitle = stringResource(Res.string.verification_failed_message_line_1),
                    panelDescription = stringResource(Res.string.verification_failed_message_line_2),
                    icon = {
                        VerificationFailedIcon()
                    },
                    button = {
                        BaseButton(
                            text = stringResource(Res.string.verification_failed_primary_action_request_new_link),
                            onClick = {},
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                        )
                    },
                    onTextClick = {
                        viewModel.onAction(EmailVerificationResultAction.OnReturnToLoginClicked)
                    },
                )
            }

            EmailVerificationPhase.Verified -> {
                SimpleActivationLayout(
                    modifier =
                        Modifier
                            .testTag(TestTags.VERIFICATION_RESULT_VERIFIED_SCREEN)
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    title = stringResource(Res.string.verification_success_title),
                    panelColor = AccessDefaults.SuccessLine,
                    panelTitle = stringResource(Res.string.verification_success_message_line_1),
                    panelDescription = stringResource(Res.string.verification_success_message_line_2),
                    icon = {
                        VerificationSuccessIcon()
                    },
                    button = {
                        BaseButton(
                            text = stringResource(Res.string.verification_success_primary_action_return_to_login),
                            onClick = {
                                viewModel.onAction(EmailVerificationResultAction.OnReturnToLoginClicked)
                            },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .testTag(TestTags.VERIFICATION_RESULT_CONTINUE)
                                    .padding(top = 24.dp),
                        )
                    },
                )
            }
        }
    }
}
