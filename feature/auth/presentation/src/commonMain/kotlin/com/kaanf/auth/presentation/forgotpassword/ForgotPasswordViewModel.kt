package com.kaanf.auth.presentation.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.auth.AuthService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ForgotPasswordViewModel(
    private val authService: AuthService
) : ViewModel() {
    private val eventChannel = Channel<ForgotPasswordEvent>()
    val events
        get() = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ForgotPasswordState())
    val state =
        _state
            .onStart {
                if (!hasLoadedInitialData) {

                    hasLoadedInitialData = true
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = ForgotPasswordState(),
            )

    fun onAction(action: ForgotPasswordAction) {

    }
}
