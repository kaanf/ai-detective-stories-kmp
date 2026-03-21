package com.kaanf.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.kaanf.auth.presentation.email_verification.EmailVerificationRoot
import com.kaanf.auth.presentation.login.LoginRoot
import com.kaanf.auth.presentation.register.RegisterRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
) {
    navigation<AuthGraphRoutes.Graph>(
        startDestination = AuthGraphRoutes.Login,
    ) {
        composable<AuthGraphRoutes.Login> {
            LoginRoot(
                onRegisterClick = {
                    navController.navigate(AuthGraphRoutes.Register) {
                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }
        composable<AuthGraphRoutes.Register> {
            RegisterRoot(
                onRegisterSuccess = {
                    navController.navigate(
                        AuthGraphRoutes.RegisterVerification(it),
                    )
                },
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        composable<AuthGraphRoutes.RegisterVerification> { backStackEntry ->
            val route = backStackEntry.toRoute<AuthGraphRoutes.RegisterVerification>()

            EmailVerificationRoot(
                email = route.email,
                onReturnToTerminal = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks = listOf(
                navDeepLink {
                    this.uriPattern = "https://api.kaanf.com/api/auth/verify?token={token}"
                },
                navDeepLink {
                    this.uriPattern = "detectiveaistories://api.kaanf.com/api/auth/verify?token={token}"
                },
            )
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<AuthGraphRoutes.EmailVerification>()

            EmailVerificationRoot(
                token = route.token,
                onReturnToTerminal = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Graph) {
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
