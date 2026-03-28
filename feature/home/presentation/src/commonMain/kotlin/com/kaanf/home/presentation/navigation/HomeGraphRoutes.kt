package com.kaanf.home.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface HomeGraphRoutes {
    @Serializable
    data object Graph: HomeGraphRoutes
}
