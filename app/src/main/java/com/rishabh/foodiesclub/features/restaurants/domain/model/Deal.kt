package com.rishabh.foodiesclub.features.restaurants.domain.model

data class Deal(
    val id: String,
    val discountPercent: Int,
    val isDineIn: Boolean,
    val isLightningDeal: Boolean,
    val startTime: String?,
    val endTime: String?,
    val quantityLeft: Int
)