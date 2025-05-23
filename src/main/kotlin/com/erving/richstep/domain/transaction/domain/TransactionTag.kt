package com.erving.richstep.domain.transaction.domain

import com.erving.richstep.domain.tag.domain.Tag

data class TransactionTag(
    var id: Long? = null,
    val transactionId: Long? = null,
    val tag: Tag
)