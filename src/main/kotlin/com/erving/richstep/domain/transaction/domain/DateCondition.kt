package com.erving.richstep.domain.transaction.domain

import com.erving.richstep.domain.transaction.exception.InvalidDateException
import java.time.LocalDate
import java.time.LocalDateTime

sealed class DateCondition {
    abstract fun resolve(): Pair<LocalDateTime, LocalDateTime>

    data class Range(val from: LocalDateTime, val to: LocalDateTime) : DateCondition() {
        init {
            if (from.isAfter(to)) {
                throw InvalidDateException()
            }
        }

        override fun resolve(): Pair<LocalDateTime, LocalDateTime> = from to to
    }

    data class SpecificDate(val date: LocalDate) : DateCondition() {
        override fun resolve(): Pair<LocalDateTime, LocalDateTime> {
            val start = date.atStartOfDay()
            val end = start.plusDays(1).minusNanos(1)
            return start to end
        }
    }

    data class YearMonth(val year: Int, val month: Int) : DateCondition() {
        init {
            if (month < 1 || month > 12) {
                throw InvalidDateException()
            }
        }

        override fun resolve(): Pair<LocalDateTime, LocalDateTime> {
            val start = LocalDate.of(year, month, 1).atStartOfDay()
            val end = start.plusMonths(1).minusNanos(1)
            return start to end
        }
    }

    data class YearOnly(val year: Int) : DateCondition() {
        override fun resolve(): Pair<LocalDateTime, LocalDateTime> {
            val start = LocalDate.of(year, 1, 1).atStartOfDay()
            val end = start.plusYears(1).minusNanos(1)
            return start to end
        }
    }
}