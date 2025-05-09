package com.example.aquarium_server.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.*
import lombok.*

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "verify_email")
class VerifyEmail: BaseEntity() {
    @Column(unique = true, nullable = false)
    var email: String? = null

    @Column(unique = true, nullable = false)
    var token: String? = null
}