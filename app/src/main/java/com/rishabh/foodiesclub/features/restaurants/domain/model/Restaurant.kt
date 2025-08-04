package com.rishabh.foodiesclub.features.restaurants.domain.model

data class Restaurant(
    val id: String,
    val name: String,
    val address: String,
    val suburb: String,
    val cuisines: List<String>,
    val imageUrl: String,
    val openTime: String?,
    val closeTime: String?,
    val deals: List<Deal>
)
