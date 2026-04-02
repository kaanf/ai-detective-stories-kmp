package com.kaanf.home.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kaanf.core.designsystem.component.badge.ResourceBadge
import com.kaanf.core.designsystem.component.badge.ResourceBadgeIcon
import com.kaanf.core.designsystem.component.badge.ResourceBadgeType
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessHeaderTextStyle
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.domain.model.user.User
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_agency_lead
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_name_suffix
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DashboardHeader(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AccessDefaults.PanelBackground)
                .padding(horizontal = 18.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(AccessDefaults.ButtonBackground)
                        .border(1.dp, Color(0xFF333333))
                        .shadow(10.dp, ambientColor = Color.Black),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = user.profileImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = stringResource(Res.string.dashboard_agency_lead),
                        style = AccessMetaTextStyle().copy(
                            color = AccessDefaults.FooterText,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp
                        ),
                    )

                    Text(
                        text = stringResource(Res.string.dashboard_name_suffix, user.fullName?.uppercase() ?: ""),
                        style = AccessHeaderTextStyle().copy(
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            letterSpacing = 1.sp,
                            color = AccessDefaults.FieldText,
                        ),
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                ResourceBadge(
                    value = user.energy,
                    type = ResourceBadgeType.Bounty,
                    badge = ResourceBadgeIcon.Energy
                )

                ResourceBadge(
                    value = user.gold,
                    type = ResourceBadgeType.Bounty,
                    badge = ResourceBadgeIcon.Gold
                )
            }
        }

        HorizontalDivider(
            color = AccessDefaults.ButtonBorder,
            thickness = 1.dp,
        )
    }
}

@Preview
@Composable
fun DashboardHeaderPreview() {
    DetectiveAiStoriesTheme {
        DashboardHeader(
            User(
                id = "0",
                email = "",
                fullName = "Kaan Fırat",
                profileImageUrl = "https://ads.kaanf.com/profile/profile1.png",
                gold = 5,
                energy = 20,
            ),
        )
    }
}
