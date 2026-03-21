package com.kaanf.detectiveaistories

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kaanf.core.designsystem.theme.DetectiveAiStoriesTheme
import com.kaanf.detectiveaistories.navigation.DeepLinkListener
import com.kaanf.detectiveaistories.navigation.NavigationRoot
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    val isDarkTheme = isSystemInDarkTheme()
    DeepLinkListener(navController)

    DetectiveAiStoriesTheme(isDarkTheme = true) {
        SystemBarsEffect(isDarkTheme = true)
        NavigationRoot(navController)
    }
}
