package com.kaanf.auth.presentation.register

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
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.textfield.BasePasswordTextField
import com.kaanf.core.designsystem.component.textfield.BaseTextField
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessFooterTextStyle
import com.kaanf.core.designsystem.theme.AccessLabelTextStyle
import com.kaanf.core.designsystem.theme.AccessTitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.util.ObserveAsEvents
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.register_begin_first_case
import detective_ai_stories.feature.auth.presentation.generated.resources.register_email_placeholder
import detective_ai_stories.feature.auth.presentation.generated.resources.register_password_placeholder
import detective_ai_stories.feature.auth.presentation.generated.resources.register_quote
import detective_ai_stories.feature.auth.presentation.generated.resources.register_return_to_login
import detective_ai_stories.feature.auth.presentation.generated.resources.register_retype_password_placeholder
import detective_ai_stories.feature.auth.presentation.generated.resources.register_screen_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onRegisterSuccess: (String) -> Unit,
    onLoginClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Success -> {
                onRegisterSuccess(event.email)
            }

            is RegisterEvent.UsernameValidationSuccess -> {
            }

            is RegisterEvent.MailValidationSuccess -> {
            }

            is RegisterEvent.UsernameValidationFailure -> {
                snackbarHostState.showSnackbar(
                    event.message.asStringAsync(),
                )
            }

            is RegisterEvent.MailValidationFailure -> {
                snackbarHostState.showSnackbar(
                    event.message.asStringAsync(),
                )
            }

            is RegisterEvent.Message -> {
                snackbarHostState.showSnackbar(
                    event.message.asStringAsync(),
                )
            }
        }
    }

    SnackbarScaffold(snackbarHostState) { innerPadding ->
        RegisterScreen(
            state = state,
            modifier =
                Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
            onAction = viewModel::onAction,
            onLoginClick = onLoginClick,
        )
    }
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 24.dp)
            .imePadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(
            modifier = Modifier.weight(0.4f),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(Res.string.register_screen_title),
                style = AccessTitleTextStyle(
                    fontSizeSp = 20,
                    letterSpacingSp = 4.2,
                ),
            )

            Text(
                text = stringResource(Res.string.register_quote),
                style = AccessLabelTextStyle(),
            )
        }

        Spacer(
            modifier = Modifier.weight(1f),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            BaseTextField(
                state = state.emailTextState,
                placeholder = stringResource(Res.string.register_email_placeholder),
            )

            BasePasswordTextField(
                state = state.passwordTextState,
                placeholder = stringResource(Res.string.register_password_placeholder),
            )

            BasePasswordTextField(
                state = state.rePasswordTextState,
                placeholder = stringResource(Res.string.register_retype_password_placeholder),
            )
        }

        BaseButton(
            text = stringResource(Res.string.register_begin_first_case),
            onClick = {
                onAction(RegisterAction.OnRegisterClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp),
            isLoading = state.isRegistering,
        )

        Text(
            text = stringResource(Res.string.register_return_to_login),
            modifier = Modifier
                .padding(top = 28.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onLoginClick,
                ),
            style = AccessFooterTextStyle(),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        RegisterScreen(
            modifier = Modifier.background(
                AccessDefaults.PanelBackground
            ),
            state = RegisterState(
                usernameTextState = TextFieldState("John Doe"),
                emailTextState = TextFieldState("detective@agency.io"),
                passwordTextState = TextFieldState("AccessKey9"),
            ),
            onAction = {},
            onLoginClick = {},
        )
    }
}
