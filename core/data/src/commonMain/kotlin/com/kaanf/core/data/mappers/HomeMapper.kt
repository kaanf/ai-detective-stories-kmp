package com.kaanf.core.data.mappers

import com.kaanf.core.data.dto.AuthInfoSerializable
import com.kaanf.core.data.dto.AvatarSerializable
import com.kaanf.core.data.dto.AvatarsSerializable
import com.kaanf.core.domain.home.UserAvatar
import com.kaanf.core.domain.home.UserAvatarList

fun AvatarsSerializable.toDomain(): UserAvatarList {
    return UserAvatarList(
        images = this@toDomain.images.map { avatar ->
            avatar.toDomain()
        }
    )
}

fun AvatarSerializable.toDomain(): UserAvatar {
    return UserAvatar(
        id = this@toDomain.id,
        avatarImageUrl = this@toDomain.imageUrl
    )
}
