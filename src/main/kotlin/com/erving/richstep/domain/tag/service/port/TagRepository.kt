package com.erving.richstep.domain.tag.service.port

import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.user.domain.User

interface TagRepository {

    fun save(tag: Tag): Tag

    fun findAllByUser(user: User): List<Tag>

    fun findAllByUserAndNames(user: User, tagNames: List<String>): List<Tag>
}