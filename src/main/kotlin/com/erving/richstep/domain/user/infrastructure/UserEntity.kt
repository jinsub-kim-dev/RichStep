package com.erving.richstep.domain.user.infrastructure

import com.erving.richstep.domain.common.infrastructure.BaseTimeEntity
import com.erving.richstep.domain.user.domain.User
import jakarta.persistence.*

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