package com.aquariumshop.aquariumshop.service

import com.aquariumshop.aquariumshop.dto.request.UpdateImageRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile

interface ImageService {

    fun getImage(name: String): ResponseEntity<Any>

    fun getMediaType(name: String): MediaType

    fun updateImage(id: Long, type: String, file: MultipartFile): ResponseEntity<APIResponse<String>>
}