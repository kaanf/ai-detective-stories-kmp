package com.kaanf.auth.presentation.emailverification.verificationresult

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
import com.kaanf.auth.presentation.emailverification.component.icon.VerificationFailedIcon
import com.kaanf.auth.presentation.emailverification.component.icon.VerificationSuccessIcon
import com.kaanf.auth.presentation.emailverification.layout.SimpleActivationLayout
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.CustomSnackbarVariant
import com.kaanf.core.designsystem.component.layout.LoadingScreen
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.layout.showSnackbar
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.presentation.util.ObserveAsEvents
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.do_not_close_this_window
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_failed_primary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_failed_secondary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_failed_title
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_resend
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_create_persona
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_primary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_secondary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_verified_title
import detective_ai_stories.feature.auth.presentation.generated.resources.verifying_account
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
            is EmailVerificationResultEvent.Message -> {
                snackbarHostState.showSnackbar(
                    message = event.message.asStringAsync(),
                    variant =
                        if (state.phase == EmailVerificationPhase.Verified) {
                            CustomSnackbarVariant.Success
                        } else {
                            CustomSnackbarVariant.Failure
                        },
                )
            }

            EmailVerificationResultEvent.NavigateToResult -> {
                onLoginClick.invoke()
            }
        }
    }

    SnackbarScaffold(snackbarHostState = snackbarHostState) { innerPadding ->
        when (state.phase) {
            EmailVerificationPhase.Verifying -> {
                LoadingScreen(
                    text = stringResource(Res.string.verifying_account),
                    supportingText = stringResource(Res.string.do_not_close_this_window),
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                )
            }

            EmailVerificationPhase.Failed -> {
                SimpleActivationLayout(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    title = stringResource(Res.string.email_signal_failed_title),
                    panelColor = AccessDefaults.AlertLine,
                    panelTitle = stringResource(Res.string.email_signal_failed_primary),
                    panelDescription = stringResource(Res.string.email_signal_failed_secondary),
                    icon = {
                        VerificationFailedIcon()
                    },
                    button = {
                        BaseButton(
                            text = stringResource(Res.string.email_signal_resend),
                            onClick = {
                            },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                        )
                    },
                    onTextClick = {
                        onLoginClick.invoke()
                    },
                )
            }

            EmailVerificationPhase.Verified -> {
                SimpleActivationLayout(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    title = stringResource(Res.string.email_signal_verified_title),
                    panelColor = AccessDefaults.SuccessLine,
                    panelTitle = stringResource(Res.string.email_signal_verified_primary),
                    panelDescription = stringResource(Res.string.email_signal_verified_secondary),
                    icon = {
                        VerificationSuccessIcon()
                    },
                    button = {
                        BaseButton(
                            text = stringResource(Res.string.email_signal_verified_create_persona),
                            onClick = {
                                onLoginClick.invoke()
                            },
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                        )
                    },
                )
            }
        }
    }
}
