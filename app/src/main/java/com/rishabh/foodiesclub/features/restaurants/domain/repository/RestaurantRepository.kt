package com.rishabh.foodiesclub.features.restaurants.domain.repository

import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    fun getRestaurants(): Flow<List<Restaurant>>
}
