package com.aquariumshop.aquariumshop.model.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.*
import lombok.*


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
class Comment: BaseEntity() {
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    var customer: Customer? = null

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    var product: Product? = null

    @Column(nullable = false, columnDefinition = "TEXT")
    var content: String? = null

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 5")
    var rating: Int? = null
}