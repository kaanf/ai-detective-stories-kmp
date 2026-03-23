package com.kaanf.auth.presentation.email_verification.verification_sent

data class EmailVerificationSentState(
    val isVerifying: Boolean = false,
    val isVerified: Boolean = false,
    val isResending: Boolean = false,
    val isResendEnabled: Boolean = false
)
