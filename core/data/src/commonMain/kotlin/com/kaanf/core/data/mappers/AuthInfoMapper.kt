package com.kaanf.core.data.mappers

import com.kaanf.core.data.dto.AuthInfoSerializable
import com.kaanf.core.data.dto.UserSerializable
import com.kaanf.core.domain.auth.AuthInfo
import com.kaanf.core.domain.auth.User

fun AuthInfoSerializable.toDomain(): AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}

fun User.toSerializable(): UserSerializable {
    return UserSerializable(
        id = id,
        email = email,
        fullName = fullName
    )
}

fun AuthInfo.toSerializable(): AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )
}
