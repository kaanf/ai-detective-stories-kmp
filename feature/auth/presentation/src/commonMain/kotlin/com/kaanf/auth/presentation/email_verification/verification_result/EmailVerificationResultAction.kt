package com.kaanf.auth.presentation.email_verification.verification_result


sealed interface EmailVerificationResultAction {
    data object OnReturnToLoginClicked : EmailVerificationResultAction

    data object OnResendSignalClicked : EmailVerificationResultAction
}

