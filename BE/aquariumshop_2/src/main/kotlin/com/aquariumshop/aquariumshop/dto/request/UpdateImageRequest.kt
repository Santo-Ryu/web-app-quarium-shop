package com.aquariumshop.aquariumshop.dto.request

import org.springframework.web.multipart.MultipartFile

data class UpdateImageRequest(
    val id: Long,
    val file: MultipartFile,
    val type: String // user_image or product_image
)