package com.kaanf.character.domain.model.avatar

data class UserAvatarList(
    val images: List<UserAvatar>
)

data class UserAvatar(
    val id: String,
    val avatarImageUrl: String
)

