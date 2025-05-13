package com.aquariumshop.aquariumshop.service

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

interface ImageService {

    fun getImage(name: String): ResponseEntity<Any>

    fun getMediaType(name: String): MediaType
}