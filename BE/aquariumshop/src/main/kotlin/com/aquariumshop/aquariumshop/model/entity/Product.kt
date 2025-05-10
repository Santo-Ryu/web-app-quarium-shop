package com.aquariumshop.aquariumshop.model.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.*
import lombok.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
class Product: BaseEntity() {
    @Column(nullable = false, unique = true)
    var name: String? = null

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    var category: Category? = null

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    var description: String? = null

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    var quantity: Int = 0

    @Column(nullable = false, columnDefinition = "DECIMAL(10, 3) DEFAULT 0.000")
    var price: BigDecimal = BigDecimal.ZERO

    @Column(name = "discount_percentage", columnDefinition = "INT DEFAULT 0")
    var discountPercentage: Int = 0

    @Column(name = "discount_start_date", columnDefinition = "TIMESTAMP")
    var discountStartDate: LocalDateTime? = null

    @Column(name = "discount_end_date", columnDefinition = "TIMESTAMP")
    var discountEndDate: LocalDateTime? = null

    /* Product reviews */
    @Column(nullable = false, columnDefinition = "DECIMAL(3,1) DEFAULT 0.0")
    var rating: Float = 0.0f

    @Column(name = "sales_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    var salesCount: Int = 0

    @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    var isActive: Int = 0
}