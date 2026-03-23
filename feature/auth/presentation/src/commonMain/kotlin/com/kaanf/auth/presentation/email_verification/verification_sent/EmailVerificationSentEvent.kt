package com.kaanf.auth.presentation.email_verification.verification_sent

import com.kaanf.core.presentation.util.UIText

sealed interface EmailVerificationSentEvent {
    data class Message(val message: UIText) : EmailVerificationSentEvent

    data object NavigateToTerminal : EmailVerificationSentEvent
}
