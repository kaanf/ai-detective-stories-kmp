package com.kaanf.home.presentation.pub

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaanf.core.designsystem.component.badge.ResourceBadge
import com.kaanf.core.designsystem.component.badge.ResourceBadgeIcon
import com.kaanf.core.designsystem.component.badge.ResourceBadgeType
import com.kaanf.core.designsystem.theme.AccessDefaults
import com.kaanf.core.designsystem.theme.SpecialElite
import com.kaanf.core.domain.model.user.User
import com.kaanf.home.domain.model.PubItem
import com.kaanf.home.domain.model.PubItemAccent
import com.kaanf.home.presentation.pub.component.PubItemCard
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import org.koin.compose.viewmodel.koinViewModel

private val PubTitleColor = Color(0xFFC4B59D)
private val PubDescBorderColor = Color(0x66333333)

@Composable
fun PubRoot(
    viewModel: PubViewModel = koinViewModel(),
    onLoadingChanged: (Boolean) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    androidx.compose.runtime.LaunchedEffect(state.isLoading) {
        onLoadingChanged(state.isLoading)
    }

    PubScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}

@Composable
fun PubScreen(
    state: PubState,
    onAction: (PubAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AccessDefaults.LoadingScreenBackground),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            item {
                PubHeader(user = state.user)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            items(
                items = state.items,
                key = { it.id },
            ) { item ->
                PubItemCard(
                    item = item,
                    onActionClick = { onAction(PubAction.OnItemActionClick(item.id)) },
                    modifier = Modifier.padding(horizontal = 24.dp),
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun PubHeader(user: User?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            user?.let {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    ResourceBadge(
                        value = it.energy,
                        type = ResourceBadgeType.Bounty,
                        badge = ResourceBadgeIcon.XP
                    )

                    ResourceBadge(
                        value = it.gold,
                        type = ResourceBadgeType.Bounty,
                        badge = ResourceBadgeIcon.Gold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "KÖR KEDİ\nMEYHANESİ",
                style = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal,
                    fontSize = 36.sp,
                    lineHeight = 40.sp,
                    letterSpacing = 5.4.sp,
                    color = PubTitleColor,
                    textAlign = TextAlign.Center,
                ),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0x4D111111),
                                Color.Transparent,
                            ),
                        ),
                    )
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                PubDescBorderColor,
                                Color.Transparent,
                            ),
                        ),
                        shape = androidx.compose.ui.graphics.RectangleShape,
                    )
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "İçerisi ucuz tütün, rutubet ve çaresizlik kokuyor. " +
                        "Barmen kirli bir bezi bardağın içinde çevirirken göz ucuyla seni süzüyor. " +
                        "Bir şeyler içerek gücünü toplayabilir ya da etraftaki fısıltılara kulak kabartabilirsin.",
                    style = TextStyle(
                        fontFamily = SpecialElite,
                        fontSize = 11.sp,
                        lineHeight = 22.sp,
                        letterSpacing = 1.1.sp,
                        color = AccessDefaults.SupportingText,
                        textAlign = TextAlign.Center,
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview
@Composable
private fun Preview() {
    DetectiveAiStoriesTheme {
        PubScreen(
            state = PubState(
                user = User(
                    id = "1",
                    email = "test@test.com",
                    fullName = "RENO, V.",
                    profileImageUrl = "",
                    gold = 1000,
                    energy = 10,
                    xp = 0,
                ),
                items = listOf(
                    PubItem(
                        id = "1",
                        name = "EKSİMİŞ BİRA",
                        subtitle = "+20 ENERJİ VERİR",
                        description = "Tadı paslı bir borudan akmış gibi. Boğazı temizler, dedikoduları dinlemeye bahanedir.",
                        energyBonus = 20,
                        actionCost = 3,
                        actionLabel = "SÖYLENTİ DİNLE",
                        accentType = PubItemAccent.GOLD,
                    ),
                    PubItem(
                        id = "2",
                        name = "KAÇAK VİSKİ",
                        subtitle = "+50 ENERJİ VERİR",
                        description = "Genzini yakıp kavuruyor. Sadece en umutsuzların ve barmene konuşmak isteyenlerin tercihi.",
                        energyBonus = 50,
                        actionCost = 5,
                        actionLabel = "BARMENİ SIKIŞTIR",
                        accentType = PubItemAccent.RED,
                    ),
                ),
            ),
            onAction = {},
        )
    }
}
