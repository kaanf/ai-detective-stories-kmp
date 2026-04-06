package com.kaanf.home.presentation.dispatch.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessDefaults.CardBackground
import com.kaanf.core.designsystem.theme.AccessDefaults.TimerBorder
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.dispatch_countdown_label
import detective_ai_stories.feature.home.presentation.generated.resources.ic_clock
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DispatchCountdownTimer(
    remainingSeconds: Int,
    modifier: Modifier = Modifier,
) {
    val hours = remainingSeconds / 3600
    val minutes = (remainingSeconds % 3600) / 60
    val seconds = remainingSeconds % 60
    val timeText = "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0.03f)
        )

        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_clock),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = AccessDefaults.FooterText,
                )

                Text(
                    modifier = Modifier
                        .padding(top = 2.dp),
                    text = stringResource(Res.string.dispatch_countdown_label),
                    style = AccessSubtitleTextStyle().copy(
                        fontSize = 10.sp,
                        color = AccessDefaults.FooterText,
                    )
                )
            }

            Text(
                text = timeText,
                style = AccessSubtitleTextStyle().copy(
                    fontSize = 30.sp,
                    letterSpacing = 6.sp,
                    color = AccessDefaults.AlertLine,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun DispatchCountdownTimerPreview() {
    DetectiveAiStoriesTheme {
        DispatchCountdownTimer(remainingSeconds = 1376)
    }
}

