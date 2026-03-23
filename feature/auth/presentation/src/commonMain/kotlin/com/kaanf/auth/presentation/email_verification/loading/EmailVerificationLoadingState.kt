package com.kaanf.auth.presentation.email_verification.loading

data class EmailVerificationLoadingState(
    val phase: EmailVerificationLoadingPhase = EmailVerificationLoadingPhase.Verifying,
    val progress: Float = 0.08f,
)

enum class EmailVerificationLoadingPhase {
    Verifying,
    Verified,
    Failed,
}
