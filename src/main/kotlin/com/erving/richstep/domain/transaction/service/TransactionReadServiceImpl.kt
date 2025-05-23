package com.erving.richstep.domain.transaction.service

import com.erving.richstep.domain.transaction.controller.port.TransactionReadService
import com.erving.richstep.domain.transaction.domain.Transaction
import com.erving.richstep.domain.transaction.exception.TransactionNotFoundException
import com.erving.richstep.domain.transaction.service.port.TransactionRepository

class TransactionReadServiceImpl(
    private val transactionRepository: TransactionRepository
) : TransactionReadService {

    override fun getById(id: Long): Transaction {
        return this.transactionRepository.findById(id)
            ?: throw TransactionNotFoundException()
    }
}