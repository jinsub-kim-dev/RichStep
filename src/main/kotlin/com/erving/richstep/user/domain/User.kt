package com.erving.richstep.user.domain

import java.time.LocalDateTime

data class User(
    var id: Long?,
    val email: String,
    val createdAt: LocalDateTime?,
    val modifiedAt: LocalDateTime?,
) {

}