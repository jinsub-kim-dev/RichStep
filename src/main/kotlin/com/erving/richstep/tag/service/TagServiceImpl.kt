package com.erving.richstep.tag.service

import com.erving.richstep.tag.controller.port.TagService
import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.tag.service.port.TagRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TagServiceImpl(
    private val tagRepository: TagRepository
) : TagService {

    @Transactional
    override fun create(tag: Tag): Tag {
        return this.tagRepository.save(tag)
    }
}