package com.rishabh.foodiesclub.features.restaurants.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RestaurantDto(
    @Json(name = "objectId") val objectId: String,
    @Json(name = "name") val name: String,
    @Json(name = "address1") val address1: String,
    @Json(name = "suburb") val suburb: String,
    @Json(name = "cuisines") val cuisines: List<String>,
    @Json(name = "imageLink") val imageLink: String,
    @Json(name = "open") val open: String,
    @Json(name = "close") val close: String,
    @Json(name = "deals") val deals: List<DealDto>
)