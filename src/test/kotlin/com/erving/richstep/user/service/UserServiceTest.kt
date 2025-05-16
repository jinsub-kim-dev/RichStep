package com.erving.richstep.user.service

import com.erving.richstep.user.domain.UserCreate
import com.erving.richstep.mock.FakeUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class UserServiceTest {

    private lateinit var userService: UserServiceImpl

    @BeforeEach
    fun init() {
        val fakeUserRepository = FakeUserRepository()
        this.userService = UserServiceImpl(fakeUserRepository)
    }

    @Test
    fun userCreate를_이용하여_새로운_유저를_생성할_수_있다() {
        // given
        val userCreate = UserCreate(
            email = "less8430@naver.com"
        )

        // when
        val createdUser = this.userService.create(userCreate)

        // then
        assertThat(createdUser.id).isNotNull()
        assertThat(createdUser.email).isEqualTo("less8430@naver.com")
    }
}