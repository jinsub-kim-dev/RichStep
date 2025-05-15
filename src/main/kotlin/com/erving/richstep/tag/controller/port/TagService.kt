package com.erving.richstep.tag.controller.port

import com.erving.richstep.tag.domain.Tag

interface TagService {

    fun create(tag: Tag): Tag
}