package com.kaanf.auth.presentation.emailverification.verificationsent

data class EmailVerificationSentState(
    val registeredEmail: String = "",
    val isResending: Boolean = false,
    val isResendEnabled: Boolean = false,
    val resendCountdownSeconds: Int = 60,
)
