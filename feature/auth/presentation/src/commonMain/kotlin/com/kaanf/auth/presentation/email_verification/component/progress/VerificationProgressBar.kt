package com.kaanf.auth.presentation.email_verification.component.progress

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.kaanf.core.designsystem.theme.AccessDefaults
import kotlin.math.min

@Composable
fun VerificationProgressBar(
    progress: Float,
    accentColor: Color,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier =
            modifier
                .fillMaxWidth()
                .height(6.dp),
    ) {
        drawRect(color = Color(0xFF111111))

        val fillWidth = size.width * progress.coerceIn(0f, 1f)
        if (fillWidth > 0f) {
            drawRect(
                color = accentColor,
                size = Size(fillWidth, size.height),
            )

            if (isLoading) {
                val glowWidth = min(fillWidth, 56.dp.toPx())
                drawRect(
                    brush =
                        Brush.horizontalGradient(
                            colors =
                                listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.78f),
                                    Color.Transparent,
                                ),
                            startX = fillWidth - glowWidth,
                            endX = fillWidth,
                        ),
                    topLeft = Offset((fillWidth - glowWidth).coerceAtLeast(0f), 0f),
                    size = Size(glowWidth, size.height),
                )
            }
        }

        drawRect(
            color = AccessDefaults.ButtonBorder,
            style = Stroke(width = 1.dp.toPx()),
        )
    }
}
