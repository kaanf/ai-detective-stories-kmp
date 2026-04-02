package com.kaanf.home.presentation.dispatch.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.component.badge.ResourceBadge
import com.kaanf.core.designsystem.component.badge.ResourceBadgeIcon
import com.kaanf.core.designsystem.component.badge.ResourceBadgeType
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessDefaults.ButtonTextColor
import com.kaanf.core.designsystem.theme.AccessDefaults.CardBackground
import com.kaanf.core.designsystem.theme.AccessDefaults.FieldFocusedBackground
import com.kaanf.core.designsystem.theme.AccessDefaults.FingerprintIndicatorFrame
import com.kaanf.core.designsystem.theme.AccessDefaults.QuoteAccent
import com.kaanf.core.designsystem.theme.AccessDefaults.QuoteBackground
import com.kaanf.core.designsystem.theme.AccessDefaults.QuoteTextColor
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.SpecialElite
import com.kaanf.home.domain.model.Bounty
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.CaseDifficulty
import com.kaanf.home.domain.model.CaseStatus
import com.kaanf.home.domain.model.Cost
import com.kaanf.home.domain.model.TemporaryCase
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.case_difficulty_label
import detective_ai_stories.feature.home.presentation.generated.resources.case_placeholder_description
import detective_ai_stories.feature.home.presentation.generated.resources.case_primary_action_assign
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_bounty
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_cost
import detective_ai_stories.feature.home.presentation.generated.resources.dispatch_description
import detective_ai_stories.feature.home.presentation.generated.resources.ic_difficulty
import detective_ai_stories.feature.home.presentation.generated.resources.ic_open_folder
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DispatchCaseCard(
    case: TemporaryCase,
    onPickCase: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(CardBackground)
            .border(1.dp, FieldFocusedBackground),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TemporaryCaseCardHeader(case = case)

            CaseQuoteBox(case.status != CaseStatus.OPEN)

            CaseCardFooter(
                case = case,
                onPickCase = onPickCase,
            )
        }
    }
}

@Composable
private fun TemporaryCaseCardHeader(case: TemporaryCase) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = case.type.uppercase(),
                style = AccessMetaTextStyle().copy(
                    fontSize = 9.sp,
                    lineHeight = 13.5.sp,
                    letterSpacing = 0.9.sp,
                    color = AccessDefaults.FooterText,
                ),
            )

            Text(
                text = case.title.uppercase(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = AccessHeaderTextStyle().copy(
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Start,
                    letterSpacing = 1.6.sp,
                ),
            )
        }

        DifficultyIndicator(difficulty = case.difficulty)
    }
}

@Composable
private fun DifficultyIndicator(difficulty: CaseDifficulty) {
    val filledCount = when (difficulty) {
        CaseDifficulty.EASY -> 1
        CaseDifficulty.MEDIUM -> 2
        CaseDifficulty.HARD -> 3
    }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = stringResource(Res.string.case_difficulty_label),
            style = AccessSubtitleTextStyle().copy(
                fontSize = 10.sp,
                lineHeight = 13.5.sp,
                letterSpacing = 0.9.sp,
                color = AccessDefaults.FooterText,
                textAlign = TextAlign.End,
            ),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            repeat(3) { index ->
                Icon(
                    painter = painterResource(Res.drawable.ic_difficulty),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (index < filledCount) AccessDefaults.GoldIconBackground else AccessDefaults.FooterText,
                )
            }
        }
    }
}

@Composable
private fun CaseQuoteBox(isClassified: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(QuoteBackground)
            .border(1.dp, AccessDefaults.PanelAlternativeBackground),
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(36.dp)
                .align(Alignment.CenterStart)
                .background(QuoteAccent),
        )

        Text(
            text = stringResource(Res.string.case_placeholder_description),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            modifier = Modifier
                .padding(16.dp)
                .blur(
                    if (!isClassified) 3.dp else 0.dp,
                ),
            style = AccessSubtitleTextStyle().copy(
                fontSize = 11.sp,
                letterSpacing = 0.sp,
                textAlign = TextAlign.Start,
                lineHeight = 17.9.sp,
                color = QuoteTextColor,
            ),
        )
    }
}

@Composable
private fun CaseCardFooter(
    case: TemporaryCase,
    onPickCase: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onPickCase,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ResourceBadge(
                    value = case.cost.energy,
                    type = ResourceBadgeType.Cost,
                    badge = ResourceBadgeIcon.Energy,
                )

                if (case.bounty.gold != null) {
                    ResourceBadge(
                        value = case.bounty.gold?.toInt() ?: 0,
                        type = ResourceBadgeType.Bounty,
                        badge = ResourceBadgeIcon.Gold,
                    )
                }

                if (case.bounty.energy != null) {
                    ResourceBadge(
                        value = case.bounty.energy ?: 0,
                        type = ResourceBadgeType.Bounty,
                        badge = ResourceBadgeIcon.Energy,
                    )
                }
            }

        Box(
            modifier = Modifier
                .border(1.dp, FingerprintIndicatorFrame)
                .padding(horizontal = 24.dp, vertical = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_open_folder),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = AccessDefaults.AlertLine,
                )

                Text(
                    modifier = Modifier
                        .padding(top = 2.dp),
                    text = stringResource(Res.string.case_primary_action_assign),
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        letterSpacing = 1.sp,
                        color = ButtonTextColor,
                        textAlign = TextAlign.Center,
                    ),
                )
            }
        }
    }
}

@Preview
@Composable
fun DispatchCaseCardPreview() {
    DetectiveAiStoriesTheme {
        DispatchCaseCard(
            case = TemporaryCase(
                id = "3",
                title = "Cinayet sdfkjsdjkfsjkdf sdkjfsdf",
                difficulty = CaseDifficulty.MEDIUM,
                type = "Doğu Rıhtımı",
                status = CaseStatus.OPEN,
                cost = Cost(energy = 30),
                bounty = Bounty(energy = 99, gold = 30.0),
            ),
            onPickCase = {},
        )
    }
}
