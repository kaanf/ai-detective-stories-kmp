package com.kaanf.home.presentation.dispatch.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.component.button.BaseButton
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.Inter
import com.kaanf.home.domain.model.CaseDifficulty
import com.kaanf.home.domain.model.TemporaryCase
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.ic_difficulty
import org.jetbrains.compose.resources.painterResource

private val SheetBackground = Color(0xFFEBE5D6)
private val SheetTextPrimary = Color(0xFF2C2B29)
private val SheetTextMuted = Color(0xFF555555)
private val SheetDashedBorderColor = Color(0x4D2C2B29)
private val SheetTeal = Color(0xFF5C8A8A)
private val SheetFooterBg = Color(0xFF2C2B29)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispatchCaseIntakeSheet(
    case: TemporaryCase,
    onDismiss: () -> Unit,
    onTakeCase: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        dragHandle = null,
        containerColor = Color.Transparent,
        scrimColor = Color.Black.copy(alpha = 0.84f),
        tonalElevation = 0.dp,
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SheetBackground),
        ) {
            IntakeFormHeader(case = case, onClose = onDismiss)

            HorizontalDivider(color = SheetTextPrimary, thickness = 1.dp)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                IntakeFormField(label = "CLIENT NAME / ALIAS", value = case.title)
                IntakeFormField(label = "CASE TYPE", value = case.type)

                HorizontalDivider(color = SheetTextPrimary, thickness = 1.dp)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                ) {
                    IntakeStatsColumn(case = case)
                    AgencyApprovalBox()
                }
            }

            IntakeFormFooter(onTakeCase = onTakeCase)
        }
    }
}

@Composable
private fun IntakeFormHeader(
    case: TemporaryCase,
    onClose: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Icon(
                painter = painterResource(AccessIcons.Case),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = SheetTextPrimary,
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "FORM NO: #${case.id.take(8).uppercase()}",
                    style = TextStyle(
                        fontFamily = Inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 2.1.sp,
                        color = SheetTextMuted,
                    ),
                )
                Text(
                    text = "CLIENT INTAKE FORM",
                    style = TextStyle(
                        fontFamily = Inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 1.6.sp,
                        color = SheetTextPrimary,
                    ),
                )
            }
        }

        CloseButton(onClick = onClose)
    }
}

@Composable
private fun CloseButton(onClick: () -> Unit) {
    Canvas(
        modifier = Modifier
            .size(28.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    ) {
        val strokeWidth = 1.6.dp.toPx()
        val inset = size.width * 0.28f
        drawLine(
            color = SheetTextMuted,
            start = Offset(inset, inset),
            end = Offset(size.width - inset, size.height - inset),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
        drawLine(
            color = SheetTextMuted,
            start = Offset(size.width - inset, inset),
            end = Offset(inset, size.height - inset),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round,
        )
    }
}

@Composable
private fun IntakeFormField(label: String, value: String) {
    val dashedBorderColor = SheetDashedBorderColor
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.1.sp,
                color = SheetTextMuted,
            ),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    val y = size.height
                    drawLine(
                        color = dashedBorderColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 6f)),
                    )
                }
                .padding(bottom = 8.dp),
        ) {
            Text(
                text = value,
                style = TextStyle(
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    letterSpacing = (-0.4).sp,
                    color = SheetTextPrimary,
                ),
            )
        }
    }
}

@Composable
private fun IntakeStatsColumn(case: TemporaryCase) {
    val threatLabel = when (case.difficulty) {
        CaseDifficulty.EASY -> "LOW"
        CaseDifficulty.MEDIUM -> "MODERATE"
        CaseDifficulty.HARD -> "HIGH"
    }
    val threatColor = when (case.difficulty) {
        CaseDifficulty.EASY, CaseDifficulty.MEDIUM -> SheetTeal
        CaseDifficulty.HARD -> AccessDefaults.AlertLine
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        IntakeStat(label = "THREAT LEVEL") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_difficulty),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = threatColor,
                )
                Text(
                    text = threatLabel,
                    style = TextStyle(
                        fontFamily = Inter,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        letterSpacing = (-0.45).sp,
                        color = threatColor,
                    ),
                )
            }
        }

        if (case.bounty.gold != null) {
            IntakeStat(label = "CLIENT BUDGET (ADVANCE INCL.)") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        painter = painterResource(AccessIcons.Gold),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = AccessDefaults.GoldIconBackground,
                    )
                    Text(
                        text = "${case.bounty.gold} GLD",
                        style = TextStyle(
                            fontFamily = Inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            letterSpacing = (-0.45).sp,
                            color = SheetTextPrimary,
                        ),
                    )
                }
            }
        }

        if (case.bounty.xp != null) {
            IntakeStat(label = "AGENCY EXP") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        painter = painterResource(AccessIcons.XP),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = SheetTeal,
                    )
                    Text(
                        text = "+${case.bounty.xp} EXP",
                        style = TextStyle(
                            fontFamily = Inter,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            letterSpacing = (-0.45).sp,
                            color = SheetTeal,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun IntakeStat(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.1.sp,
                color = SheetTextMuted,
            ),
        )
        content()
    }
}

@Composable
private fun AgencyApprovalBox() {
    val dashedBorderColor = SheetDashedBorderColor
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "AGENCY APPROVAL",
            style = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 1.1.sp,
                color = SheetTextMuted,
                textAlign = TextAlign.End,
            ),
        )
        Box(
            modifier = Modifier
                .rotate(-5f)
                .width(128.dp)
                .height(64.dp)
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val dash = PathEffect.dashPathEffect(floatArrayOf(8f, 6f))
                    drawLine(dashedBorderColor, Offset(0f, 0f), Offset(size.width, 0f), strokeWidth, pathEffect = dash)
                    drawLine(dashedBorderColor, Offset(0f, size.height), Offset(size.width, size.height), strokeWidth, pathEffect = dash)
                    drawLine(dashedBorderColor, Offset(0f, 0f), Offset(0f, size.height), strokeWidth, pathEffect = dash)
                    drawLine(dashedBorderColor, Offset(size.width, 0f), Offset(size.width, size.height), strokeWidth, pathEffect = dash)
                },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "SIGN HERE",
                style = TextStyle(
                    fontFamily = Inter,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = SheetTextMuted.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}

@Composable
private fun IntakeFormFooter(onTakeCase: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SheetFooterBg)
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        BaseButton(
            text = "TAKE CASE & SIGN",
            onClick = onTakeCase,
        )
        Spacer(Modifier.height(16.dp))
    }
}
