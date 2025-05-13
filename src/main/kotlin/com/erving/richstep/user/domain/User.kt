package com.erving.richstep.user.domain

import java.time.LocalDateTime

data class User(
    var id: Long? = null,
    val email: String,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
) {

    companion object {
        fun from(userCreate: UserCreate): User {
            return User(
                email = userCreate.email
            )
        }
    }
}