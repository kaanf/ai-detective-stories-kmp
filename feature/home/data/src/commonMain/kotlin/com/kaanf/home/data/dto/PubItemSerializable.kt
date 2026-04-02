package com.kaanf.home.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PubItemSerializable(
    val id: String,
    val name: String,
    val subtitle: String,
    val description: String,
    val energyBonus: Int,
    val actionCost: Int,
    val actionLabel: String,
    val accentType: String,
)
