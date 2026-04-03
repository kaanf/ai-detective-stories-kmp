package com.kaanf.home.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BountySerializable(
    val xp: Int? = null,
    val gold: Int? = null,
)
