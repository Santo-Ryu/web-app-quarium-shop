package com.aquariumshop.aquariumshop.service.impl

import com.aquariumshop.aquariumshop.enums.Role
import com.aquariumshop.aquariumshop.model.entity.Admin
import com.aquariumshop.aquariumshop.model.entity.Customer
import com.aquariumshop.aquariumshop.repository.AdminRepository
import com.aquariumshop.aquariumshop.repository.CustomerRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val adminRepository: AdminRepository,
    private val customerRepository: CustomerRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val admin = adminRepository.findByEmail(email)
        if (admin != null) {
            return createUserDetails(admin.email!!, admin.password!!, Role.ADMIN)
        }

        val customer = customerRepository.findByEmail(email)
        if (customer != null) {
            return createUserDetails(customer.email!!, customer.password!!, Role.CUSTOMER)
        }

        throw UsernameNotFoundException("User not found with email: $email")
    }

    private fun createUserDetails(email: String, password: String, role: Role): UserDetails {
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
        return User(email, password, authorities)
    }
} 