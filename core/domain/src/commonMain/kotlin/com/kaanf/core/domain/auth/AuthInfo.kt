package com.kaanf.core.domain.auth

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
)
