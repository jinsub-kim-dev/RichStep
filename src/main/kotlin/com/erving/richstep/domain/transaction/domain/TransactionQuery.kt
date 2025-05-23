package com.erving.richstep.domain.transaction.domain

import com.erving.richstep.domain.user.domain.User

interface TransactionQuery {

    fun search(user: User, condition: TransactionSearchCondition): List<Transaction>
}