package com.erving.richstep.domain.transaction.controller.port

import com.erving.richstep.domain.transaction.domain.Transaction

interface TransactionReadService {

    fun getById(id: Long): Transaction
}