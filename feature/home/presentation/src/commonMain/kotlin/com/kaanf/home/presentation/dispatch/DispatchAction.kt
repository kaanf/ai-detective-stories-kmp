package com.kaanf.home.presentation.dispatch

sealed interface DispatchAction {
    data class OnPickCase(val caseId: String) : DispatchAction
}
