package com.kaanf.core.domain.model.user

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val profileImageUrl: String,
    val gameToken: Int,
    val energy: Int
) {
    val isCharacterCreated: Boolean
        get() = fullName.isNotEmpty()
}
