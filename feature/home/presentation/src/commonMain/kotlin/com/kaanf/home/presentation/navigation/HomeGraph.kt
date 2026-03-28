package com.kaanf.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kaanf.home.presentation.createcharacter.CreateCharacterRoot

fun NavGraphBuilder.homeGraph(
    navController: NavController
) {
    navigation< HomeGraphRoutes.Graph>(
        startDestination = HomeGraphRoutes.CreateCharacter
    ) {
        composable<HomeGraphRoutes.CreateCharacter> {
            CreateCharacterRoot()
        }
    }
}
