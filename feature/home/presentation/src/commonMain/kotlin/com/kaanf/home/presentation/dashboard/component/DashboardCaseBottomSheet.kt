package com.kaanf.home.presentation.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.component.layout.BaseBottomSheetContainer
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessIcons
import com.kaanf.core.designsystem.theme.AccessMetaTextStyle
import com.kaanf.core.designsystem.theme.AccessSubtitleTextStyle
import com.kaanf.core.designsystem.theme.Inter
import com.kaanf.home.domain.model.Case
import com.kaanf.home.domain.model.CaseStatus
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.case_placeholder_description
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_sheet_client_anonymous
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_sheet_close
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_sheet_encrypted
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_sheet_estimated_returns
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_sheet_primary_action
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_sheet_reference_metadata
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_case_status_classified
import detective_ai_stories.feature.home.presentation.generated.resources.dashboard_empty_status_value
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val SheetTitleColor = Color(0xFFEBE5D6)
private val SheetMetaColor = Color(0xFF555555)
private val SheetBodyBorder = Color(0xFF222222)
private val SheetBodyText = Color(0xFF555555)
private val SheetBadgeColor = Color(0xFF8A1C1C)
private val SheetPrimaryButton = Color(0xFFEBE5D6)
private val SheetPrimaryText = Color(0xFF111111)
private val SheetSecondaryText = Color(0xFF777777)
private val SheetOverlay = Color(0x66000000)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardCaseBottomSheet(
    case: Case,
    onDismiss: () -> Unit,
    onPrimaryAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    BaseBottomSheetContainer(
        title = "",
        footerText = "",
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = modifier,
        showHeader = false,
        showFooter = false,
        shape = RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp),
        contentPadding = PaddingValues(0.dp),
        contentBackgroundBrush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF070707),
                Color(0xFF050505),
            ),
        ),
    ) {
        DashboardCaseSheetContent(
            case = case,
            onDismiss = onDismiss,
            onPrimaryAction = onPrimaryAction,
        )
    }
}

@Composable
private fun DashboardCaseSheetContent(
    case: Case,
    onDismiss: () -> Unit,
    onPrimaryAction: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    DashboardSheetStatusBadge(status = case.status)

                    Text(
                        text = stringResource(
                            Res.string.dashboard_case_sheet_reference_metadata,
                            case.id.takeLast(2).padStart(2, '0'),
                            stringResource(Res.string.dashboard_case_sheet_client_anonymous),
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = AccessMetaTextStyle().copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            letterSpacing = 1.1.sp,
                            color = SheetMetaColor,
                        ),
                    )
                }

                Text(
                    text = case.title.uppercase(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        fontSize = 30.sp,
                        lineHeight = 36.sp,
                        letterSpacing = 3.4.sp,
                        color = SheetTitleColor,
                    ),
                )
            }

            DashboardEncryptedPreview()

            DashboardReturnsSection(case = case)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                DashboardSheetButton(
                    text = stringResource(Res.string.dashboard_case_sheet_close),
                    onClick = onDismiss,
                    modifier = Modifier.weight(0.33f),
                )

                DashboardSheetButton(
                    text = stringResource(Res.string.dashboard_case_sheet_primary_action),
                    onClick = onPrimaryAction,
                    modifier = Modifier.weight(0.67f),
                    isPrimary = true,
                )
            }
        }
    }
}

@Composable
private fun DashboardSheetStatusBadge(status: CaseStatus) {
    val label = if (status == CaseStatus.OPEN) {
        stringResource(Res.string.dashboard_empty_status_value)
    } else {
        stringResource(Res.string.dashboard_case_status_classified)
    }

    Box(
        modifier = Modifier
            .background(SheetBadgeColor.copy(alpha = 0.1f))
            .border(1.dp, SheetBadgeColor.copy(alpha = 0.3f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Icon(
                modifier = Modifier.size(12.dp),
                painter = painterResource(AccessIcons.KeyClosed),
                contentDescription = null,
                tint = SheetBadgeColor,
            )

            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = label,
                style = TextStyle(
                    fontFamily = Inter,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp,
                    lineHeight = 13.5.sp,
                    letterSpacing = 0.9.sp,
                    color = SheetBadgeColor,
                ),
            )
        }
    }
}

@Composable
private fun DashboardEncryptedPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(87.dp)
            .border(1.dp, SheetBodyBorder),
            contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.case_placeholder_description),
            modifier = Modifier
                .fillMaxWidth(0.82f)
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 20.dp)
                .alpha(0.5f)
                .blur(12.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontFamily = Inter,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                lineHeight = 22.75.sp,
                color = SheetBodyText,
            ),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(85.dp)
                .background(SheetOverlay),
                contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(AccessIcons.KeyClosed),
                    contentDescription = null,
                    tint = SheetBadgeColor,
                )

                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .border(1.dp, SheetBadgeColor.copy(alpha = 0.3f))
                        .padding(horizontal = 12.dp, vertical = 7.dp),
                ) {
                    Text(
                        text = stringResource(Res.string.dashboard_case_sheet_encrypted),
                        style = TextStyle(
                            fontFamily = Inter,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            letterSpacing = 2.1.sp,
                            color = SheetBadgeColor,
                            textAlign = TextAlign.Center,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardReturnsSection(case: Case) {
    val gold = case.bounty.gold
    val xp = case.bounty.xp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SheetBodyBorder)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(Res.string.dashboard_case_sheet_estimated_returns),
            style = AccessSubtitleTextStyle().copy(
                fontFamily = Inter,
                fontSize = 9.sp,
                lineHeight = 13.5.sp,
                letterSpacing = 0.9.sp,
                textAlign = TextAlign.Start,
                color = SheetMetaColor,
            ),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            if (gold != null) {
                DashboardReturnBadge(
                    value = gold,
                    tint = AccessDefaults.GoldIconBackground,
                    icon = AccessIcons.Gold,
                )
            }

            if (xp != null) {
                DashboardReturnBadge(
                    value = xp,
                    tint = AccessDefaults.XPIconBackground,
                    icon = AccessIcons.XP,
                )
            }
        }
    }
}

@Composable
private fun DashboardReturnBadge(
    value: Int,
    tint: Color,
    icon: org.jetbrains.compose.resources.DrawableResource,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = tint,
        )

        Text(
            text = "+$value",
            style = AccessMetaTextStyle().copy(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.15).sp,
                color = tint,
            ),
        )
    }
}

@Composable
private fun DashboardSheetButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = false,
) {
    Box(
        modifier = modifier
            .height(49.dp)
            .background(
                brush = if (isPrimary) {
                    Brush.horizontalGradient(
                        colors = listOf(
                            SheetPrimaryButton,
                            SheetPrimaryButton,
                        ),
                    )
                } else {
                    Brush.horizontalGradient(
                        colors = listOf(Color.Transparent, Color.Transparent),
                    )
                },
            )
            .border(
                width = 1.dp,
                color = if (isPrimary) SheetPrimaryButton else AccessDefaults.FingerprintIndicatorFrame,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = Inter,
                fontWeight = if (isPrimary) FontWeight.Bold else FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                letterSpacing = 1.2.sp,
                color = if (isPrimary) SheetPrimaryText else SheetSecondaryText,
                textAlign = TextAlign.Center,
            ),
        )
    }
}
