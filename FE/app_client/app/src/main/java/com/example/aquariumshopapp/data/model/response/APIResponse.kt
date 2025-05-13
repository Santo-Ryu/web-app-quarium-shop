package com.example.aquariumshopapp.data.model.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class APIResponse<T> (
    var code: Int = 100,
    var message: String? = null,
    var data: T? = null
)