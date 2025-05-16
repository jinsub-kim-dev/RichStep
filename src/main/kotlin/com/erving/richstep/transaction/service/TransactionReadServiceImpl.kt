package com.erving.richstep.transaction.service

import com.erving.richstep.transaction.controller.port.TransactionReadService
import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.exception.TransactionNotFoundException
import com.erving.richstep.transaction.service.port.TransactionRepository

class TransactionReadServiceImpl(
    private val transactionRepository: TransactionRepository
) : TransactionReadService {

    override fun getById(id: Long): Transaction {
        return this.transactionRepository.findById(id)
            ?: throw TransactionNotFoundException()
    }
}