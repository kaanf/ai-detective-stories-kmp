package com.kaanf.auth.presentation.login

import com.kaanf.core.presentation.base.BaseEvent

sealed interface LoginEvent : BaseEvent {
    data object NavigateToRegister : LoginEvent
}
