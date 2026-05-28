package com.kaanf.home.presentation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.SpecialElite
import org.jetbrains.compose.ui.tooling.preview.Preview

private val StampShape = RoundedCornerShape(4.dp)

private val AssignedBorderColor = Color(0xFF8A1C1C)
private val AssignedTextColor = Color(0xFF9A2A2A)

private val SolvedBorderColor = Color(0xFF1C5C5C)
private val SolvedTextColor = Color(0xFF2A8A8A)

enum class CaseStampVariant {
    Assigned,
    Solved,
}

@Composable
fun CaseStatusStamp(
    variant: CaseStampVariant,
    modifier: Modifier = Modifier,
) {
    val borderColor = when (variant) {
        CaseStampVariant.Assigned -> AssignedBorderColor
        CaseStampVariant.Solved -> SolvedBorderColor
    }
    val mainTextColor = when (variant) {
        CaseStampVariant.Assigned -> AssignedTextColor
        CaseStampVariant.Solved -> SolvedTextColor
    }
    val statusLabel = when (variant) {
        CaseStampVariant.Assigned -> "ASSIGNED"
        CaseStampVariant.Solved -> "SOLVED"
    }

    Box(
        modifier = modifier
            .wrapContentWidth()
            .alpha(0.8f)
            .border(2.dp, borderColor, StampShape)
            .padding(horizontal = 7.dp, vertical = 7.dp),
    ) {
        Box(
            modifier = Modifier
                .wrapContentWidth()
                .border(1.dp, borderColor, StampShape)
                .padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                StampHeader(color = borderColor)

                Spacer(Modifier.height(6.dp))

                Text(
                    text = statusLabel,
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 28.sp,
                        letterSpacing = 2.8.sp,
                        color = mainTextColor,
                        textAlign = TextAlign.Center,
                    ),
                )

                Spacer(Modifier.height(4.dp))

                StampFooter(color = borderColor)
            }
        }
    }
}

@Composable
private fun StampHeader(color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "★",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 8.sp,
                color = color,
            ),
        )
        Text(
            text = "OFFICE OF THE CHIEF",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 7.sp,
                letterSpacing = 1.8.sp,
                color = color,
            ),
        )
        Text(
            text = "★",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 8.sp,
                color = color,
            ),
        )
    }
}

@Composable
private fun StampFooter(color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        HorizontalDivider(
            modifier = Modifier.width(120.dp),
            thickness = 0.5.dp,
            color = color,
        )
        Text(
            text = "AUTHORIZED PERSONNEL ONLY",
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 6.sp,
                letterSpacing = 2.4.sp,
                color = color,
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@Preview
@Composable
fun CaseStatusStampAssignedPreview() {
    DetectiveAiStoriesTheme {
        CaseStatusStamp(variant = CaseStampVariant.Assigned)
    }
}

@Preview
@Composable
fun CaseStatusStampSolvedPreview() {
    DetectiveAiStoriesTheme {
        CaseStatusStamp(variant = CaseStampVariant.Solved)
    }
}
