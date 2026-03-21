package com.kaanf.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.auth.AuthService
import com.kaanf.core.domain.util.Result
import com.kaanf.core.presentation.util.UIText
import com.kaanf.core.presentation.util.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import detective_ai_stories.feature.auth.presentation.generated.resources.Res
import detective_ai_stories.feature.auth.presentation.generated.resources.login_unavailable_message

class RegisterViewModel(
    private val authService: AuthService,
) : ViewModel() {
    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(RegisterState())
    val state =
        _state
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = RegisterState(),
            )

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnNextClick -> {

            }

            RegisterAction.OnStartClick -> {
            }

            RegisterAction.OnTerminalRegisterClick -> submitRegistration()

            RegisterAction.OnRegisterClick -> submitRegistration()

            RegisterAction.OnBackClick -> {

            }

            RegisterAction.OnLoginClick -> {
                viewModelScope.launch {
                    eventChannel.send(
                        RegisterEvent.Message(
                            UIText.Resource(Res.string.login_unavailable_message),
                        ),
                    )
                }
            }

            RegisterAction.OnTogglePasswordVisibilityClick -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            else -> {
                // Don't analyze this field.
            }
        }
    }

    private fun submitRegistration() {
        if (_state.value.isRegistering) {
            return
        }

        if (!_state.value.isPasswordValid) {
            return
        }

        register()
    }

    private fun register() = viewModelScope.launch {
        val currentState = _state.value

        _state.update {
            it.copy(isRegistering = true)
        }

        try {
            when (
                val result = authService.register(
                    email = currentState.emailTextState.text.toString(),
                    password = currentState.passwordTextState.text.toString(),
                )
            ) {
                is Result.Success -> {
                    eventChannel.send(
                        RegisterEvent.Success(
                            email = currentState.emailTextState.text.toString(),
                        ),
                    )
                }

                is Result.Failure -> {
                    eventChannel.send(
                        RegisterEvent.Message(
                            result.error.toUiText(),
                        ),
                    )
                }
            }
        } finally {
            _state.update {
                it.copy(isRegistering = false)
            }
        }
    }
}
