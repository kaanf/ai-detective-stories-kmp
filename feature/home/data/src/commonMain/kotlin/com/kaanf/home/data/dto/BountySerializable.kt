package com.kaanf.home.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class BountySerializable(
    val energy: Int? = null,
    val gold: Double? = null,
)
