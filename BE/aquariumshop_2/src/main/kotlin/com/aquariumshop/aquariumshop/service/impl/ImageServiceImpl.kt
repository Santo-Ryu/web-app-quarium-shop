package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.service.ImageService
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.nio.file.Paths

@Service
class ImageServiceImpl: ImageService {

    override fun getImage(name: String): ResponseEntity<Any> {
        return try {
            val STORAGE_PATH = "uploads/"
            val imagePath = Paths.get(STORAGE_PATH, name).normalize()

            val uploadsRoot = Paths.get(STORAGE_PATH).normalize()
            if (!imagePath.startsWith(uploadsRoot)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(mapOf("message" to "Invalid path"))
            }

            val resource = UrlResource(imagePath.toUri())
            if (resource.exists()) {
                ResponseEntity.ok()
                    .contentType(getMediaType(name))
                    .body(resource)
            } else {
                println("IMAGE: $resource")
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf("message" to "Image not found"))
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("message" to "Error loading image"))
        }
    }

    override fun getMediaType(filename: String): MediaType {
        val extension = filename.substringAfterLast('.', "").lowercase()

        return when (extension) {
            "jpg", "jpeg" -> MediaType.IMAGE_JPEG
            "png"         -> MediaType.IMAGE_PNG
            "gif"         -> MediaType.IMAGE_GIF
            else          -> MediaType.APPLICATION_OCTET_STREAM
        }
    }
}