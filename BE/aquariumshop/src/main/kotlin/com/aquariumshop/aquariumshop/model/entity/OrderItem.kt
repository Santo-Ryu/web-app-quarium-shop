package com.aquariumshop.aquariumshop.model.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items")
class OrderItem: BaseEntity() {
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    var order: Order? = null

    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    var product: Product? = null

    @Column(nullable = false, columnDefinition = "DEFAULT 0")
    var quantity: Int? = null

    @Column(nullable = false, columnDefinition = "DEFAULT 0")
    var price: Int? = null

    @Column(name = "discount_percent", nullable = false, columnDefinition = "DEFAULT 0")
    var discountPercent: Int? = null

    @Column(name = "subtotal", nullable = false, columnDefinition = "DEFAULT 0")
    var subtotal: Int? = null
}