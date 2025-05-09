package com.aquariumshop.aquariumshop.model.base

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import com.example.aquarium_server.entity.UserImage
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.OneToOne
import java.time.LocalDateTime
import java.util.Date

@MappedSuperclass
abstract class User: BaseEntity() {
    @Column(nullable = false, unique = true, length = 50)
    var email: String? = null

    @Column(nullable = false)
    var password: String? = null

    @Column(nullable = false, length = 50)
    var name: String? = null

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 2")
    var gender: Int = 2;

    @Column(name = "birth_date", columnDefinition = "DATE")
    var birthDate: Date? = null

    @Column(length = 11)
    var phone: String? = null

    @Column(name = "verify_email", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    var verifyEmail: Int = 0

    @Column(name = "verify_at", columnDefinition = "TIMESTAMP")
    var verifyAt: LocalDateTime? = null

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false, unique = true)
    var image: UserImage? = null
}