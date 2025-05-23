package com.erving.richstep.domain.tag.infrastructure

import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.tag.service.port.TagRepository
import com.erving.richstep.domain.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class TagRepositoryImpl(
    private val tagJpaRepository: TagJpaRepository
) : TagRepository {

    override fun save(tag: Tag): Tag {
        var tagEntity = TagEntity.fromModel(tag)
        tagEntity = tagJpaRepository.save(tagEntity)
        return tagEntity.toModel()
    }

    override fun findAllByUser(user: User): List<Tag> {
        return this.tagJpaRepository.findAllByUserId(user.id!!)
            .map { it.toModel() }
    }

    override fun findAllByUserAndNames(user: User, tagNames: List<String>): List<Tag> {
        return this.tagJpaRepository.findAllByUserIdAndNameIn(user.id!!, tagNames)
            .map { it.toModel() }
    }
}