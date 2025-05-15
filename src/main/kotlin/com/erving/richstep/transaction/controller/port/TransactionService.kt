package com.erving.richstep.transaction.controller.port

import com.erving.richstep.transaction.domain.Transaction
import com.erving.richstep.transaction.domain.TransactionCreate
import com.erving.richstep.user.domain.LoginedUser

interface TransactionService {

    fun create(loginedUser: LoginedUser, transactionCreate: TransactionCreate): Transaction
}