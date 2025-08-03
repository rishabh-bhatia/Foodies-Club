package com.rishabh.foodiesclub.features.restaurants.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantResponse(
    @Json(name = "restaurants") val restaurants: List<RestaurantDto>
)
