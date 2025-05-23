package com.erving.richstep.domain.tag.domain

import com.erving.richstep.domain.user.domain.User

data class Tag(
    var id: Long? = null,
    val user: User,
    val name: String
) {
}