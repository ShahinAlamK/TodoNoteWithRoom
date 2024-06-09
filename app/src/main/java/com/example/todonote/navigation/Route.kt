package com.example.todonote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todonote.ui.screens.DetailScreen
import com.example.todonote.ui.screens.HomeScreen

@Composable
fun Route(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Home.route) {

        composable(Destination.Home.route) {
            HomeScreen(
                navigateToDetail = { id ->
                    navController.navigate("${Destination.Details.route}/$id")
                }
            )
        }
        composable("${Destination.Details.route}/{id}", arguments = listOf(navArgument("id") {
            type = NavType.IntType
        })) {
            val id = it.arguments?.getInt("id")
            DetailScreen(
                navigateToBack = {
                    navController.popBackStack()
                }
            )
        }
    }

}