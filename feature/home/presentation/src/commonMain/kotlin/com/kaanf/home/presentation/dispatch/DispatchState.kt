package com.kaanf.home.presentation.dispatch

import com.kaanf.home.domain.model.TemporaryCase

data class DispatchState(
    val cases: List<TemporaryCase> = emptyList(),
    val remainingSeconds: Int = 0,
    val isLoading: Boolean = false,
)
