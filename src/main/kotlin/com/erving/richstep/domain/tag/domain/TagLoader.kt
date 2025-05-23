package com.erving.richstep.domain.tag.domain

import com.erving.richstep.domain.user.domain.User

interface TagLoader {

    fun load(user: User, tagNames: List<String>): List<Tag>
}