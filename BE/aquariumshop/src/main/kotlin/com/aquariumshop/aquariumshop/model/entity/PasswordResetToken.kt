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
@Table(name = "password_reset_token")
class PasswordResetToken: BaseEntity() {
    @Column(unique = true, nullable = false)
    var email: String? = null

    @Column(unique = true, nullable = false)
    var token: String? = null
}