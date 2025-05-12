package com.aquariumshop.aquariumshop.model.entity
import com.aquariumshop.aquariumshop.enums.Role
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
@Setter
@Getter
@Table(name = "admins")
class Admin: User() {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.ADMIN
}