package com.kaanf.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation< HomeGraphRoutes.Graph>(
        startDestination = HomeGraphRoutes.Graph
    ) {

    }
}
