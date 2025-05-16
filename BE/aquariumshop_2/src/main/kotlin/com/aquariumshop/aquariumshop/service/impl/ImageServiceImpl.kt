package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.dto.request.UpdateImageRequest
import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.model.entity.ProductImage
import com.aquariumshop.aquariumshop.repository.AdminRepository
import com.aquariumshop.aquariumshop.repository.CustomerRepository
import com.aquariumshop.aquariumshop.repository.ProductImageRepository
import com.aquariumshop.aquariumshop.repository.ProductRepository
import com.aquariumshop.aquariumshop.repository.UserImageRepository
import com.aquariumshop.aquariumshop.service.ImageService
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class ImageServiceImpl(
    val userImageRepository: UserImageRepository,
    val productImageRepository: ProductImageRepository,
    val customerRepository: CustomerRepository,
    val adminRepository: AdminRepository,
    val productRepository: ProductRepository
): ImageService {
    val STORAGE_PATH = "uploads/"

    override fun getImage(name: String): ResponseEntity<Any> {
        return try {
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

    override fun updateImage(id: Long, type: String, file: MultipartFile): ResponseEntity<APIResponse<String>> {
        try {
            val uploadDir = Paths.get(System.getProperty("user.dir"), "uploads")
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir)
            }

            if (type.equals("customer") || type.equals("admin") || type.equals("product")) {
                val timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                val extension = file.originalFilename?.substringAfterLast('.', "")
                val uuid = UUID.randomUUID().toString().substring(0, 8)
                val filename = "${id}_${timeStamp}_${uuid}.${extension}"

                val filePath = uploadDir.resolve(filename)
                file.transferTo(filePath.toFile()) // lưu ảnh

                if (type.equals("customer")) {
                    val customer = customerRepository.findById(id).orElseThrow { RuntimeException("Khách hàng không tồn tại!") }
                    val userImage = userImageRepository.findById(customer.image?.id!!).orElseThrow { RuntimeException("Hình ảnh không tồn tại!") }
                    userImage.name = filename
                    userImageRepository.save(userImage)
                    println("CUSTOMER_SAVE_IMAGE")
                }
                if (type.equals("admin")) {
                    val admin = adminRepository.findById(id).orElseThrow { RuntimeException("Admin không tồn tại!") }
                    val userImage = userImageRepository.findById(admin.image?.id!!).orElseThrow { RuntimeException("Hình ảnh không tồn tại!") }
                    userImage.name = filename
                    userImageRepository.save(userImage)
                    println("ADMIN_SAVE_IMAGE")
                }
                if (type.equals("product")){ // type === product
                    val productImage = ProductImage()
                    productImage.product = productRepository.findById(id).orElseThrow{ RuntimeException("Sản phẩm không tồn tại!") }
                    productImage.name = filename
                    productImageRepository.save(productImage)
                    println("PRODUCT_SAVE_IMAGE: $filename")
                }
            }else println("Type invalid!")

            return ResponseFactory.success("ok", "Tải ảnh thành công!")
        }catch (e: Exception) {
            e.printStackTrace()
            return ResponseFactory.badRequest("Lỗi: ${e.message}")
        }
    }
}