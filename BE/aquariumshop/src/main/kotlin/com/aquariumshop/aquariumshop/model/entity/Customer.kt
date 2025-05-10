package com.aquariumshop.aquariumshop.model.entity

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
@Builder
@Table(name = "customers")
class Customer: User() {
    var address: String? = null

    @Column(name = "last_login", columnDefinition = "TIMESTAMP")
    var lastLogin: LocalDateTime? = null

    /* 0 is customer | 1 is admin */
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    var role: Int = 0
}