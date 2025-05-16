package com.erving.richstep.transaction.infrastructure

import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.service.port.TransactionRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class TransactionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository
) : TransactionRepository {

    override fun save(transaction: Transaction): Transaction {
        var transactionEntity = TransactionEntity.fromModel(transaction)
        transactionEntity = this.transactionJpaRepository.save(transactionEntity)
        return transactionEntity.toModel()
    }

    override fun findById(id: Long): Transaction? {
        return this.transactionJpaRepository.findByIdOrNull(id)?.toModel()
    }
}