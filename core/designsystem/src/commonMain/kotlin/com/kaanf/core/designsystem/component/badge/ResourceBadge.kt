package com.kaanf.core.designsystem.component.badge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ResourceBadgeType {
    Bounty, Cost, Default
}

enum class ResourceBadgeIcon {
    Gold, XP, Energy
}

@Composable
fun ResourceBadge(
    value: Int,
    badge: ResourceBadgeIcon,
    type: ResourceBadgeType,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = when (type) {
                ResourceBadgeType.Cost -> "-$value"
                ResourceBadgeType.Bounty -> "+$value"
                ResourceBadgeType.Default -> "$value"
            },
            style = AccessSubtitleTextStyle().copy(
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = when (type) {
                    ResourceBadgeType.Cost -> AccessDefaults.HeadingColor
                    ResourceBadgeType.Bounty,
                    ResourceBadgeType.Default-> {
                        when (badge) {
                            ResourceBadgeIcon.Gold -> AccessDefaults.GoldIconBackground
                            ResourceBadgeIcon.XP -> AccessDefaults.XPIconBackground
                            ResourceBadgeIcon.Energy -> AccessDefaults.EnergyIconBackground
                        }
                    }
                },
            ),
        )

        Icon(
            modifier = Modifier
                .size(16.dp),
            painter = painterResource(
                when (badge) {
                    ResourceBadgeIcon.Gold -> AccessIcons.Gold
                    ResourceBadgeIcon.XP -> AccessIcons.XP
                    ResourceBadgeIcon.Energy -> AccessIcons.Energy
                },
            ),
            contentDescription = null,
            tint = when (type) {
                ResourceBadgeType.Default,
                ResourceBadgeType.Bounty,
                    -> {
                    when (badge) {
                        ResourceBadgeIcon.Gold -> AccessDefaults.GoldIconBackground
                        ResourceBadgeIcon.XP -> AccessDefaults.XPIconBackground
                        ResourceBadgeIcon.Energy -> AccessDefaults.EnergyIconBackground
                    }
                }

                ResourceBadgeType.Cost -> AccessDefaults.HeadingColor
            },
        )
    }
}


@Preview
@Composable
fun ResourceBadgePreview() {
    DetectiveAiStoriesTheme {
        ResourceBadge(
            value = 100,
            type = ResourceBadgeType.Bounty,
            badge = ResourceBadgeIcon.XP,
        )
    }
}
