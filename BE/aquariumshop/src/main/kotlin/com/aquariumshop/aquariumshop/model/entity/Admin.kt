package com.aquariumshop.aquariumshop.model.entity
import com.aquariumshop.aquariumshop.model.base.User
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
@Table(name = "admins")
class Admin: User() {
    /* 0 is customer | 1 is admin */
    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    var role: Int = 1
}