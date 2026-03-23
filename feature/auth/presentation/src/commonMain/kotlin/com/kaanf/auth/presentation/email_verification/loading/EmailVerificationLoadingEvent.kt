package com.kaanf.auth.presentation.email_verification.loading

import com.kaanf.core.presentation.util.UIText

sealed interface EmailVerificationLoadingEvent {
    data class Message(val message: UIText) : EmailVerificationLoadingEvent
}
