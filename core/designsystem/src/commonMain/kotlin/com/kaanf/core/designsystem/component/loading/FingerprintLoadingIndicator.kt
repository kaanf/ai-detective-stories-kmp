package com.kaanf.core.designsystem.component.loading

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FingerprintLoadingIndicator(
    modifier: Modifier = Modifier,
    idleTint: Color = AccessDefaults.FingerprintIndicatorIdle,
    activeTint: Color = AccessDefaults.FingerprintIndicatorActive,
    glowColor: Color = AccessDefaults.FingerprintIndicatorGlow,
    frameColor: Color = AccessDefaults.FingerprintIndicatorFrame,
) {
    val transition = rememberInfiniteTransition(label = "fingerprintLoader")
    val scanProgress =
        transition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 2100, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse,
                ),
            label = "fingerprintScan",
        )

    val scanlineAlpha = 0.56f + (scanProgress.value * 0.18f)
    val glowAlpha = 0.18f + (scanProgress.value * 0.12f)
    val activeBandCenter = lerpFloat(start = 6f, end = 42f, fraction = scanProgress.value).dp
    val activeBandHeight = 14.dp

    Box(
        modifier = modifier.size(56.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 1.05.dp.toPx()
            val cornerLength = 11.99.dp.toPx()
            val glowWidth = 48.dp.toPx()
            val glowHeight = 2.dp.toPx()
            val outerGlowHeight = 8.dp.toPx()
            val scanlineTop =
                lerpFloat(
                    start = 10.dp.toPx(),
                    end = 42.dp.toPx(),
                    fraction = scanProgress.value,
                )
            val glowLeft = 4.dp.toPx()

            fun drawCorner(x: Float, y: Float, horizontalEndX: Float, verticalEndY: Float) {
                drawLine(
                    color = frameColor,
                    start = Offset(x, y),
                    end = Offset(horizontalEndX, y),
                    strokeWidth = strokeWidth,
                )
                drawLine(
                    color = frameColor,
                    start = Offset(x, y),
                    end = Offset(x, verticalEndY),
                    strokeWidth = strokeWidth,
                )
            }

            drawRoundRect(
                color = glowColor.copy(alpha = glowAlpha),
                topLeft = Offset(glowLeft, scanlineTop - ((outerGlowHeight - glowHeight) / 2f)),
                size = Size(glowWidth, outerGlowHeight),
                cornerRadius = CornerRadius(outerGlowHeight, outerGlowHeight),
            )
            drawRoundRect(
                color = glowColor.copy(alpha = scanlineAlpha),
                topLeft = Offset(glowLeft, scanlineTop),
                size = Size(glowWidth, glowHeight),
                cornerRadius = CornerRadius(glowHeight, glowHeight),
            )

            drawCorner(
                x = 0f,
                y = 0f,
                horizontalEndX = cornerLength,
                verticalEndY = cornerLength,
            )
            drawCorner(
                x = size.width,
                y = 0f,
                horizontalEndX = size.width - cornerLength,
                verticalEndY = cornerLength,
            )
            drawCorner(
                x = 0f,
                y = size.height,
                horizontalEndX = cornerLength,
                verticalEndY = size.height - cornerLength,
            )
            drawCorner(
                x = size.width,
                y = size.height,
                horizontalEndX = size.width - cornerLength,
                verticalEndY = size.height - cornerLength,
            )
        }

        Image(
            painter = painterResource(AccessIcons.Fingerprint),
            contentDescription = null,
            colorFilter = ColorFilter.tint(idleTint),
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.Center),
        )

        Image(
            painter = painterResource(AccessIcons.Fingerprint),
            contentDescription = null,
            colorFilter = ColorFilter.tint(activeTint),
            modifier =
                Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .drawWithContent {
                        val bandHalf = activeBandHeight.toPx() / 2f
                        val bandCenter = activeBandCenter.toPx()
                        clipRect(
                            left = 0f,
                            top = bandCenter - bandHalf,
                            right = size.width,
                            bottom = bandCenter + bandHalf,
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    },
        )
    }
}

private fun lerpFloat(start: Float, end: Float, fraction: Float): Float = start + (end - start) * fraction

@Preview
@Composable
private fun FingerprintLoadingIndicatorPreview() {
    DetectiveAiStoriesTheme(isDarkTheme = true) {
        Box(contentAlignment = Alignment.Center) {
            FingerprintLoadingIndicator()
        }
    }
}
