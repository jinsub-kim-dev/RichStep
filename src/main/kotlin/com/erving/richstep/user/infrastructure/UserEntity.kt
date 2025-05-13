package com.erving.richstep.user.infrastructure

import com.erving.richstep.common.infrastructure.BaseTimeEntity
import com.erving.richstep.user.domain.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserEntity(
    email: String
) : BaseTimeEntity() {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @Column(name = "email")
    var email: String = email
        protected set

    fun toModel(): User {
        return User(
            id = this.id,
            email = this.email,
            createdAt = this.createdAt,
            modifiedAt = this.modifiedAt
        )
    }

    companion object {
        fun fromModel(user: User): UserEntity {
            val userEntity = UserEntity(user.email)
            userEntity.id = user.id
            userEntity.createdAt = user.createdAt
            userEntity.modifiedAt = user.modifiedAt
            return userEntity
        }
    }
}