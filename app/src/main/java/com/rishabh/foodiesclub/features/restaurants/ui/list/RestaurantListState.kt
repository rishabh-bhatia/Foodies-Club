package com.rishabh.foodiesclub.features.restaurants.ui.list

import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant

data class RestaurantListState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val error: String? = null,
    val searchQuery: String = ""
)
