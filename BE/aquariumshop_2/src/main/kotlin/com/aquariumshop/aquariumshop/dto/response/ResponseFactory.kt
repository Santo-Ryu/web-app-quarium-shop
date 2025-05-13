package com.aquariumshop.aquariumshop.dto.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseFactory {
    fun <T> success(data: T, message: String = "Success"): ResponseEntity<APIResponse<T>> {
        return ResponseEntity(
            APIResponse(ResponseCode.SUCCESS, message, data),
            HttpStatus.OK
        )
    }

    fun created(message: String = "Created"): ResponseEntity<APIResponse<Nothing>> {
        return ResponseEntity(
            APIResponse(ResponseCode.CREATED, message),
            HttpStatus.CREATED
        )
    }

    fun <T> badRequest(message: String = "Bad request"): ResponseEntity<APIResponse<T>> {
        return ResponseEntity(
            APIResponse(ResponseCode.BAD_REQUEST, message),
            HttpStatus.BAD_REQUEST
        )
    }

    fun <T> unauthorized(message: String = "Unauthorized"): ResponseEntity<APIResponse<T>> {
        return ResponseEntity(
            APIResponse(ResponseCode.UNAUTHORIZED, message),
            HttpStatus.UNAUTHORIZED
        )
    }

    fun <T> serverError(message: String = "Internal server error"): ResponseEntity<APIResponse<T>> {
        return ResponseEntity(
            APIResponse(ResponseCode.SERVER_ERROR, message),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
