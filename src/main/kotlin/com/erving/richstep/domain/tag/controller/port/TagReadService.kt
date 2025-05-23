package com.erving.richstep.domain.tag.controller.port

import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.user.domain.User

interface TagReadService {

    fun findAllByUser(user: User): List<Tag>

    fun findAllByUserAndNames(user: User, tagNames: List<String>): List<Tag>
}