package com.example.aquarium_server.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "product_images")
class ProductImage: BaseEntity() {
    @Column(nullable = false, unique = true)
    var name: String? = null

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product? = null
}