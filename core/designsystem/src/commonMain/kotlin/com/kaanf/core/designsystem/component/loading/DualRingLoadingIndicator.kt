package com.kaanf.core.designsystem.component.loading

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import detective_ai_stories.core.designsystem.generated.resources.Res
import detective_ai_stories.core.designsystem.generated.resources.ic_lock
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DualRingLoadingIndicator(
    modifier: Modifier = Modifier,
    outerSweepColor: Color = AccessDefaults.AlertLine,
    outerTrackColor: Color = AccessDefaults.LoadingIndicatorOuterTrack,
) {
    val transition = rememberInfiniteTransition(label = "dualRingLoader")
    val outerRotation =
        transition.animateFloat(
            initialValue = -90f,
            targetValue = 270f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 1150, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart,
                ),
            label = "outerRotation",
        )

    Box(
        modifier = modifier.aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val canvasCenter = Offset(size.width / 2f, size.height / 2f)
                        val outerStroke = 1.05.dp.toPx()
                        val outerRadius = size.minDimension / 2f - outerStroke
                        val outerArcTopLeft = Offset(x = canvasCenter.x - outerRadius, y = canvasCenter.y - outerRadius)
                        val outerArcSize = Size(outerRadius * 2f, outerRadius * 2f)
                        val ambientRadius = size.minDimension * 0.72f

                        val ambientGlowBrush =
                            Brush.radialGradient(
                                colors =
                                    listOf(
                                        outerSweepColor.copy(alpha = 0.08f),
                                        Color.Transparent,
                                    ),
                                center = canvasCenter,
                                radius = ambientRadius,
                            )

                        val outerTrackStroke = Stroke(width = outerStroke)
                        val outerHaloStroke = Stroke(width = outerStroke * 2.1f)
                        val outerGlowStroke = Stroke(width = outerStroke * 7.4f, cap = StrokeCap.Round)
                        val outerMidGlowStroke = Stroke(width = outerStroke * 4.1f, cap = StrokeCap.Round)
                        val outerSoftStroke = Stroke(width = outerStroke * 2.15f, cap = StrokeCap.Round)
                        val outerMainStroke = Stroke(width = outerStroke * 1.15f, cap = StrokeCap.Round)

                        onDrawBehind {
                            val outerBaseStart = outerRotation.value

                            drawCircle(
                                brush = ambientGlowBrush,
                                radius = ambientRadius,
                                center = center,
                            )

                            drawCircle(
                                color = outerTrackColor,
                                radius = outerRadius,
                                style = outerTrackStroke,
                            )
                            drawCircle(
                                color = outerSweepColor.copy(alpha = 0.07f),
                                radius = outerRadius,
                                style = outerHaloStroke,
                            )
                            drawArc(
                                color = outerSweepColor.copy(alpha = 0.10f),
                                startAngle = outerBaseStart - 12f,
                                sweepAngle = 82f,
                                useCenter = false,
                                style = outerGlowStroke,
                                topLeft = outerArcTopLeft,
                                size = outerArcSize,
                            )
                            drawArc(
                                color = outerSweepColor.copy(alpha = 0.22f),
                                startAngle = outerBaseStart - 6f,
                                sweepAngle = 70f,
                                useCenter = false,
                                style = outerMidGlowStroke,
                                topLeft = outerArcTopLeft,
                                size = outerArcSize,
                            )
                            drawArc(
                                color = outerSweepColor.copy(alpha = 0.56f),
                                startAngle = outerBaseStart - 1.5f,
                                sweepAngle = 60f,
                                useCenter = false,
                                style = outerSoftStroke,
                                topLeft = outerArcTopLeft,
                                size = outerArcSize,
                            )
                            drawArc(
                                color = outerSweepColor,
                                startAngle = outerBaseStart + 2f,
                                sweepAngle = 48f,
                                useCenter = false,
                                style = outerMainStroke,
                                topLeft = outerArcTopLeft,
                                size = outerArcSize,
                            )
                        }
                    },
        )

        Image(
            painter = painterResource(Res.drawable.ic_lock),
            contentDescription = null,
            modifier = Modifier.size(128.dp),
        )
    }
}

@Preview
@Composable
private fun DualRingLoadingIndicatorPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        DualRingLoadingIndicator(modifier = Modifier.aspectRatio(1f).fillMaxSize())
    }
}
