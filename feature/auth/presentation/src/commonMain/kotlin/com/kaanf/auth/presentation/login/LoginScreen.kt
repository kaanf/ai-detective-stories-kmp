package com.kaanf.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.component.brand.SimpleBrandLogo
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.CustomSnackbarVariant
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.layout.showSnackbar
import com.kaanf.core.designsystem.component.textfield.BasePasswordTextField
import com.kaanf.core.designsystem.component.textfield.BaseTextField
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessFooterTextStyle
import com.kaanf.core.designsystem.theme.AccessLabelTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.login_badge_placeholder
import detective_ai_stories.feature.auth.presentation.generated.resources.login_create_archive_record
import detective_ai_stories.feature.auth.presentation.generated.resources.login_enter_system
import detective_ai_stories.feature.auth.presentation.generated.resources.login_lost_credentials
import detective_ai_stories.feature.auth.presentation.generated.resources.login_passcode_placeholder
import detective_ai_stories.feature.auth.presentation.generated.resources.login_warning
import detective_ai_stories.feature.auth.presentation.generated.resources.login_wordmark_subtitle
import detective_ai_stories.feature.auth.presentation.generated.resources.login_wordmark_title

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onRegisterClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.Failure -> {
                snackbarHostState.showSnackbar(
                    message = event.message.asStringAsync(),
                    variant = CustomSnackbarVariant.Failure,
                )
            }

            LoginEvent.Success -> {
                // Todo: Navigate dashboard.
            }

            LoginEvent.NavigateToRegister -> {
                onRegisterClick()
            }
        }
    }

    SnackbarScaffold(snackbarHostState = snackbarHostState) { innerPadding ->
        LoginScreen(
            state = state,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            onAction = viewModel::onAction,
        )
    }
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 18.dp)
            .imePadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier.weight(1f),
        )

        SimpleBrandLogo(
            title = stringResource(Res.string.login_wordmark_title),
            subtitle = stringResource(Res.string.login_wordmark_subtitle),
        )

        Spacer(
            modifier = Modifier.weight(1f),
        )

        Text(
            text = stringResource(Res.string.login_warning),
            style = AccessLabelTextStyle(),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            BaseTextField(
                state = state.emailTextState,
                placeholder = stringResource(Res.string.login_badge_placeholder),
                keyboardType = KeyboardType.Email
            )

            BasePasswordTextField(
                state = state.passwordTextState,
                placeholder = stringResource(Res.string.login_passcode_placeholder),
            )
        }

        BaseButton(
            text = stringResource(Res.string.login_enter_system),
            onClick = {
                focusManager.clearFocus()
                onAction(LoginAction.OnLoginClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            isLoading = state.isSubmitting,
            enabled = state.isPasswordValid
        )

        Text(
            text = stringResource(Res.string.login_lost_credentials),
            modifier = Modifier
                .padding(top = 24.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onAction(LoginAction.OnRegisterClick) },
                ),
            style = AccessFooterTextStyle().copy(
                color = AccessDefaults.FooterText,
                fontSize = 10.sp
            ),
            textAlign = TextAlign.Center,
        )

        Text(
            text = stringResource(Res.string.login_create_archive_record),
            modifier = Modifier
                .padding(top = 18.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onAction(LoginAction.OnRegisterClick) },
                ),
            style = AccessFooterTextStyle(),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        LoginScreen(
            modifier = Modifier
                .background(AccessDefaults.PanelBackground),
            state = LoginState(
                emailTextState = TextFieldState("2049-ALPHA"),
                passwordTextState = TextFieldState("classified"),
            ),
            onAction = {},
        )
    }
}
