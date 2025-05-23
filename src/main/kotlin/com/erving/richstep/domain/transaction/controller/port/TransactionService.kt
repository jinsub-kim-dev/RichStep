package com.erving.richstep.domain.transaction.controller.port

import com.erving.richstep.domain.transaction.domain.Transaction
import com.erving.richstep.domain.transaction.domain.TransactionCreate
import com.erving.richstep.domain.auth.domain.LoginedUser

interface TransactionService {

    fun create(loginedUser: LoginedUser, transactionCreate: TransactionCreate): Transaction
}