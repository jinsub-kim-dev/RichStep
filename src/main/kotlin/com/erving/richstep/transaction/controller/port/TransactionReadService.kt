package com.erving.richstep.transaction.controller.port

import com.erving.richstep.transaction.domain.Transaction

interface TransactionReadService {

    fun getById(id: Long): Transaction
}