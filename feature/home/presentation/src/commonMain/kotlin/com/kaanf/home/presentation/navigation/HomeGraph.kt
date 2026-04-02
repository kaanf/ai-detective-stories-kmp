package com.kaanf.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kaanf.home.presentation.HomeScreenRoot
import com.kaanf.home.presentation.HomeScreenRoute
import com.kaanf.home.presentation.dashboard.DashboardRoot

fun NavGraphBuilder.homeGraph(
    navController: NavController,
) {
    navigation<HomeGraphRoutes.Graph>(
        startDestination = HomeScreenRoute,
    ) {
        composable<HomeScreenRoute> {
            HomeScreenRoot()
        }
    }
}
