package com.kaanf.auth.presentation.email_verification.verification_result

data class EmailVerificationResultState(
    val paramOne: String = "default",
    val paramTwo: List<String> = emptyList(),
)
