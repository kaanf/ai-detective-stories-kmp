package com.kaanf.character.data.mapper

import com.kaanf.character.data.dto.UserAvatarListSerializable
import com.kaanf.character.data.dto.UserAvatarSerializable
import com.kaanf.character.domain.model.avatar.UserAvatar
import com.kaanf.character.domain.model.avatar.UserAvatarList

fun UserAvatarListSerializable.toDomain(): UserAvatarList {
    return UserAvatarList(
        images = this@toDomain.images.map { avatar ->
            avatar.toDomain()
        }
    )
}

fun UserAvatarSerializable.toDomain(): UserAvatar {
    return UserAvatar(
        id = this@toDomain.id,
        avatarImageUrl = this@toDomain.imageUrl
    )
}
