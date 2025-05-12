package com.aquariumshop.aquariumshop.service.support

sealed class Recipient {
    data class Admin(val id: Long) : Recipient()
    data class Customer(val id: Long) : Recipient()
}