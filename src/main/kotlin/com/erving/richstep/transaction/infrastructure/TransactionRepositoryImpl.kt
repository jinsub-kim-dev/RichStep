package com.erving.richstep.transaction.infrastructure

import com.erving.richstep.tag.infrastructure.QTagEntity
import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.domain.TransactionSearchCondition
import com.erving.richstep.transaction.service.port.TransactionRepository
import com.erving.richstep.user.domain.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class TransactionRepositoryImpl(
    private val transactionJpaRepository: TransactionJpaRepository,
    private val queryFactory: JPAQueryFactory
) : TransactionRepository {

    override fun save(transaction: Transaction): Transaction {
        var transactionEntity = TransactionEntity.fromModel(transaction)
        transactionEntity = this.transactionJpaRepository.save(transactionEntity)
        return transactionEntity.toModel()
    }

    override fun findById(id: Long): Transaction? {
        return this.transactionJpaRepository.findByIdOrNull(id)?.toModel()
    }

    override fun findByCondition(user: User, condition: TransactionSearchCondition): List<Transaction> {
        val qTransactionEntity = QTransactionEntity.transactionEntity
        val qTransactionTagEntity = QTransactionTagEntity.transactionTagEntity
        val qTagEntity = QTagEntity.tagEntity

        val dateRange = condition.dateCondition?.resolve()
        val (start, end) = dateRange ?: Pair(null, null)

        val results = queryFactory
            .selectFrom(qTransactionEntity)
            .leftJoin(qTransactionEntity.transactionTags, qTransactionTagEntity).fetchJoin()
            .leftJoin(qTransactionTagEntity.tag, qTagEntity).fetchJoin()
            .where(
                qTransactionEntity.user.id.eq(user.id),
                start?.let { qTransactionEntity.date.goe(it) },
                end?.let { qTransactionEntity.date.loe(it) },
                condition.tag?.let { qTagEntity.name.eq(it) },
                condition.type?.let { qTransactionEntity.type.eq(it) }
            )
            .distinct()
            .orderBy(qTransactionEntity.date.desc())
            .fetch()

        return results.map { it.toModel() }
    }
}