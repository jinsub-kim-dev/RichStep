package com.erving.richstep.domain.transaction.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface TransactionJpaRepository : JpaRepository<TransactionEntity, Long> {
}