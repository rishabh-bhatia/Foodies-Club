package com.rishabh.foodiesclub.navigation

sealed class Screen(val route: String) {
    object RestaurantList : Screen("restaurant_list")
    object RestaurantDetail : Screen("restaurant_detail/{restaurantId}") {
        fun createRoute(restaurantId: String) = "restaurant_detail/$restaurantId"
    }
}
