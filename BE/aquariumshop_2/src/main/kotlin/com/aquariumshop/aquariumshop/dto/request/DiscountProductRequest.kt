package com.aquariumshop.aquariumshop.dto.request

import java.util.Date

data class DiscountProductRequest (
    val id: Long = 0,
    val name: String ? = null,
    val image: String ? = null,
    val discountPercentage: Int = 0,
    val discountStartDate: Date? = null,
    val discountEndDate: Date? = null
)