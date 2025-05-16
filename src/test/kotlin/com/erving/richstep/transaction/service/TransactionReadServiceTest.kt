package com.erving.richstep.transaction.service

import com.erving.richstep.mock.FakeTagRepository
import com.erving.richstep.mock.FakeTransactionRepository
import com.erving.richstep.mock.FakeUserRepository
import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.domain.TransactionTag
import com.erving.richstep.transaction.domain.TransactionType
import com.erving.richstep.transaction.exception.TransactionNotFoundException
import com.erving.richstep.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class TransactionReadServiceTest {

    private lateinit var transactionReadService: TransactionReadServiceImpl

    @BeforeEach
    fun init() {
        val fakeTransactionRepository = FakeTransactionRepository()
        val fakeUserRepository = FakeUserRepository()
        val fakeTagRepository = FakeTagRepository()

        this.transactionReadService = TransactionReadServiceImpl(fakeTransactionRepository)

        val user = fakeUserRepository.save(User(id = 1L, email = "test@user.com"))
        val tag = fakeTagRepository.save(Tag(user = user, name = "식비"))

        fakeTransactionRepository.save(
            Transaction(
                id = 1L,
                user = user,
                date = LocalDateTime.now(),
                amount = BigDecimal("10000"),
                type = TransactionType.EXPENSE,
                description = "점심식사",
                transactionTags = mutableListOf(TransactionTag(id = 1L, tag = tag))
            )
        )
    }

    @Test
    fun getById로_Transaction을_조회할_수_있다() {
        // given
        val targetTransactionId = 1L

        // when
        val findTransaction = this.transactionReadService.getById(targetTransactionId)

        // then
        assertThat(findTransaction.id).isEqualTo(targetTransactionId)
        assertThat(findTransaction.user.id).isEqualTo(1L)
        assertThat(findTransaction.user.email).isEqualTo("test@user.com")
        assertThat(findTransaction.amount).isEqualTo(BigDecimal("10000"))
        assertThat(findTransaction.type).isEqualTo(TransactionType.EXPENSE)
        assertThat(findTransaction.description).isEqualTo("점심식사")

        val tagNames = findTransaction.transactionTags.map { it.tag.name }
        assertThat(tagNames).containsExactlyInAnyOrder("식비")
    }

    @Test
    fun 존재하지_않는_Id로_getById를_호출하면_에러를_던진다() {
        // given
        val targetTransactionId = 999L

        // when
        // then
        assertThatThrownBy {
            this.transactionReadService.getById(targetTransactionId)
        }.isInstanceOf(TransactionNotFoundException::class.java)
    }
}