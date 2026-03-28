package com.kaanf.detectiveaistories

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.kaanf.auth.presentation.navigation.AuthGraphRoutes
import com.kaanf.character.presentation.createcharacter.navigation.CharacterGraphRoutes
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.detectiveaistories.navigation.DeepLinkListener
import com.kaanf.detectiveaistories.navigation.NavigationRoot
import com.kaanf.home.presentation.navigation.HomeGraphRoutes
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    viewModel: MainViewModel = koinViewModel(),
    onAuthenticationChecked: () -> Unit = {}
) {
    val navController = rememberNavController()
    val isDarkTheme = isSystemInDarkTheme()
    DeepLinkListener(navController)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCheckingAuth) {
        if (!state.isCheckingAuth) {
            onAuthenticationChecked()
        }
    }

    DetectiveAiStoriesTheme(isDarkTheme = true) {
        SystemBarsEffect(isDarkTheme = true)

        if(!state.isCheckingAuth) {
            NavigationRoot(
                navController = navController,
                startDestination = when {
                    !state.isLoggedIn -> {
                        AuthGraphRoutes.Graph
                    }
                    !state.isCharacterCreated -> {
                        CharacterGraphRoutes.Graph
                    }
                    else -> {
                        HomeGraphRoutes.Graph
                    }
                }
            )
        }
    }
}
