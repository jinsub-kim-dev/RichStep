package com.erving.richstep.tag.service

import com.erving.richstep.tag.controller.port.TagReadService
import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.tag.service.port.TagRepository
import com.erving.richstep.user.domain.User
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