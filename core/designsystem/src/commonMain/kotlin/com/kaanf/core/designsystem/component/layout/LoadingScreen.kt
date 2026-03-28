package com.kaanf.core.designsystem.component.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.component.loading.DualRingLoadingIndicator
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessLabelTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import detective_ai_stories.core.designsystem.generated.resources.Res
import detective_ai_stories.core.designsystem.generated.resources.go_back
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoadingScreen(
    text: String,
    modifier: Modifier = Modifier,
    supportingText: String,
    onBackClick: (() -> Unit)? = null,
    outerSweepColor: Color = AccessDefaults.AlertLine,
) {
    Box(
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 24.dp),
    ) {
        if (onBackClick != null) {
            Row(
                modifier =
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .clickable(onClick = onBackClick),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.go_back),
                    modifier = Modifier.size(16.dp),
                    tint = AccessDefaults.FooterText,
                )

                Text(
                    text = stringResource(Res.string.go_back),
                    style =
                        AccessLabelTextStyle().copy(
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            letterSpacing = 1.sp,
                            color = AccessDefaults.FooterText,
                            textAlign = TextAlign.Start,
                        ),
                )
            }
        }

        Column(
            modifier =
                Modifier
                    .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            DualRingLoadingIndicator(
                modifier = Modifier.size(64.dp),
                outerSweepColor = outerSweepColor,
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = text,
                    style =
                        AccessLabelTextStyle().copy(
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                            letterSpacing = 3.6.sp,
                            color = AccessDefaults.HeadingColor,
                        ),
                )

                Text(
                    text = supportingText,
                    style =
                        AccessLabelTextStyle().copy(
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            letterSpacing = 0.9.sp,
                            color = AccessDefaults.FooterText.copy(alpha = 0.82f),
                            textAlign = TextAlign.Center,
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    DetectiveAiStoriesTheme {
        LoadingScreen(
            text = "Yükleniyor",
            supportingText = "Lütfen bekleyiniz.",
        )
    }
}
