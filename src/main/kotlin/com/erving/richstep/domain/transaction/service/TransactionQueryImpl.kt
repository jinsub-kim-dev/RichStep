package com.erving.richstep.domain.transaction.service

import com.erving.richstep.domain.transaction.domain.Transaction
import com.erving.richstep.domain.transaction.domain.TransactionQuery
import com.erving.richstep.domain.transaction.domain.TransactionSearchCondition
import com.erving.richstep.domain.transaction.service.port.TransactionRepository
import com.erving.richstep.domain.user.domain.User
import org.springframework.stereotype.Component

@Component
class TransactionQueryImpl(
    private val transactionRepository: TransactionRepository,
) : TransactionQuery {

    override fun search(user: User, condition: TransactionSearchCondition): List<Transaction> {
        return this.transactionRepository.findByCondition(user, condition)
    }
}