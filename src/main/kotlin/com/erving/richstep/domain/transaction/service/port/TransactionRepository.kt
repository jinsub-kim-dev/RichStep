package com.erving.richstep.domain.transaction.service.port

import com.erving.richstep.domain.transaction.domain.Transaction
import com.erving.richstep.domain.transaction.domain.TransactionSearchCondition
import com.erving.richstep.domain.user.domain.User

interface TransactionRepository {

    fun save(transaction: Transaction): Transaction

    fun findById(id: Long): Transaction?

    fun findByCondition(user: User, condition: TransactionSearchCondition): List<Transaction>
}