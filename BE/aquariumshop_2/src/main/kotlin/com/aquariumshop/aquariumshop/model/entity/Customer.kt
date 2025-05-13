package com.aquariumshop.aquariumshop.model.entity

import com.aquariumshop.aquariumshop.enums.Role
import com.aquariumshop.aquariumshop.model.base.User
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "customers")
class Customer: User() {
    var address: String? = null

    @Column(name = "last_login", columnDefinition = "TIMESTAMP")
    var lastLogin: LocalDateTime? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.CUSTOMER
}