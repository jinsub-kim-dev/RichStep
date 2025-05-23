package com.erving.richstep.transaction.service

import com.erving.richstep.domain.tag.domain.Tag
import com.erving.richstep.domain.tag.service.TagLoaderImpl
import com.erving.richstep.domain.tag.service.TagReadServiceImpl
import com.erving.richstep.domain.tag.service.TagServiceImpl
import com.erving.richstep.domain.transaction.domain.TransactionCreate
import com.erving.richstep.domain.transaction.domain.TransactionType
import com.erving.richstep.domain.transaction.service.TransactionServiceImpl
import com.erving.richstep.domain.user.domain.LoginedUser
import com.erving.richstep.domain.user.domain.User
import com.erving.richstep.domain.user.service.UserReadServiceImpl
import com.erving.richstep.mock.FakeTagRepository
import com.erving.richstep.mock.FakeTransactionRepository
import com.erving.richstep.mock.FakeUserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionServiceTest {

    private lateinit var transactionService: TransactionServiceImpl

    @BeforeEach
    fun init() {
        val fakeTransactionRepository = FakeTransactionRepository()

        val fakeUserRepository = FakeUserRepository()
        val userReadService = UserReadServiceImpl(fakeUserRepository)

        val fakeTagRepository = FakeTagRepository()
        val tagReadService = TagReadServiceImpl(fakeTagRepository)
        val tagService = TagServiceImpl(fakeTagRepository)

        val tagLoader = TagLoaderImpl(tagReadService, tagService)

        this.transactionService = TransactionServiceImpl(
            fakeTransactionRepository,
            userReadService,
            tagLoader
        )

        val user = fakeUserRepository.save(User(id = 1L, email = "test@user.com"))
        fakeTagRepository.save(Tag(user = user, name = "식비"))
    }

    @Test
    fun transactionCreate를_이용하여_새로운_Transaction을_생성할_수_있다() {
        // given
        val loginedUser = LoginedUser(id = 1L)
        val date = LocalDateTime.now()
        val transactionCreate = TransactionCreate(
            date = date,
            amount = BigDecimal("10000"),
            type = TransactionType.EXPENSE,
            description = "점심식사",
            tags = listOf("식비", "점심")
        )

        // when
        val transaction = this.transactionService.create(loginedUser, transactionCreate)

        // then
        assertThat(transaction.id).isNotNull()
        assertThat(transaction.user.id).isEqualTo(loginedUser.id)
        assertThat(transaction.date).isEqualTo(transactionCreate.date)
        assertThat(transaction.amount).isEqualTo(transactionCreate.amount)
        assertThat(transaction.type).isEqualTo(transactionCreate.type)
        assertThat(transaction.description).isEqualTo(transactionCreate.description)

        assertThat(transaction.transactionTags).hasSize(2)

        val tagNames = transaction.transactionTags.map { it.tag.name }
        assertThat(tagNames).containsExactlyInAnyOrder("식비", "점심")
    }
}