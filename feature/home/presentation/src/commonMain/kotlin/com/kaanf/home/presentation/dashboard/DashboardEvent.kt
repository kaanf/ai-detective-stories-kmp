package com.kaanf.home.presentation.dashboard

sealed interface DashboardEvent {
    data class ShowSnackbar(val snackbarMessage: String) : DashboardEvent
}
