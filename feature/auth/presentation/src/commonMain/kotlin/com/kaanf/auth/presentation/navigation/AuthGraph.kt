package com.kaanf.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.kaanf.auth.presentation.email_verification.loading.EmailVerificationLoadingRoot
import com.kaanf.auth.presentation.email_verification.verification_sent.EmailVerificationSentRoot
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
        composable<AuthGraphRoutes.RegisterVerification> {
            EmailVerificationSentRoot(
                onLoginClick = {
                    navController.navigate(AuthGraphRoutes.Login) {
                        popUpTo(AuthGraphRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<AuthGraphRoutes.EmailVerification>(
            deepLinks = listOf(
                navDeepLink {
                    this.uriPattern = "https://ads.kaanf.com/api/notification/activate-user?token={token}"
                },
                navDeepLink {
                    this.uriPattern = "detectiveaistories://ads.kaanf.com/api/notification/activate-user?token={token}"
                },
            )
        ) {
            EmailVerificationLoadingRoot()
        }
    }
}
