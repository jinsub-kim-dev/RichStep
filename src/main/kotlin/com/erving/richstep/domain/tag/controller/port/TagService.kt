package com.erving.richstep.domain.tag.controller.port

import com.erving.richstep.domain.tag.domain.Tag

interface TagService {

    fun create(tag: Tag): Tag
}