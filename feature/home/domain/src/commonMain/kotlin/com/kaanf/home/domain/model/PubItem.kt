package com.kaanf.home.domain.model

data class PubItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val description: String,
    val energyBonus: Int,
    val actionCost: Int,
    val actionLabel: String,
    val accentType: PubItemAccent,
)

enum class PubItemAccent {
    GOLD,
    RED,
}
