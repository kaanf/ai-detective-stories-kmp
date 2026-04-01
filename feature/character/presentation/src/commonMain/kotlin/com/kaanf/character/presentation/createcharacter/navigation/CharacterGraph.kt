package com.kaanf.character.presentation.createcharacter.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kaanf.character.presentation.createcharacter.CreateCharacterRoot

fun NavGraphBuilder.characterGraph(
    navController: NavController,
    onNavigateToHome: () -> Unit,
) {
    navigation< CharacterGraphRoutes.Graph>(
        startDestination = CharacterGraphRoutes.CreateCharacter
    ) {
        composable<CharacterGraphRoutes.CreateCharacter> {
            CreateCharacterRoot(onNavigateToHome = onNavigateToHome)
        }
    }
}
