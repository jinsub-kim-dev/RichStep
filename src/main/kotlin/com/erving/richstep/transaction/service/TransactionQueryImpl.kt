package com.erving.richstep.transaction.service

import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.domain.TransactionQuery
import com.erving.richstep.transaction.domain.TransactionSearchCondition
import com.erving.richstep.transaction.service.port.TransactionRepository
import com.erving.richstep.user.domain.User
import org.springframework.stereotype.Component

@Component
class TransactionQueryImpl(
    private val transactionRepository: TransactionRepository,
) : TransactionQuery {

    override fun search(user: User, condition: TransactionSearchCondition): List<Transaction> {
        return this.transactionRepository.findByCondition(user, condition)
    }
}