package com.rishabh.foodiesclub.features.restaurants.data.mappers

import com.rishabh.foodiesclub.features.restaurants.data.remote.dto.DealDto
import com.rishabh.foodiesclub.features.restaurants.data.remote.dto.RestaurantDto
import com.rishabh.foodiesclub.features.restaurants.domain.model.Deal
import com.rishabh.foodiesclub.features.restaurants.domain.model.Restaurant

fun RestaurantDto.toDomain(): Restaurant {
    return Restaurant(
        id = objectId,
        name = name,
        address = address1,
        suburb = suburb,
        cuisines = cuisines,
        imageUrl = imageLink,
        openTime = open,
        closeTime = close,
        deals = deals.map { it.toDomain() }
    )
}

fun DealDto.toDomain(): Deal {
    return Deal(
        id = objectId,
        discountPercent = discount.toIntOrNull() ?: 0,
        isDineIn = dineIn.toBoolean(),
        isLightningDeal = lightning.toBoolean(),
        startTime = open ?: start,
        endTime = close ?: end,
        quantityLeft = qtyLeft.toIntOrNull() ?: 0
    )
}
