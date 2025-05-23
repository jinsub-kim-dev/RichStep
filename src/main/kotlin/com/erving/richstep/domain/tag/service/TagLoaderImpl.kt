package com.erving.richstep.domain.tag.service

import com.erving.richstep.domain.tag.controller.port.TagReadService
import com.erving.richstep.domain.tag.controller.port.TagService
import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.tag.domain.TagLoader
import com.erving.richstep.domain.user.domain.User
import org.springframework.stereotype.Component

@Component
class TagLoaderImpl(
    private val tagReadService: TagReadService,
    private val tagService: TagService
) : TagLoader {

    override fun load(user: User, tagNames: List<String>): List<Tag> {
        val existingTags = tagReadService.findAllByUserAndNames(user, tagNames)
            .associateBy { it.name }

        return tagNames.map { name ->
            existingTags[name] ?: tagService.create(Tag(user = user, name = name))
        }
    }
}