package com.example.toptracercase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toptracercase.login.LoginScreen
import com.example.toptracercase.welcome.WelcomeScreen

@Composable
fun TopTracerNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Login.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navigateToWelcome = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(onLogout = { navController.navigate(Screen.Login.route) })
        }
    }
}

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Welcome : Screen("welcome")
}