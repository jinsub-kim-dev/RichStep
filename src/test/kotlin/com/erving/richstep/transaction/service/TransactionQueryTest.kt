package com.erving.richstep.transaction.service

import com.erving.richstep.mock.FakeTagRepository
import com.erving.richstep.mock.FakeTransactionRepository
import com.erving.richstep.mock.FakeUserRepository
import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.transaction.domain.DateCondition
import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.domain.TransactionSearchCondition
import com.erving.richstep.transaction.domain.TransactionTag
import com.erving.richstep.transaction.domain.TransactionType
import com.erving.richstep.user.domain.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class TransactionQueryTest {

    private lateinit var transactionQuery: TransactionQueryImpl

    @BeforeEach
    fun init() {
        val fakeTransactionRepository = FakeTransactionRepository()
        this.transactionQuery = TransactionQueryImpl(fakeTransactionRepository)

        val fakeUserRepository = FakeUserRepository()
        val fakeTagRepository = FakeTagRepository()

        val user = fakeUserRepository.save(User(id = 1L, email = "test@user.com"))

        val tag1 = fakeTagRepository.save(Tag(user = user, name = "식비"))
        val tag2 = fakeTagRepository.save(Tag(user = user, name = "교통"))
        val tag3 = fakeTagRepository.save(Tag(user = user, name = "통신비"))
        val tag4 = fakeTagRepository.save(Tag(user = user, name = "월급"))

        fakeTransactionRepository.save(Transaction(
            user = user, date = LocalDateTime.of(2025, 5, 1, 11, 28),
            amount = BigDecimal("20000"), type = TransactionType.EXPENSE, description = "점심 식사",
            transactionTags = mutableListOf(TransactionTag(id = 1L, tag = tag1))
        ))
        fakeTransactionRepository.save(Transaction(
            user = user, date = LocalDateTime.of(2025, 5, 5, 19, 10),
            amount = BigDecimal("25000"), type = TransactionType.EXPENSE, description = "치킨 주문",
            transactionTags = mutableListOf(TransactionTag(id = 2L, tag = tag1))
        ))
        fakeTransactionRepository.save(Transaction(
            user = user, date = LocalDateTime.of(2025, 5, 5, 12, 0),
            amount = BigDecimal("70000"), type = TransactionType.EXPENSE, description = "차량 휘발유 주유",
            transactionTags = mutableListOf(TransactionTag(id = 3L, tag = tag2))
        ))
        fakeTransactionRepository.save(Transaction(
            user = user, date = LocalDateTime.of(2025, 5, 25, 4, 0),
            amount = BigDecimal("5000000"), type = TransactionType.INCOME, description = "5월 월급",
            transactionTags = mutableListOf(TransactionTag(id = 4L, tag = tag4))
        ))
        fakeTransactionRepository.save(Transaction(
            user = user, date = LocalDateTime.of(2025, 6, 2, 9, 30),
            amount = BigDecimal("45000"), type = TransactionType.EXPENSE, description = "휴대폰 요금",
            transactionTags = mutableListOf(TransactionTag(id = 5L, tag = tag3))
        ))
        fakeTransactionRepository.save(Transaction(
            user = user, date = LocalDateTime.of(2025, 6, 6, 18, 10),
            amount = BigDecimal("12000"), type = TransactionType.EXPENSE, description = "저녁 식사",
            transactionTags = mutableListOf(TransactionTag(tag = tag1))
        ))
    }

    @Test
    fun 아무_조건이_주어지지_않으면_유저의_전체_데이터를_반환한다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val condition = TransactionSearchCondition()

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)

        // then
        assertThat(searchedTransactions).allSatisfy {
            assertThat(it.user.id).isEqualTo(user.id)
        }
    }

    @Test
    fun 타_유저의_데이터는_검색되지_않는다() {
        // given
        val user = User(id = 999L, email = "not.exist@user.com")
        val condition = TransactionSearchCondition()

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)

        // then
        assertThat(searchedTransactions).isEmpty()
    }

    @Test
    fun 태그_조건만_있는_경우_해당_태그가_붙은_거래만_조회된다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val searchTag = "식비"
        val condition = TransactionSearchCondition(tag = searchTag)

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)

        // then
        assertThat(searchedTransactions).allSatisfy {
            val tagNames = it.transactionTags.map { transactionTag -> transactionTag.tag.name }
            assertThat(tagNames).contains(searchTag)
        }
    }

    @Test
    fun Range_조건이_있는_경우_해당_날짜_범위만_조회된다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val dateCondition = DateCondition.Range(
            from = LocalDateTime.of(2025,5, 1, 0, 0),
            to = LocalDateTime.of(2025, 5, 31, 23, 59)
        )
        val condition = TransactionSearchCondition(dateCondition = dateCondition)

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)

        // then
        assertThat(searchedTransactions).allSatisfy {
            assertThat(it.date).isBetween(dateCondition.resolve().first, dateCondition.resolve().second)
        }
    }

    @Test
    fun 연도만_지정된_경우_해당_연도_전체_거래가_조회된다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val searchYear = 2025
        val condition = TransactionSearchCondition(
            dateCondition = DateCondition.YearOnly(searchYear)
        )

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)

        // then
        assertThat(searchedTransactions).allSatisfy {
            assertThat(it.date.year).isEqualTo(searchYear)
        }
    }

    @Test
    fun 특정_날짜_조건이_주어진_경우_해당_날짜의_거래만_조회된다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val searchDate = LocalDate.of(2025, 5, 5)
        val condition = TransactionSearchCondition(
            dateCondition = DateCondition.SpecificDate(searchDate)
        )

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)

        // then
        assertThat(searchedTransactions).allSatisfy {
            assertThat(it.date.toLocalDate()).isEqualTo(searchDate)
        }
    }

    @Test
    fun 년도와_월_조건이_주어진_경우_해당_년월의_결과만_조회된다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val searchYear = 2025
        val searchMonth = 5
        val condition = TransactionSearchCondition(
            dateCondition = DateCondition.YearMonth(searchYear, searchMonth)
        )

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)

        // then
        assertThat(searchedTransactions).allSatisfy {
            assertThat(it.date.year).isEqualTo(searchYear)
            assertThat(it.date.monthValue).isEqualTo(searchMonth)
        }
    }

    @Test
    fun 기간과_태그가_함께_조건으로_주어지면_둘_다_만족하는_거래만_조회된다() {
        // given
        val user = User(id = 1L, email = "test@user.com")
        val searchYear = 2025
        val searchMonth = 5
        val searchTag = "식비"
        val dateCondition = DateCondition.YearMonth(searchYear, searchMonth)
        val condition = TransactionSearchCondition(
            dateCondition = dateCondition,
            tag = searchTag
        )

        // when
        val searchedTransactions = this.transactionQuery.search(user, condition)
        val (from, to) = dateCondition.resolve()

        // then
        assertThat(searchedTransactions).allSatisfy { tx ->
            assertThat(tx.date).isBetween(from, to)
            assertThat(tx.transactionTags.map { it.tag.name }).contains(searchTag)
        }
    }
}