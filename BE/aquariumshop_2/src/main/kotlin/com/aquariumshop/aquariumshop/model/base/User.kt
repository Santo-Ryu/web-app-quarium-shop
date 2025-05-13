package com.aquariumshop.aquariumshop.model.base

import com.aquariumshop.aquariumshop.enums.Gender
import com.aquariumshop.aquariumshop.enums.VerifyEmailType
import com.aquariumshop.aquariumshop.model.entity.UserImage
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var gender: Gender = Gender.OTHER

    @Column(name = "birth_date", columnDefinition = "DATE")
    var birthDate: Date? = null

    @Column(length = 11)
    var phone: String? = null

    @Column(name = "verify_email", nullable = false)
    @Enumerated(EnumType.STRING)
    var verifyEmail: VerifyEmailType = VerifyEmailType.UNVERIFIED

    @Column(name = "verify_at", columnDefinition = "TIMESTAMP")
    var verifyAt: LocalDateTime? = null

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", unique = true)
    var image: UserImage? = null
}