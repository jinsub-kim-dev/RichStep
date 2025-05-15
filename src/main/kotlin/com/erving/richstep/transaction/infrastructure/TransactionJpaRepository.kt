package com.erving.richstep.transaction.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJpaRepository : JpaRepository<TransactionEntity, Long> {
}