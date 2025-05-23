package com.erving.richstep.transaction.domain

import com.erving.richstep.domain.transaction.domain.DateCondition
import com.erving.richstep.domain.transaction.exception.InvalidDateException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

class DateConditionTest {

    @Test
    fun Range_정상_범위는_그대로_반환한다() {
        // given
        val from = LocalDateTime.of(2025, 5, 9, 10, 0) // 2025-05-09 10:00
        val to = LocalDateTime.of(2025, 5, 10, 10, 0) // 2025-05-10 10:00
        val dateCondition = DateCondition.Range(from, to)

        // when
        val (resolvedFrom, resolvedTo) = dateCondition.resolve()

        // then
        assertThat(resolvedFrom).isEqualTo(from)
        assertThat(resolvedTo).isEqualTo(to)
    }

    @Test
    fun Ragne_from이_to보다_늦으면_예외를_발생시킨다() {
        // given
        val from = LocalDateTime.of(2025, 5, 10, 10, 0) // 2025-05-10 10:00
        val to = LocalDateTime.of(2025, 5, 9, 10, 0) // 2025-05-09 10:00

        // when
        // then
        assertThatThrownBy {
            DateCondition.Range(from, to)
        }.isInstanceOf(InvalidDateException::class.java)
    }

    @Test
    fun SepcificDate_당일_0시_0분부터_명일_0시_0분_이전까지의_범위를_반환한다() {
        // given
        val date = LocalDate.of(2025, 5, 15)
        val dateCondition = DateCondition.SpecificDate(date)

        // when
        val (from, to) = dateCondition.resolve()

        // then
        assertThat(from).isEqualTo(date.atStartOfDay())
        assertThat(to).isBefore(date.atStartOfDay().plusDays(1))
    }

    @Test
    fun YearMonth_해당_월의_1일부터_말일까지의_범위를_반환한다() {
        // given
        val dateCondition = DateCondition.YearMonth(2025, 5)

        // when
        val (from, to) = dateCondition.resolve()

        // then
        assertThat(from).isEqualTo(LocalDate.of(2025, 5, 1).atStartOfDay())
        assertThat(to).isBefore(LocalDate.of(2025, 6, 1).atStartOfDay())
    }

    @Test
    fun YearOnly_해당_년도의_1월_1일부터_12월_31일까지의_범위를_반환한다() {
        // given
        val dateCondition = DateCondition.YearOnly(2025)

        // when
        val (from, to) = dateCondition.resolve()

        // then
        assertThat(from).isEqualTo(LocalDate.of(2025, 1, 1).atStartOfDay())
        assertThat(to).isBefore(LocalDate.of(2026, 1, 1).atStartOfDay())
    }
}