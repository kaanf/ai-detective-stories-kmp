package com.kaanf.home.presentation.dashboard.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.Inter
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_code_204
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_empty_description
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_empty_status_label
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_empty_status_value
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_empty_title
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_live_feed
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardEmptyCard() {
    Box(
        modifier = Modifier
            .drawCornerBrackets()
            .padding(all = 32.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {/*
            Icon(
                painter = painterResource(AccessIcons.Inbox),
                contentDescription = null,
                tint = AccessDefaults.AlertLine,
                modifier = Modifier.size(48.dp),
            )
            */

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .alpha(0.72f)
                        .background(
                            color = AccessDefaults.AlertLine,
                            shape = CircleShape,
                        ),
                )

                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = stringResource(Res.string.dashboard_code_204),
                    style = AccessSubtitleTextStyle().copy(
                        fontSize = 14.sp,
                        letterSpacing = 1.sp,
                        color = AccessDefaults.AlertLine,
                    ),
                )

                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .alpha(0.72f)
                        .background(
                            color = AccessDefaults.AlertLine,
                            shape = CircleShape,
                        ),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(Res.string.dashboard_empty_title),
                style = AccessMetaTextStyle().copy(
                    fontSize = 17.sp,
                    color = AccessDefaults.HeadingColor
                ),
            )

            Text(
                text = stringResource(Res.string.dashboard_empty_description),
                style = AccessMetaTextStyle().copy(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    color = AccessDefaults.SupportingText,
                    lineHeight = 18.sp
                )
            )
        }
    }
}
private fun Modifier.drawCornerBrackets(): Modifier = drawBehind {
    val bracketLen = 16.dp.toPx()
    val strokeWidth = 0.63.dp.toPx()
    val offset = 8.dp.toPx()
    val color = AccessDefaults.FooterText

    drawLine(color, Offset(offset, offset), Offset(offset + bracketLen, offset), strokeWidth)
    drawLine(color, Offset(offset, offset), Offset(offset, offset + bracketLen), strokeWidth)

    drawLine(color, Offset(size.width - offset, offset), Offset(size.width - offset - bracketLen, offset), strokeWidth)
    drawLine(color, Offset(size.width - offset, offset), Offset(size.width - offset, offset + bracketLen), strokeWidth)

    drawLine(color, Offset(offset, size.height - offset), Offset(offset + bracketLen, size.height - offset), strokeWidth)
    drawLine(color, Offset(offset, size.height - offset), Offset(offset, size.height - offset - bracketLen), strokeWidth)

    drawLine(color, Offset(size.width - offset, size.height - offset), Offset(size.width - offset - bracketLen, size.height - offset), strokeWidth)
    drawLine(color, Offset(size.width - offset, size.height - offset), Offset(size.width - offset, size.height - offset - bracketLen), strokeWidth)
}

@Preview
@Composable
private fun DashboardEmptyCardPreview() {
    DetectiveAiStoriesTheme {
        DashboardEmptyCard()
    }
}
