package com.kaanf.auth.presentation.emailverification.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.auth.presentation.emailverification.component.VerificationInfoPanel
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessFooterTextStyle
import com.kaanf.core.designsystem.theme.AccessTitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_primary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_secondary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_dispatched_title
import detective_ai_stories.feature.auth.presentation.generated.resources.register_return_to_login
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SimpleActivationLayout(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    title: String,
    signalColor: Color,
    signalTitle: String,
    signalDescription: String,
    button: @Composable () -> Unit,
    onTextClick: (() -> Unit)? = null,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .imePadding()
                .navigationBarsPadding()
                .background(AccessDefaults.PanelBackground)
                .padding(all = 24.dp),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            icon()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Text(
                    text = title,
                    style =
                        AccessTitleTextStyle(
                            fontSizeSp = 20,
                            letterSpacingSp = 4.8,
                        ).copy(
                            lineHeight = 32.sp,
                        ),
                )

                VerificationInfoPanel(
                    title = signalTitle,
                    description = signalDescription,
                    color = signalColor,
                )
            }

            button()
        }

        if (onTextClick != null) {
            Text(
                text = stringResource(Res.string.register_return_to_login),
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .clickable {
                            onTextClick.invoke()
                        },
                style =
                    AccessFooterTextStyle().copy(
                        fontSize = 10.sp,
                        color = Color(0xFF555555),
                    ),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview
fun SimpleActivationLayoutPreview() {
    DetectiveAiStoriesTheme {
        SimpleActivationLayout(
            title = stringResource(Res.string.email_signal_dispatched_title),
            signalColor = AccessDefaults.AlertLine,
            signalTitle = stringResource(Res.string.email_signal_dispatched_primary),
            signalDescription = stringResource(Res.string.email_signal_dispatched_secondary),
            onTextClick = {},
            button = {},
            icon = {},
        )
    }
}
