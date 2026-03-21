package com.kaanf.auth.presentation.email_verification

data class EmailVerificationState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)
