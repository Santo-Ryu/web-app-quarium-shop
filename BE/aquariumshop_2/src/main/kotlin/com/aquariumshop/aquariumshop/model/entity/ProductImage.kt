package com.aquariumshop.aquariumshop.model.entity

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
@Setter
@Getter
@Table(name = "product_images")
class ProductImage: BaseEntity() {
    @Column(nullable = false, unique = true)
    var name: String? = null

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    var product: Product? = null

    override fun toString(): String {
        return "ProductImage(id=$id, name='$name', productId=${product?.id})"
    }
}