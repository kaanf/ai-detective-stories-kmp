package com.kaanf.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessDefaults.FieldBorder
import com.kaanf.core.designsystem.theme.AccessDefaults.LoadingScreenBackground
import com.kaanf.core.designsystem.theme.AccessDefaults.PanelAlternativeBackground
import com.kaanf.core.designsystem.theme.AccessDefaults.QuoteBackground
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.core.domain.model.user.User
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val StatusBarBackground = Color(0xFF020202)
private val XpSectionBackground = Color(0xFF030303)
private val AvatarBorderColor = Color(0xFF333333)
private val MetaLabelColor = Color(0xFF666666)

@Composable
fun UserTopPanel(
    user: User,
    level: Int = 1,
    maxXp: Int = 1000,
    maxEnergy: Int = 50,
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(color = FieldBorder, thickness = 1.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(StatusBarBackground),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(StatusBarBackground)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "SYS.ID: 942-X // AUTH_VERIFIED",
                style = AccessMetaTextStyle().copy(
                    fontSize = 8.sp,
                    letterSpacing = 1.6.sp,
                    color = AccessDefaults.FooterText,
                    fontWeight = FontWeight.Normal,
                ),
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(AccessDefaults.AlertLine, shape = CircleShape),
                )

                Text(
                    text = "SİSTEM AKTİF",
                    style = AccessMetaTextStyle().copy(
                        fontSize = 8.sp,
                        letterSpacing = 1.6.sp,
                        color = AccessDefaults.AlertLine,
                        fontWeight = FontWeight.Normal,
                    ),
                )
            }
        }
        // XP progress row
        HorizontalDivider(color = FieldBorder, thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(QuoteBackground)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black)
                        .border(1.dp, AvatarBorderColor),
                    contentAlignment = Alignment.Center,
                ) {
                    AsyncImage(
                        model = user.profileImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "AJAN PROFİLİ",
                        style = AccessMetaTextStyle().copy(
                            fontSize = 8.sp,
                            letterSpacing = 2.4.sp,
                            color = MetaLabelColor,
                            fontWeight = FontWeight.Normal,
                        ),
                    )

                    Text(
                        text = user.fullName?.uppercase() ?: "",
                        style = AccessMetaTextStyle().copy(
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            letterSpacing = 1.8.sp,
                            color = AccessDefaults.HeadingColor,
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                EnergyBadgeBox(energy = user.energy, maxEnergy = maxEnergy)

                GoldBadgeBox(gold = user.gold)
            }
        }

        // XP progress row
        HorizontalDivider(color = PanelAlternativeBackground, thickness = 1.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LoadingScreenBackground)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        painter = painterResource(AccessIcons.XP),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = AccessDefaults.XPIconBackground,
                    )

                    Text(
                        text = "SEVİYE $level",
                        style = AccessMetaTextStyle().copy(
                            fontSize = 9.sp,
                            letterSpacing = 1.8.sp,
                            color = AccessDefaults.XPIconBackground,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = AccessDefaults.FooterText)) {
                            append("${user.xp} ")
                        }
                        withStyle(SpanStyle(color = AccessDefaults.FooterText.copy(alpha = 0.7f))) {
                            append("/ $maxXp")
                        }
                    },
                    style = AccessMetaTextStyle().copy(
                        fontSize = 8.sp,
                        letterSpacing = 0.8.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(PanelAlternativeBackground)
                    .border(1.dp, AvatarBorderColor),
            ) {
                val progress = (620.toFloat() / maxXp.toFloat()).coerceIn(0f, 1f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .height(4.dp)
                        .background(AccessDefaults.XPIconBackground),
                )
            }
        }

        // XP progress row
        HorizontalDivider(color = FieldBorder, thickness = 1.dp)
    }
}

@Composable
private fun EnergyBadgeBox(energy: Int, maxEnergy: Int) {
    Row(
        modifier = Modifier
            .background(Color.Black)
            .border(1.dp, FieldBorder)
            .padding(horizontal = 9.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            painter = painterResource(AccessIcons.Energy),
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = AccessDefaults.EnergyIconBackground,
        )

        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 14.sp, color = AccessDefaults.HeadingColor)) {
                    append("$energy")
                }
                withStyle(SpanStyle(fontSize = 10.sp, color = AccessDefaults.FooterText)) {
                    append("/$maxEnergy")
                }
            },
            style = AccessSubtitleTextStyle().copy(
                letterSpacing = 0.7.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            ),
        )
    }
}

@Composable
private fun GoldBadgeBox(gold: Int) {
    Row(
        modifier = Modifier
            .background(Color.Black)
            .border(1.dp, FieldBorder)
            .padding(horizontal = 9.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            painter = painterResource(AccessIcons.Gold),
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = AccessDefaults.GoldIconBackground,
        )

        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = "$gold",
            style = AccessSubtitleTextStyle().copy(
                fontSize = 14.sp,
                letterSpacing = 0.7.sp,
                textAlign = TextAlign.Center,
                color = AccessDefaults.GoldIconBackground,
                fontWeight = FontWeight.Normal,
            ),
        )
    }
}

@Preview
@Composable
fun UserTopPanelPreview() {
    DetectiveAiStoriesTheme {
        UserTopPanel(
            user = User(
                id = "0",
                email = "",
                fullName = "Reno",
                profileImageUrl = "",
                gold = 450,
                energy = 28,
                xp = 650,
            ),
            level = 4,
            maxXp = 1000,
            maxEnergy = 50,
        )
    }
}
