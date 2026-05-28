package com.kaanf.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.component.badge.ResourceBadge
import com.kaanf.core.designsystem.component.badge.ResourceBadgeIcon
import com.kaanf.core.designsystem.component.badge.ResourceBadgeType
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.home.domain.model.Bounty
import com.kaanf.home.domain.model.Cost
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_bounty
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_cost
import org.jetbrains.compose.resources.stringResource

@Composable
fun CaseResourceRows(
    bounty: Bounty,
    modifier: Modifier = Modifier,
    cost: Cost? = null,
    alpha: Float = 1f,
) {
    val gold = bounty.gold
    val xp = bounty.xp

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (cost != null) {
            CaseResourceRow(label = stringResource(Res.string.dashboard_case_cost)) {
                Row(
                    modifier = Modifier.alpha(alpha),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ResourceBadge(
                        value = cost.energy,
                        type = ResourceBadgeType.Cost,
                        badge = ResourceBadgeIcon.Energy,
                    )
                }
            }
        }

        CaseResourceRow(label = stringResource(Res.string.dashboard_case_bounty)) {
            Row(
                modifier = Modifier.alpha(alpha),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (gold != null) {
                    ResourceBadge(
                        value = gold,
                        type = ResourceBadgeType.Bounty,
                        badge = ResourceBadgeIcon.Gold,
                    )
                }

                if (xp != null) {
                    ResourceBadge(
                        value = xp,
                        type = ResourceBadgeType.Bounty,
                        badge = ResourceBadgeIcon.XP,
                    )
                }
            }
        }
    }
}

@Composable
private fun CaseResourceRow(
    label: String,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label.uppercase(),
            style = AccessMetaTextStyle().copy(
                fontSize = 9.sp,
                lineHeight = 13.5.sp,
                letterSpacing = 0.9.sp,
                fontWeight = FontWeight.Normal,
                color = AccessDefaults.FooterText,
            ),
        )

        content()
    }
}
