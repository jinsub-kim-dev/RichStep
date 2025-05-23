package com.erving.richstep.user.service

import com.erving.richstep.domain.user.domain.User
import com.erving.richstep.domain.user.exception.UserNotFoundException
import com.erving.richstep.domain.user.service.UserReadServiceImpl
import com.erving.richstep.mock.FakeUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class UserReadServiceTest {

    private lateinit var userReadService: UserReadServiceImpl

    @BeforeEach
    fun init() {
        val fakeUserRepository = FakeUserRepository()
        this.userReadService = UserReadServiceImpl(fakeUserRepository)

        fakeUserRepository.save(User(
                id = 1,
                email = "erving.kim@naver.com",
                createdAt = LocalDateTime.now(),
                modifiedAt = LocalDateTime.now()
            )
        )

        fakeUserRepository.save(User(
            id = 2,
            email = "erving.kim.dev@naver.com",
            createdAt = LocalDateTime.now(),
            modifiedAt = LocalDateTime.now()
        ))
    }

    @Test
    fun getById로_유저를_조회할_수_있다() {
        // given
        val targetUserId = 1L

        // when
        val findUser = this.userReadService.getById(targetUserId)

        // then
        assertThat(findUser.id).isNotNull()
        assertThat(findUser.id).isEqualTo(targetUserId)
    }

    @Test
    fun 존재하지_않는_Id로_getById를_호출하면_에러를_던진다() {
        // given
        val targetUserId = 999L

        // when
        // then
        assertThatThrownBy {
            this.userReadService.getById(targetUserId)
        }.isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun getByEmail로_유저를_조회할_수_있다() {
        // given
        val targetEmail = "erving.kim@naver.com"

        // when
        val findUser = this.userReadService.getByEmail(targetEmail)

        // then
        assertThat(findUser.id).isNotNull()
        assertThat(findUser.email).isEqualTo(targetEmail)
    }

    @Test
    fun 존재하지_않는_email로_getByEmail을_호출하면_에러를_던진다() {
        // given
        val targetUserEmail = "not.exist@email.com"

        // when
        // then
        assertThatThrownBy {
            this.userReadService.getByEmail(targetUserEmail)
        }.isInstanceOf(UserNotFoundException::class.java)
    }
}