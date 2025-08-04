package com.rishabh.foodiesclub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rishabh.foodiesclub.features.restaurants.ui.detail.RestaurantDetailScreen
import com.rishabh.foodiesclub.features.restaurants.ui.list.RestaurantListScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.RestaurantList.route) {
        composable(Screen.RestaurantList.route) {
            RestaurantListScreen(
                onRestaurantClick = { restaurantId ->
                    navController.navigate(Screen.RestaurantDetail.createRoute(restaurantId))
                }
            )
        }
        composable(
            route = Screen.RestaurantDetail.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) {
            RestaurantDetailScreen()
        }
    }
}
