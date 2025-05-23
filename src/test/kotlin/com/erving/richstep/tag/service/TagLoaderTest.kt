package com.erving.richstep.tag.service

import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.tag.service.TagLoaderImpl
import com.erving.richstep.domain.tag.service.TagReadServiceImpl
import com.erving.richstep.domain.tag.service.TagServiceImpl
import com.erving.richstep.domain.tag.service.port.TagRepository
import com.erving.richstep.domain.user.domain.User
import com.erving.richstep.mock.FakeTagRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TagLoaderTest {

    private lateinit var tagLoader: TagLoaderImpl
    private lateinit var tagRepository: TagRepository

    @BeforeEach
    fun init() {
        this.tagRepository = FakeTagRepository()
        val tagReadService = TagReadServiceImpl(this.tagRepository)
        val tagService = TagServiceImpl(this.tagRepository)
        this.tagLoader = TagLoaderImpl(tagReadService, tagService)
    }

    @Test
    fun 존재하는_태그는_재사용하고_없는_태그는_새로_생성한다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        this.tagRepository.save(Tag(user = user, name = "식비"))

        // when
        val tags = this.tagLoader.load(user, listOf("식비", "교통"))

        // then
        assertThat(tags).hasSize(2)
        assertThat(tags.any { it.name == "식비" && it.id != null && it.id == 1L }).isTrue()
        assertThat(tags.any { it.name == "교통" && it.id != null && it.id == 2L }).isTrue()
    }
}