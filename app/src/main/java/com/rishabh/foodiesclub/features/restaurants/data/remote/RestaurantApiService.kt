package com.rishabh.foodiesclub.features.restaurants.data.remote

import com.rishabh.foodiesclub.features.restaurants.data.remote.dto.RestaurantResponse
import retrofit2.http.GET

interface RestaurantApiService {
    @GET("misc/challengedata.json")
    suspend fun getRestaurants(): RestaurantResponse
}
