package com.erving.richstep.transaction.service.port

import com.erving.richstep.transaction.domain.TransactionSearchCondition
import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.user.domain.User

interface TransactionRepository {

    fun save(transaction: Transaction): Transaction

    fun findById(id: Long): Transaction?

    fun findByCondition(user: User, condition: TransactionSearchCondition): List<Transaction>
}