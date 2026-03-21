package com.kaanf.core.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UsernameRequest(
    val username: String,
)
