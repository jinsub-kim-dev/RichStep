package com.erving.richstep.transaction.domain

data class TransactionSearchCondition(
    val dateCondition: DateCondition? = null,
    val tag: String? = null,
    val type: TransactionType? = null,
)