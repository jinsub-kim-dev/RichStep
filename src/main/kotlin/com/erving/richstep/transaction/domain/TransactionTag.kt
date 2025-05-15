package com.erving.richstep.transaction.domain

import com.erving.richstep.tag.domain.Tag

data class TransactionTag(
    var id: Long? = null,
    val tag: Tag
)