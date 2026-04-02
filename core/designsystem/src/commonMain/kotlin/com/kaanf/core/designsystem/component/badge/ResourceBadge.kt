package com.kaanf.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.designsystem.theme.SpecialElite
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.reflect.typeOf

enum class ResourceBadgeType {
    Bounty, Cost, Default
}

enum class ResourceBadgeIcon {
    Gold, Energy
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
                    ResourceBadgeType.Cost,
                    ResourceBadgeType.Bounty,
                        -> {
                        when (badge) {
                            ResourceBadgeIcon.Gold -> AccessDefaults.GoldIconBackground
                            ResourceBadgeIcon.Energy -> AccessDefaults.EnergyIconBackground
                        }
                    }

                    ResourceBadgeType.Default -> AccessDefaults.HeadingColor
                },
            ),
        )

        Icon(
            modifier = Modifier
                .size(16.dp),
            painter = painterResource(
                when (badge) {
                    ResourceBadgeIcon.Gold -> AccessIcons.Gold
                    ResourceBadgeIcon.Energy -> AccessIcons.Energy
                },
            ),
            contentDescription = null,
            tint = when (type) {
                ResourceBadgeType.Cost,
                ResourceBadgeType.Bounty,
                    -> {
                    when (badge) {
                        ResourceBadgeIcon.Gold -> AccessDefaults.GoldIconBackground
                        ResourceBadgeIcon.Energy -> AccessDefaults.EnergyIconBackground
                    }
                }

                ResourceBadgeType.Default -> AccessDefaults.HeadingColor
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
            badge = ResourceBadgeIcon.Energy,
        )
    }
}
