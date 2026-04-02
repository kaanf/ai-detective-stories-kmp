package com.kaanf.home.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface HomeGraphRoutes {
    @Serializable
    data object Graph : HomeGraphRoutes
}

@Serializable
data object DashboardRoute

@Serializable
data object InformantsRoute

@Serializable
data object SecretaryRoute

@Serializable
data object BarRoute

@Serializable
data object ProfileRoute
