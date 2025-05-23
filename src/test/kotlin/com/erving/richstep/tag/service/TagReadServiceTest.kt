package com.erving.richstep.tag.service

import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.tag.service.TagReadServiceImpl
import com.erving.richstep.domain.user.domain.User
import com.erving.richstep.mock.FakeTagRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TagReadServiceTest {

    private lateinit var tagReadService: TagReadServiceImpl

    @BeforeEach
    fun init() {
        val fakeTagRepository = FakeTagRepository()
        this.tagReadService = TagReadServiceImpl(fakeTagRepository)

        val user = User(id = 1L, email = "test@user.com")

        fakeTagRepository.save(Tag(user = user, name = "식비"))
        fakeTagRepository.save(Tag(user = user, name = "교통"))
        fakeTagRepository.save(Tag(user = user, name = "관리비"))
        fakeTagRepository.save(Tag(user = user, name = "대출이자"))
    }

    @Test
    fun user의_모든_Tag를_조회할_수_있다() {
        // given
        val user = User(id = 1L, email = "test@user.com")

        // when
        val foundTags = this.tagReadService.findAllByUser(user)

        // then
        assertThat(foundTags).allSatisfy {
            assertThat(it.user.id).isEqualTo(user.id)
        }
    }

    @Test
    fun 다른_user의_tag는_조회할_수_없다() {
        // given
        val user = User(id = 999L, email = "not.exist@user.com")

        // when
        val foundTags = this.tagReadService.findAllByUser(user)

        // then
        assertThat(foundTags).isEmpty()
    }

    @Test
    fun user와_tagName이_일치하는_모든_Tag를_조회할_수_있다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val tagNames = listOf("교통", "관리비", "식비")

        // when
        val foundTags = this.tagReadService.findAllByUserAndNames(user, tagNames)

        // then
        assertThat(tagNames).hasSize(3)

        val foundTagNames = foundTags.map { it.name }
        assertThat(foundTagNames).containsExactlyInAnyOrder("교통", "관리비", "식비")
    }
}