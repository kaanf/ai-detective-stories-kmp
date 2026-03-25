package com.kaanf.auth.presentation.email_verification.verification_result

data class EmailVerificationResultState(
    val phase: EmailVerificationPhase = EmailVerificationPhase.Verifying,
)

enum class EmailVerificationPhase {
    Verifying,
    Verified,
    Failed,
}
