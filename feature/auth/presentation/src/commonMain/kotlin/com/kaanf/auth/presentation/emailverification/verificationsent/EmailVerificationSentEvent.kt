package com.kaanf.auth.presentation.emailverification.verificationsent

import com.kaanf.core.presentation.util.UIText

sealed interface EmailVerificationSentEvent {
    data class Success(val message: UIText) : EmailVerificationSentEvent

    data class Failure(val message: UIText) : EmailVerificationSentEvent

    data object NavigateToLogin : EmailVerificationSentEvent
}
