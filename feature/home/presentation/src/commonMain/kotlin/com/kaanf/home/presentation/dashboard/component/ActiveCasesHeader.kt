package com.kaanf.home.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_active_cases
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_live_feed
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ActiveCasesHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(Res.string.dashboard_active_cases),
            style = AccessHeaderTextStyle().copy(
                fontSize = 18.sp,
                letterSpacing = 2.4.sp,
            ),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .alpha(0.72f)
                    .background(
                        color = AccessDefaults.AlertLine,
                        shape = CircleShape,
                    ),
            )

            Text(
                text = stringResource(Res.string.dashboard_live_feed),
                style = AccessSubtitleTextStyle().copy(
                    fontSize = 12.sp,
                    letterSpacing = 1.sp,
                    color = AccessDefaults.AlertLine,
                ),
            )
        }
    }
}

@Preview
@Composable
fun ActiveCasesHeaderPreview() {
    DetectiveAiStoriesTheme {
        ActiveCasesHeader()
    }
}

