package com.kaanf.character.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserAvatarListSerializable(
    val images: List<UserAvatarSerializable>
)

@Serializable
data class UserAvatarSerializable(
    val id: String,
    val imageUrl: String
)
