package com.kaanf.auth.presentation.email_verification.verification_sent

data class EmailVerificationSentState(
    val registeredEmail: String = "",
    val isResending: Boolean = false,
    val isResendEnabled: Boolean = false
)
