package com.kaanf.home.presentation.pub

import com.kaanf.core.domain.model.user.User
import com.kaanf.home.domain.model.PubItem
import com.kaanf.home.domain.model.PubItemAccent

data class PubState(
    val user: User? = null,
    val items: List<PubItem> = listOf(
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
    val isLoading: Boolean = false,
)
