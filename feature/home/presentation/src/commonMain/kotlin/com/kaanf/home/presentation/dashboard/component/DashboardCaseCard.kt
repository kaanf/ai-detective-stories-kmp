package com.kaanf.home.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.SpecialElite
import com.kaanf.home.domain.model.Bounty
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.CaseDifficulty
import com.kaanf.home.domain.model.CaseStatus
import com.kaanf.home.domain.model.Cost
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.case_placeholder_description
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_bounty
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_cost
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_file_prefix
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_status_classified
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_status_open
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardCaseCard(
    case: Case,
    onClick: () -> Unit,
) {
    val isClassified = case.status != CaseStatus.OPEN
    val cardAlpha = if (isClassified) 0.7f else 1f
    val cardBackground = if (isClassified) Color(0xFF080808) else Color(0xFF0D0D0D)
    val cardBorder = if (isClassified) Color(0xFF111111) else Color(0xFF333333)
    val titleColor = if (isClassified) AccessDefaults.FooterText else AccessDefaults.HeadingColor

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha)
            .background(cardBackground)
            .border(1.dp, cardBorder)
            .clickable(enabled = !isClassified, onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x0D8A1C1C),
                            Color.Transparent,
                        ),
                    ),
                ),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CaseCardHeader(case = case)

                Text(
                    text = case.title.uppercase(),
                    style = AccessHeaderTextStyle().copy(
                        fontSize = 20.sp,
                        letterSpacing = 0.4.sp,
                        color = titleColor,
                        textAlign = TextAlign.Start
                    )
                )

                Text(
                    text = stringResource(Res.string.case_placeholder_description),
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 12.sp,
                        lineHeight = 19.5.sp,
                        color = if (isClassified) Color(0xFF333333) else Color(0xFFA0A0A0),
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = if (isClassified) Modifier.blur(2.dp) else Modifier,
                )

                HorizontalDivider(
                    color = Color(0xFF1A1A1A),
                    thickness = 1.dp,
                )

                CaseCardFooter(case = case, isClassified = isClassified)
            }
        }
    }
}

@Composable
private fun CaseCardHeader(case: Case) {
    val isClassified = case.status == CaseStatus.OPEN

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF111111))
                    .border(1.dp, AccessDefaults.ButtonBorder)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 2.dp),
                    text = stringResource(Res.string.dashboard_case_file_prefix, case.type),
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        letterSpacing = 1.sp,
                        color = AccessDefaults.SupportingText,
                    ),
                )
            }
        }

        CaseStatusBadge(status = case.status)
    }
}

@Composable
private fun CaseStatusBadge(status: CaseStatus) {
    val isOpen = status == CaseStatus.OPEN
    val badgeBg = if (isOpen) Color(0x1A5A8A1C) else Color(0x1A8A1C1C)
    val badgeBorder = if (isOpen) Color(0x4D5A8A1C) else Color(0x4D8A1C1C)
    val textColor = if (isOpen) AccessDefaults.SuccessLine else AccessDefaults.AlertLine
    val label = if (isOpen) {
        stringResource(Res.string.dashboard_case_status_open)
    } else {
        stringResource(Res.string.dashboard_case_status_classified)
    }

    Box(
        modifier = Modifier
            .background(badgeBg)
            .border(1.dp, badgeBorder)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                modifier = Modifier
                    .size(10.dp),
                painter = painterResource(
                    if (isOpen) AccessIcons.KeyOpen else AccessIcons.KeyClosed,
                ),
                contentDescription = null,
                tint = if (isOpen) AccessDefaults.SuccessLine else AccessDefaults.AlertLine,
            )

            Text(
                modifier = Modifier
                    .padding(top = 2.dp),
                text = label,
                style = TextStyle(
                    fontFamily = SpecialElite,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 1.sp,
                    color = textColor,
                ),
            )
        }
    }
}

@Composable
private fun CaseCardFooter(
    case: Case,
    isClassified: Boolean,
) {
    val textColor = if (isClassified) AccessDefaults.FooterText else AccessDefaults.HeadingColor

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(Res.string.dashboard_case_cost),
                style = TextStyle(
                    fontFamily = SpecialElite,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 1.sp,
                    color = AccessDefaults.FooterText,
                ),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    modifier = Modifier
                        .size(17.dp),
                    painter = painterResource(AccessIcons.Energy),
                    contentDescription = null,
                    tint = AccessDefaults.HeadingColor,
                )

                Text(
                    text = "-${case.cost.energy}",
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = textColor,
                    ),
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(Res.string.dashboard_case_bounty),
                style = TextStyle(
                    fontFamily = SpecialElite,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 1.sp,
                    color = AccessDefaults.FooterText,
                ),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    modifier = Modifier.alpha(0.8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(17.dp),
                        painter = painterResource(AccessIcons.Gold),
                        contentDescription = null,
                        tint = AccessDefaults.GoldIconBackground,
                    )

                    Text(
                        text = "+${formatNumber(case.bounty.gold ?: 0)}",
                        style = TextStyle(
                            fontFamily = SpecialElite,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = AccessDefaults.GoldIconBackground,
                        ),
                    )
                }

                Row(
                    modifier = Modifier.alpha(0.8f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(17.dp),
                        painter = painterResource(AccessIcons.XP),
                        contentDescription = null,
                        tint = AccessDefaults.XPIconBackground,
                    )

                    Text(
                        text = "+${case.bounty.xp}",
                        style = TextStyle(
                            fontFamily = SpecialElite,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            color = AccessDefaults.XPIconBackground,
                        ),
                    )
                }
            }
        }
    }
}

private fun formatNumber(number: Int): String {
    if (number < 1000) return number.toString()
    val thousands = number / 1000
    val remainder = number % 1000
    return if (remainder == 0) {
        "$thousands,000"
    } else {
        "$thousands,${remainder.toString().padStart(3, '0')}"
    }
}

@Preview
@Composable
fun DashboardCaseCardPreview() {
    DetectiveAiStoriesTheme {
        DashboardCaseCard(
            Case(
                id = "1",
                title = "The Midnight Murders",
                status = CaseStatus.CLOSED,
                cost = Cost(energy = 20),
                bounty = Bounty(xp = 25),
                type = "Steal",
                difficulty = CaseDifficulty.MEDIUM
            ), {}
        )
    }
}
