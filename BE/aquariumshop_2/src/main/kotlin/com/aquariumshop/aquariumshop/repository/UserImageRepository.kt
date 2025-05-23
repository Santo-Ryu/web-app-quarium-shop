package com.aquariumshop.aquariumshop.repository

import com.aquariumshop.aquariumshop.model.entity.UserImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserImageRepository: JpaRepository<UserImage, Long> {
}