package com.kaanf.core.data.mappers

import com.kaanf.core.data.dto.AuthInfoSerializable
import com.kaanf.core.data.dto.UserSerializable
import com.kaanf.core.domain.model.auth.AuthInfo
import com.kaanf.core.domain.model.user.User

fun AuthInfoSerializable.toDomain(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user?.toDomain()
    )
}

fun UserSerializable.toDomain(): User {
    return User(
        id = id,
        email = email,
        fullName = fullName,
        profileImageUrl = profileImageUrl,
        gameToken = gameToken,
        energy = energy
    )
}

fun User.toSerializable(): UserSerializable {
    return UserSerializable(
        id = id,
        email = email,
        fullName = fullName,
        profileImageUrl = profileImageUrl,
        gameToken = gameToken,
        energy = energy
    )
}

fun AuthInfo.toSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        user = user?.toSerializable()
    )
}
