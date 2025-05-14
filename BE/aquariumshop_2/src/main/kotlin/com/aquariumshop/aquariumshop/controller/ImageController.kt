package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.request.UpdateImageRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.service.impl.ImageServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api")
class ImageController(
    val imageServiceImpl: ImageServiceImpl
) {
    @PostMapping("/image/update_image")
    fun updateImage(
        @RequestParam id: Long,
        @RequestParam type: String,
        @RequestPart file: MultipartFile
    ): ResponseEntity<APIResponse<String>> {
        return imageServiceImpl.updateImage(id, type, file)
    }

}