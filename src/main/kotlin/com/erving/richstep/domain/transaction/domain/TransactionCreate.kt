package com.erving.richstep.domain.transaction.domain

import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionCreate(
    val date: LocalDateTime,
    val amount: BigDecimal,
    val type: TransactionType,
    val description: String?,
    val tags: List<String> = emptyList()
) {
}