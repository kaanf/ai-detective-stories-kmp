package com.kaanf.core.domain.auth

import kotlinx.serialization.SerialName

data class User(
    val id: String,
    val email: String,
    val fullName: String,
)
