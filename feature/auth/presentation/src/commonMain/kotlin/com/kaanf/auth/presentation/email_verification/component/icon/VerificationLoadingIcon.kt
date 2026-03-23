package com.kaanf.auth.presentation.email_verification.component.icon

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.kaanf.auth.presentation.email_verification.loading.EmailVerificationLoadingPhase
import com.kaanf.core.designsystem.theme.AccessDefaults

@Composable
fun VerificationLoadingIcon(
    modifier: Modifier = Modifier,
    phase: EmailVerificationLoadingPhase,
    accentColor: Color,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 0.8.dp.toPx()
            drawCircle(
                color = Color(0xFF333333),
                radius = size.minDimension / 2 - strokeWidth,
                style =
                    Stroke(
                        width = strokeWidth,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)),
                    ),
                alpha = 0.75f,
            )

            drawCircle(
                brush =
                    Brush.radialGradient(
                        colors =
                            listOf(
                                accentColor.copy(alpha = if (phase == EmailVerificationLoadingPhase.Verified) 0.14f else 0.10f),
                                Color.Transparent,
                            ),
                        radius = size.minDimension * 0.44f,
                    ),
                radius = size.minDimension * 0.44f,
            )
        }

        Box(
            modifier =
                Modifier
                    .size(112.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF050505))
                    .border(
                        width = 1.dp,
                        color = AccessDefaults.ButtonBorder,
                        shape = CircleShape,
                    ),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    brush =
                        Brush.radialGradient(
                            colors =
                                listOf(
                                    accentColor.copy(alpha = if (phase == EmailVerificationLoadingPhase.Verified) 0.20f else 0.14f),
                                    Color.Transparent,
                                ),
                            radius = size.minDimension * 0.44f,
                        ),
                    radius = size.minDimension * 0.44f,
                )

                if (phase != EmailVerificationLoadingPhase.Verified) {
                    val focusBoxSize = size.minDimension * 0.32f
                    val left = (size.width - focusBoxSize) / 2f
                    val top = (size.height - focusBoxSize) / 2f
                    val right = left + focusBoxSize
                    val bottom = top + focusBoxSize
                    val corner = focusBoxSize * 0.28f
                    val focusStroke = 3.dp.toPx()

                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(left, top + corner),
                        end = Offset(left, top),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(left, top),
                        end = Offset(left + corner, top),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(right - corner, top),
                        end = Offset(right, top),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(right, top),
                        end = Offset(right, top + corner),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(left, bottom - corner),
                        end = Offset(left, bottom),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(left, bottom),
                        end = Offset(left + corner, bottom),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(right - corner, bottom),
                        end = Offset(right, bottom),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                    drawLine(
                        color = accentColor.copy(alpha = 0.9f),
                        start = Offset(right, bottom - corner),
                        end = Offset(right, bottom),
                        strokeWidth = focusStroke,
                        cap = StrokeCap.Round,
                    )
                }
            }

            ForgotPasswordLockIcon(
                modifier = Modifier.size(50.dp),
                colorFilter =
                    ColorFilter.tint(
                        color =
                            when (phase) {
                                EmailVerificationLoadingPhase.Verifying -> AccessDefaults.HeadingColor.copy(alpha = 0.86f)
                                EmailVerificationLoadingPhase.Verified -> accentColor
                                EmailVerificationLoadingPhase.Failed -> accentColor
                            },
                    )
            )
        }
    }
}
