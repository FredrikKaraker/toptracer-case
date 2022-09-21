package com.example.toptracercase.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.toptracercase.login.LoginScreen

@Composable
fun TopTracerNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "login"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                navigateToGif = { navController.navigate("gif") },
            )
        }
        composable("gif") { GifScreen() }
    }
}

@Composable
private fun GifScreen() {
    Box {
        Text("GIF HERE!")
    }
}