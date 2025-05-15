package com.erving.richstep.tag.domain

import com.erving.richstep.user.domain.User

interface TagLoader {

    fun load(user: User, tagNames: List<String>): List<Tag>
}