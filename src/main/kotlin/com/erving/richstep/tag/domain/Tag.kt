package com.erving.richstep.tag.domain

import com.erving.richstep.user.domain.User

data class Tag(
    var id: Long? = null,
    val user: User,
    val name: String
) {
}