package com.aquariumshop.aquariumshop.model.entity

import com.aquariumshop.aquariumshop.model.base.BaseEntity
import jakarta.persistence.*
import lombok.*

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_status")
class OrderStatus: BaseEntity() {
    @Column(name = "status_name", unique = true, length = 50)
    var statusName: String? = null
}