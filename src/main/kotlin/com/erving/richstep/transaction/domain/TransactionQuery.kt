package com.erving.richstep.transaction.domain

import com.erving.richstep.user.domain.User

interface TransactionQuery {

    fun search(user: User, condition: TransactionSearchCondition): List<Transaction>
}