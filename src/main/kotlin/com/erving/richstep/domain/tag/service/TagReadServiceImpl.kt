package com.erving.richstep.domain.tag.service

import com.erving.richstep.domain.tag.controller.port.TagReadService
import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.tag.service.port.TagRepository
import com.erving.richstep.domain.user.domain.User
import org.springframework.stereotype.Service

@Service
class TagReadServiceImpl(
    private val tagRepository: TagRepository
) : TagReadService {

    override fun findAllByUser(user: User): List<Tag> {
        return tagRepository.findAllByUser(user)
    }

    override fun findAllByUserAndNames(user: User, tagNames: List<String>): List<Tag> {
        return tagRepository.findAllByUserAndNames(user, tagNames)
    }
}