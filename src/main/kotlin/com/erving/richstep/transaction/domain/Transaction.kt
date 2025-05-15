package com.erving.richstep.transaction.domain

import com.erving.richstep.tag.domain.Tag
import com.erving.richstep.user.domain.User
import java.math.BigDecimal
import java.time.LocalDateTime

data class Transaction(
    var id: Long? = null,
    val user: User,
    val date: LocalDateTime,
    val amount: BigDecimal,
    val type: TransactionType,
    val description: String?,
    val transactionTags: MutableList<TransactionTag> = mutableListOf()
) {

    companion object {
        fun from(user: User, tags: List<Tag>, transactionCreate: TransactionCreate): Transaction {
            return Transaction(
                user = user,
                date = transactionCreate.date,
                amount = transactionCreate.amount,
                type = transactionCreate.type,
                description = transactionCreate.description,
                transactionTags = tags.map { TransactionTag(tag = it) }.toMutableList()
            )
        }
    }
}