package com.kaanf.auth.presentation.login

import com.kaanf.core.presentation.util.UIText

sealed interface LoginEvent {
    data object Success : LoginEvent

    data class Failure(val message: UIText) : LoginEvent

    data object NavigateToRegister : LoginEvent
}
