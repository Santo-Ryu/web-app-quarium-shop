package com.aquariumshop.aquariumshop.model.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.*
import lombok.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
class Order: BaseEntity() {
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    var customer: Customer? = null

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    var product: Product? = null

    @Column(nullable = false, columnDefinition = "DEFAULT 0")
    var price: Int = 0

    @CreationTimestamp
    @Column(name = "order_date")
    var orderDate: LocalDateTime = LocalDateTime.now()

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    var status: OrderStatus? = null
}