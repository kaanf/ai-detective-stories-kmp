package com.kaanf.auth.presentation.register

sealed interface RegisterAction {
    data object OnNextClick : RegisterAction

    data object OnStartClick : RegisterAction

    data object OnTerminalRegisterClick : RegisterAction

    data object OnBackClick : RegisterAction

    data object OnLoginClick : RegisterAction

    data object OnInputTextFocusGain : RegisterAction

    data object OnRegisterClick : RegisterAction

    data object OnTogglePasswordVisibilityClick : RegisterAction
}
