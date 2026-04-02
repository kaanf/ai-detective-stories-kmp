package com.kaanf.home.presentation.pub.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.Inter
import com.kaanf.core.designsystem.theme.SpecialElite
import com.kaanf.home.domain.model.PubItem
import com.kaanf.home.domain.model.PubItemAccent
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.pub_requirement_label
import detective_ai_stories.feature.home.presentation.generated.resources.pub_requirement_unit
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val GoldAccent = Color(0xFFA38A5C)
private val GoldOverlay = Color(0x0DA38A5C)
private val RedAccent = AccessDefaults.AlertLine
private val RedOverlay = Color(0x0D8A1C1C)

@Composable
fun PubItemCard(
    item: PubItem,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val accentColor = when (item.accentType) {
        PubItemAccent.GOLD -> GoldAccent
        PubItemAccent.RED -> RedAccent
    }
    val overlayColor = when (item.accentType) {
        PubItemAccent.GOLD -> GoldOverlay
        PubItemAccent.RED -> RedOverlay
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xCC0A0A0A))
            .border(1.dp, AccessDefaults.ButtonBorder),
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(overlayColor),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF050505))
                            .border(1.dp, Color(0xFF333333)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            painter = painterResource(AccessIcons.Gold),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = accentColor,
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = item.name,
                            style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Normal,
                                fontSize = 20.sp,
                                lineHeight = 28.sp,
                                letterSpacing = 2.sp,
                                color = AccessDefaults.HeadingColor,
                            ),
                        )

                        Text(
                            text = item.subtitle,
                            style = TextStyle(
                                fontFamily = SpecialElite,
                                fontSize = 9.sp,
                                lineHeight = 13.5.sp,
                                letterSpacing = 1.8.sp,
                                color = accentColor,
                            ),
                        )
                    }
                }

                Text(
                    text = item.description,
                    style = TextStyle(
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp,
                        lineHeight = 17.9.sp,
                        color = Color(0xFF666666),
                    ),
                )
            }

            RequirementSection(
                actionCost = item.actionCost,
                actionLabel = item.actionLabel,
                onActionClick = onActionClick,
            )
        }
    }
}

@Composable
private fun RequirementSection(
    actionCost: Int,
    actionLabel: String,
    onActionClick: () -> Unit,
) {
    val borderColor = AccessDefaults.ButtonBorder

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = 1.dp.toPx(),
                )
            }
            .padding(start = 24.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(Res.string.pub_requirement_label),
                style = TextStyle(
                    fontFamily = SpecialElite,
                    fontSize = 9.sp,
                    lineHeight = 13.5.sp,
                    letterSpacing = 0.9.sp,
                    color = Color(0xFF444444),
                ),
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "$actionCost",
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        color = AccessDefaults.HeadingColor,
                    ),
                )

                Text(
                    text = stringResource(Res.string.pub_requirement_unit),
                    modifier = Modifier.padding(bottom = 3.dp),
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 10.sp,
                        lineHeight = 15.6.sp,
                        color = AccessDefaults.SupportingText,
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xFF333333))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onActionClick,
                )
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = actionLabel,
                style = TextStyle(
                    fontFamily = SpecialElite,
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    letterSpacing = 1.sp,
                    color = Color(0xFFA0A0A0),
                    textAlign = TextAlign.Center,
                ),
            )
        }
    }
}
