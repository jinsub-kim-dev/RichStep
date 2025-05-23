package com.erving.richstep.domain.transaction.service

import com.erving.richstep.domain.tag.domain.TagLoader
import com.erving.richstep.domain.transaction.controller.port.TransactionService
import com.erving.richstep.domain.transaction.domain.Transaction
import com.erving.richstep.domain.transaction.domain.TransactionCreate
import com.erving.richstep.domain.transaction.service.port.TransactionRepository
import com.erving.richstep.domain.user.controller.port.UserReadService
import com.erving.richstep.domain.user.domain.LoginedUser
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val userReadService: UserReadService,
    private val tagLoader: TagLoader,
) : TransactionService {

    @Transactional
    override fun create(loginedUser: LoginedUser, transactionCreate: TransactionCreate): Transaction {
        val user = this.userReadService.getById(loginedUser.id)
        val tags = user.getOrCreateTags(transactionCreate.tags, tagLoader)
        val transaction = Transaction.from(user, tags, transactionCreate)

        return this.transactionRepository.save(transaction)
    }
}