package com.kaanf.auth.presentation.email_verification.verification_sent

sealed interface EmailVerificationSentAction {
    data object OnReturnToLoginClicked : EmailVerificationSentAction

    data object OnResendSignalClicked : EmailVerificationSentAction
}
