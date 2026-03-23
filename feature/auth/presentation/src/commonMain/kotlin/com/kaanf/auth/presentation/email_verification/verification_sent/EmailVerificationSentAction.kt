package com.kaanf.auth.presentation.email_verification.verification_sent

sealed interface EmailVerificationSentAction {
    data object OnReturnToTerminalClick : EmailVerificationSentAction

    data object OnResendSignalClick : EmailVerificationSentAction
}
