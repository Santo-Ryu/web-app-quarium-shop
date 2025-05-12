package com.aquariumshop.aquariumshop.model.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.*
import lombok.*

@Entity
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