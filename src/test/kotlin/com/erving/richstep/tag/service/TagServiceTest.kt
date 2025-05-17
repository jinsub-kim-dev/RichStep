package com.erving.richstep.tag.service

import com.erving.richstep.mock.FakeTagRepository
import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TagServiceTest {

    private lateinit var tagService: TagServiceImpl

    @BeforeEach
    fun init() {
        val fakeTagRepository = FakeTagRepository()
        this.tagService = TagServiceImpl(fakeTagRepository)
    }

    @Test
    fun tag를_저장할_수_있다() {
        // given
        val user = User(id = 1L, email = "test@user.com")

        // when
        val tag = this.tagService.create(Tag(user = user, name = "식비"))

        // then
        assertThat(tag.id).isNotNull()
        assertThat(tag.user.id).isEqualTo(user.id)
        assertThat(tag.user.email).isEqualTo(user.email)
        assertThat(tag.name).isEqualTo("식비")
    }
}