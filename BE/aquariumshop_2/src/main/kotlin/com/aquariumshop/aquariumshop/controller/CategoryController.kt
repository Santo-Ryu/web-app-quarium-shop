package com.aquariumshop.aquariumshop.controller

import com.aquariumshop.aquariumshop.dto.response.APIResponse
import com.aquariumshop.aquariumshop.dto.response.ResponseFactory
import com.aquariumshop.aquariumshop.model.entity.Category
import com.aquariumshop.aquariumshop.repository.CategoryRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class CategoryController(
    val categoryRepository: CategoryRepository
) {
    @PostMapping("/admin/delete_category")
    fun deletedCategory(@RequestParam id: Long): ResponseEntity<APIResponse<Any>> {
        val row = categoryRepository.deleteById(id)
        return ResponseFactory.success(row, "Xóa thành công!")
    }

    @PostMapping("/admin/add_category")
    fun addCategory(@RequestParam categoryName: String): ResponseEntity<APIResponse<Any>> {
        val categoryFind = categoryRepository.findByCategory(categoryName)

        if (categoryFind != null) {
            return ResponseFactory.badRequest("Danh mục đã tồn tại!")
        }

        val newCategory = Category()
        newCategory.category = categoryName
        categoryRepository.save(newCategory)

        return ResponseFactory.success(1, "Thêm thành công!")
    }

    @PostMapping("/admin/update_category")
    fun updateCategory(
        @RequestParam newCategoryName: String,
        @RequestParam id: Long
    ): ResponseEntity<APIResponse<Any>> {
        val result = categoryRepository.findById(id).orElseThrow { RuntimeException("Lỗi") }
        result.category = newCategoryName
        categoryRepository.save(result)
        return ResponseFactory.success(1, "Thêm thành công!")
    }
}