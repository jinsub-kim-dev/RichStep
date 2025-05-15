package com.erving.richstep.tag.controller.port

import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.user.domain.User

interface TagReadService {

    fun findAllByUserAndNames(user: User, tagNames: List<String>): List<Tag>
}