package com.kaanf.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSerializable(
    @SerialName("_id")
    val id: String,
    val email: String,
    val fullName: String,
    @SerialName("profileImage")
    val profileImageUrl: String,
    val gameToken: Int,
    val energy: Int
)
