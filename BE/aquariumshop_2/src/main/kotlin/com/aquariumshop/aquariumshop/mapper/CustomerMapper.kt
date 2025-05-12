package com.aquariumshop.aquariumshop.mapper

import com.aquariumshop.aquariumshop.dto.response.CustomerResponse
import com.aquariumshop.aquariumshop.model.entity.Customer
import org.mapstruct.Mapper

@Mapper(componentModel = "spring", uses = [UserImageMapper::class])
interface CustomerMapper {
    fun toResponse(customer: Customer): CustomerResponse
    fun toEntity(response: CustomerResponse): Customer
    fun toResponseList(customers: List<Customer>): List<CustomerResponse>
    fun toEntityList(responses: List<CustomerResponse>): List<Customer>
}