package com.rishabh.foodiesclub.features.restaurants.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DealDto(
    @Json(name = "objectId") val objectId: String,
    @Json(name = "discount") val discount: String,
    @Json(name = "dineIn") val dineIn: String,
    @Json(name = "lightning") val lightning: String,
    @Json(name = "open") val open: String,
    @Json(name = "close") val close: String,
    @Json(name = "qtyLeft") val qtyLeft: String
)