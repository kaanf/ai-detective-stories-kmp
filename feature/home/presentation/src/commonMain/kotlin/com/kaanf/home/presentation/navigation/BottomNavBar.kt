package com.kaanf.home.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.AccessDefaults.GoldIconBackground
import com.kaanf.core.designsystem.theme.Inter
import detective_ai_stories.feature.home.presentation.generated.resources.Res
import detective_ai_stories.feature.home.presentation.generated.resources.bottom_nav_dashboard
import detective_ai_stories.feature.home.presentation.generated.resources.bottom_nav_dispatch
import detective_ai_stories.feature.home.presentation.generated.resources.bottom_nav_informants
import detective_ai_stories.feature.home.presentation.generated.resources.bottom_nav_profile
import detective_ai_stories.feature.home.presentation.generated.resources.bottom_nav_pub
import detective_ai_stories.feature.home.presentation.generated.resources.ic_dashboard
import detective_ai_stories.feature.home.presentation.generated.resources.ic_dispatch
import detective_ai_stories.feature.home.presentation.generated.resources.ic_informant
import detective_ai_stories.feature.home.presentation.generated.resources.ic_profile
import detective_ai_stories.feature.home.presentation.generated.resources.ic_pub
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

enum class BottomNavTab(
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val route: Any,
) {
    Dashboard(Res.string.bottom_nav_dashboard, Res.drawable.ic_dashboard, DashboardRoute),
    Informants(Res.string.bottom_nav_informants, Res.drawable.ic_dispatch, InformantsRoute),
    Bar(Res.string.bottom_nav_pub, Res.drawable.ic_pub, BarRoute),
    Secretary(Res.string.bottom_nav_dispatch, Res.drawable.ic_informant, SecretaryRoute),
    Profile(Res.string.bottom_nav_profile, Res.drawable.ic_profile, ProfileRoute),
}

private val BottomNavBackground = Color(0xFF1A1A1A)
private val BottomNavBorderColor = Color(0xFF333333)
private val TabActiveColor = AccessDefaults.HeadingColor
private val TabInactiveColor = Color(0xFF666666)

@Composable
fun BottomNavBar(
    currentTab: BottomNavTab,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(15.dp, ambientColor = Color.Black)
            .drawBehind {
                drawLine(
                    color = BottomNavBorderColor,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx(),
                )
            }
            .background(AccessDefaults.PanelBackground)
            .navigationBarsPadding()
            .height(64.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BottomNavTab.entries.forEach { tab ->
            val isSelected = tab == currentTab

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onTabSelected(tab) },
                    )
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        painter = painterResource(tab.iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = if (isSelected) TabActiveColor else TabInactiveColor,
                    )

                    Text(
                        text = stringResource(tab.labelRes),
                        style = TextStyle(
                            fontFamily = Inter,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 10.sp,
                            lineHeight = 15.sp,
                            letterSpacing = 0.5.sp,
                            color = if (isSelected) TabActiveColor else TabInactiveColor,
                        ),
                    )
                }
            }
        }
    }
}
