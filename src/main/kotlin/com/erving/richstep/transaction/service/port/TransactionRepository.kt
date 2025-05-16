package com.erving.richstep.transaction.service.port

import com.erving.richstep.transaction.domain.Transaction

interface TransactionRepository {

    fun save(transaction: Transaction): Transaction

    fun findById(id: Long): Transaction?
}