package com.kaanf.auth.presentation.register

import com.kaanf.core.presentation.util.UIText

sealed interface RegisterEvent {
    data class Failure(val message: UIText) : RegisterEvent

    data class Message(val message: UIText) : RegisterEvent

    data class Success(val email: String) : RegisterEvent

    data object NavigateToLogin : RegisterEvent
}
