package com.kaanf.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navDeepLink
import com.kaanf.auth.presentation.emailverification.verificationresult.EmailVerificationResultRoot
import com.kaanf.auth.presentation.emailverification.verificationsent.EmailVerificationSentRoot
import com.kaanf.auth.presentation.forgotpassword.ForgotPasswordRoot
import com.kaanf.auth.presentation.login.LoginRoot
import com.kaanf.auth.presentation.register.RegisterRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
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
                        AuthGraphRoutes.EmailVerificationSent(it),
                    )
                },
                onReturnToLoginClick = {
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
        composable<AuthGraphRoutes.ForgotPassword> {
            ForgotPasswordRoot()
        }
        composable<AuthGraphRoutes.EmailVerificationSent> {
            EmailVerificationSentRoot(
                onReturnToLoginClick = {
                    navController.popBackStack(AuthGraphRoutes.Login, inclusive = false)
                },
            )
        }
        composable<AuthGraphRoutes.EmailVerificationResult>(
            deepLinks =
                listOf(
                    navDeepLink {
                        this.uriPattern = "https://ads.kaanf.com/api/notification/activate-user?token={token}"
                    },
                    navDeepLink {
                        this.uriPattern = "ads://ads.kaanf.com/api/notification/activate-user?token={token}"
                    },
                ),
        ) {
            EmailVerificationResultRoot(
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
    }
}
