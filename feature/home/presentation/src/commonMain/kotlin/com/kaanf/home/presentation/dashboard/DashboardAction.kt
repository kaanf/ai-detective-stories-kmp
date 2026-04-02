package com.kaanf.home.presentation.dashboard

sealed interface DashboardAction {
    data class OnCaseClick(val caseId: String) : DashboardAction
}
