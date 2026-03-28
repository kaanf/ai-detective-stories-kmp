package com.kaanf.core.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AvatarsSerializable(
    val images: List<AvatarSerializable>
)

@Serializable
data class AvatarSerializable(
    val id: String,
    val imageUrl: String
)
