package com.kaanf.auth.presentation.email_verification.loading

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.auth.presentation.email_verification.component.icon.VerificationLoadingIcon
import com.kaanf.auth.presentation.email_verification.component.progress.VerificationProgressBar
import com.kaanf.core.designsystem.component.layout.CustomSnackbarVariant
import com.kaanf.core.designsystem.component.layout.SnackbarScaffold
import com.kaanf.core.designsystem.component.layout.showSnackbar
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessFooterTextStyle
import com.kaanf.core.designsystem.theme.AccessLabelTextStyle
import com.kaanf.core.designsystem.theme.AccessTitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.presentation.util.ObserveAsEvents
import com.kaanf.core.presentation.util.UIText
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_footer_primary
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_progress
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_signature_failed
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_signature_pending
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_signature_verified
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_subtitle
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_target_label
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_loading_title
import detective_ai_stories.feature.auth.presentation.generated.resources.email_signal_failed_title
import kotlin.math.roundToInt
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationLoadingRoot(
    viewModel: EmailVerificationLoadingViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is EmailVerificationLoadingEvent.Message -> {
                snackbarHostState.showSnackbar(
                    message = event.message.asStringAsync(),
                    variant = CustomSnackbarVariant.Failure,
                    title = UIText.Resource(Res.string.email_signal_failed_title).asStringAsync(),
                )
            }
        }
    }

    SnackbarScaffold(snackbarHostState = snackbarHostState) { innerPadding ->
        EmailVerificationLoadingScreen(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .consumeWindowInsets(innerPadding),
            state = state,
        )
    }
}

@Composable
fun EmailVerificationLoadingScreen(
    modifier: Modifier = Modifier,
    state: EmailVerificationLoadingState,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = state.progress,
        animationSpec = tween(durationMillis = 220, easing = LinearOutSlowInEasing),
        label = "emailVerificationProgress",
    )

    val accentColor = getAccessColor(state.phase)

    val signalText =
        when (state.phase) {
            EmailVerificationLoadingPhase.Verifying -> stringResource(Res.string.email_signal_loading_signature_pending)
            EmailVerificationLoadingPhase.Verified -> stringResource(Res.string.email_signal_loading_signature_verified)
            EmailVerificationLoadingPhase.Failed -> stringResource(Res.string.email_signal_loading_signature_failed)
        }

    val progressText =
        if (animatedProgress >= 0.995f) {
            "100%"
        } else {
            "${(animatedProgress * 100).roundToInt()}%"
        }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .imePadding()
                .navigationBarsPadding()
                .padding(horizontal = 32.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 376.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(Res.string.email_signal_loading_title),
                    style =
                        AccessTitleTextStyle(
                            fontSizeSp = 24,
                            letterSpacingSp = 4.8,
                        ).copy(
                            lineHeight = 32.sp,
                        ),
                )

                Text(
                    text = stringResource(Res.string.email_signal_loading_subtitle),
                    style =
                        AccessLabelTextStyle().copy(
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            letterSpacing = 1.sp,
                            color = AccessDefaults.SupportingText,
                        ),
                )
            }

            Spacer(modifier = Modifier.height(72.dp))

            VerificationLoadingIcon(
                modifier = Modifier.size(160.dp),
                phase = state.phase,
                accentColor = accentColor,
            )

            Spacer(modifier = Modifier.height(58.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = stringResource(Res.string.email_signal_loading_target_label),
                    style =
                        AccessLabelTextStyle().copy(
                            fontSize = 9.sp,
                            lineHeight = 13.5.sp,
                            letterSpacing = 2.7.sp,
                            color = AccessDefaults.FooterText,
                        ),
                )

                Text(
                    text = signalText,
                    style =
                        AccessLabelTextStyle().copy(
                            fontSize = 18.sp,
                            lineHeight = 28.sp,
                            letterSpacing = 3.6.sp,
                            color = getColorFromPhase(state.phase, accentColor),
                            shadow =
                                Shadow(
                                    color = accentColor.copy(alpha = 0.5f),
                                    blurRadius = if (state.phase == EmailVerificationLoadingPhase.Verified) 18f else 10f,
                                ),
                        ),
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(Res.string.email_signal_loading_progress),
                        modifier = Modifier.align(Alignment.CenterStart),
                        style =
                            AccessLabelTextStyle().copy(
                                fontSize = 10.sp,
                                lineHeight = 15.sp,
                                letterSpacing = 1.sp,
                                color = AccessDefaults.FooterText,
                                textAlign = TextAlign.Start,
                            ),
                    )

                    Text(
                        text = progressText,
                        modifier = Modifier.align(Alignment.CenterEnd),
                        style =
                            AccessLabelTextStyle().copy(
                                fontSize = 10.sp,
                                lineHeight = 15.sp,
                                letterSpacing = 1.sp,
                                color =
                                    if (state.phase == EmailVerificationLoadingPhase.Verifying) {
                                        AccessDefaults.HeadingColor
                                    } else {
                                        accentColor
                                    },
                                textAlign = TextAlign.End,
                            ),
                    )
                }

                VerificationProgressBar(
                    progress = animatedProgress,
                    accentColor = accentColor,
                    isLoading = state.phase == EmailVerificationLoadingPhase.Verifying,
                )
            }
        }

        Column(
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(Res.string.email_signal_loading_footer_primary),
                style =
                    AccessFooterTextStyle().copy(
                        fontSize = 10.sp,
                        color = AccessDefaults.FooterText,
                    ),
            )
        }
    }
}

private fun getAccessColor(phase: EmailVerificationLoadingPhase): Color {
    return when (phase) {
        EmailVerificationLoadingPhase.Verifying -> AccessDefaults.AlertLine
        EmailVerificationLoadingPhase.Verified -> AccessDefaults.SuccessLine
        EmailVerificationLoadingPhase.Failed -> AccessDefaults.AlertLine
    }
}

private fun getColorFromPhase(
    phase: EmailVerificationLoadingPhase,
    accentColor: Color
): Color {
    return when (phase) {
        EmailVerificationLoadingPhase.Verifying -> AccessDefaults.HeadingColor
        EmailVerificationLoadingPhase.Verified -> accentColor
        EmailVerificationLoadingPhase.Failed -> accentColor
    }
}

@Preview
@Composable
private fun EmailVerificationLoadingScreenPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        EmailVerificationLoadingScreen(
            state = EmailVerificationLoadingState(progress = 0.33f),
        )
    }
}

@Preview
@Composable
private fun EmailVerificationLoadingScreenVerifiedPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        EmailVerificationLoadingScreen(
            state =
                EmailVerificationLoadingState(
                    phase = EmailVerificationLoadingPhase.Verified,
                    progress = 1f,
                ),
        )
    }
}
