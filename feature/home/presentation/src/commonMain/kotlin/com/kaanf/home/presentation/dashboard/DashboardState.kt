package com.kaanf.home.presentation.dashboard

import com.kaanf.core.domain.model.user.User
import com.kaanf.home.domain.model.Case

data class DashboardState(
    val user: User? = null,
    val cases: List<Case> = emptyList(),
    val isLoading: Boolean = false,
)
