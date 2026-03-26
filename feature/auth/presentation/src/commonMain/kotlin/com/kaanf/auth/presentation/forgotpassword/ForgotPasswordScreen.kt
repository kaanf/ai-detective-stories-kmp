package com.kaanf.auth.presentation.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaanf.auth.presentation.emailverification.component.icon.ForgotPasswordLockIcon
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.textfield.BaseTextField
import com.kaanf.core.designsystem.theme.AccessLabelTextStyle
import com.kaanf.core.designsystem.theme.AccessTitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.util.ObserveAsEvents
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.lost_credentials
import detective_ai_stories.feature.auth.presentation.generated.resources.lost_credentials_description
import detective_ai_stories.feature.auth.presentation.generated.resources.lost_credentials_send_resent_link
import detective_ai_stories.feature.auth.presentation.generated.resources.register_email_placeholder
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ForgotPasswordRoot(viewModel: ForgotPasswordViewModel = viewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            else -> Unit
        }
    }

    SnackbarScaffold(snackbarHostState = snackbarHostState) { innerPadding ->
        ForgotPasswordScreen(
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
fun ForgotPasswordScreen(
    modifier: Modifier,
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit,
) {
    Column(
        modifier =
            modifier
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

        ForgotPasswordLockIcon()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(Res.string.lost_credentials),
                style =
                    AccessTitleTextStyle(
                        fontSizeSp = 20,
                        letterSpacingSp = 4.2,
                    ),
            )

            Text(
                text = stringResource(Res.string.lost_credentials_description),
                style = AccessLabelTextStyle(),
            )
        }

        Spacer(
            modifier = Modifier.weight(1f),
        )

        BaseTextField(
            state = state.emailTextState,
            placeholder = stringResource(Res.string.register_email_placeholder),
        )

        BaseButton(
            text = stringResource(Res.string.lost_credentials_send_resent_link),
            onClick = {
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
            isLoading = state.isLoading,
        )
    }
}

@Preview
@Composable
private fun ForgotPasswordScreenPreview() {
    DetectiveAiStoriesTheme {
        ForgotPasswordScreen(
            modifier = Modifier,
            state = ForgotPasswordState(),
            onAction = {},
        )
    }
}
