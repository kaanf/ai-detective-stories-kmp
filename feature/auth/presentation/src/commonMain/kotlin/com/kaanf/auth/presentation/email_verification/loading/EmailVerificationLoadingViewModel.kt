package com.kaanf.auth.presentation.email_verification.loading

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaanf.core.domain.auth.AuthService
import com.kaanf.core.domain.util.DataError
import com.kaanf.core.domain.util.Result
import com.kaanf.core.presentation.util.toUiText
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmailVerificationLoadingViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val verificationToken = savedStateHandle.get<String>("token")

    private val eventChannel = Channel<EmailVerificationLoadingEvent>()
    val events = eventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(EmailVerificationLoadingState())
    val state = _state.asStateFlow()

    init {
        startVerification()
    }

    private fun startVerification() {
        viewModelScope.launch {
            completeWithPhase(EmailVerificationLoadingPhase.Verified)
        }
    }

    private suspend fun completeWithFailure(error: DataError.Remote) {
        completeWithPhase(EmailVerificationLoadingPhase.Failed)
        eventChannel.send(EmailVerificationLoadingEvent.Message(error.toUiText()))
    }

    private suspend fun completeWithPhase(phase: EmailVerificationLoadingPhase) {
        while (_state.value.progress < 1f) {
            delay(50L)
            _state.update { current ->
                current.copy(
                    progress = (current.progress + 0.04f).coerceAtMost(1f),
                )
            }
        }

        _state.update { current ->
            current.copy(
                phase = phase,
                progress = 1f,
            )
        }
    }

    private fun nextPendingProgress(currentProgress: Float): Float {
        val increment =
            when {
                currentProgress < 0.32f -> 0.045f
                currentProgress < 0.68f -> 0.026f
                else -> 0.012f
            }

        return (currentProgress + increment).coerceAtMost(0.93f)
    }
}
