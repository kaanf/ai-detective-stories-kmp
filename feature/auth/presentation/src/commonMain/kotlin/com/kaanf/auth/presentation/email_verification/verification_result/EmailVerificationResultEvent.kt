package com.kaanf.auth.presentation.email_verification.verification_result

import com.kaanf.core.presentation.util.UIText

sealed interface EmailVerificationResultEvent {
    data class Message(val message: UIText) : EmailVerificationResultEvent

    data object NavigateToResult : EmailVerificationResultEvent
}
