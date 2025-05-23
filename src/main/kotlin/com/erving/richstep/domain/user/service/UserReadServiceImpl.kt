package com.erving.richstep.domain.user.service

import com.erving.richstep.domain.user.controller.port.UserReadService
import com.erving.richstep.domain.user.domain.User
import com.erving.richstep.domain.user.exception.UserNotFoundException
import com.erving.richstep.domain.user.service.port.UserRepository
import org.springframework.stereotype.Service

@Service
class UserReadServiceImpl(
    private val userRepository: UserRepository
) : UserReadService {

    override fun getById(id: Long): User {
        return this.userRepository.findById(id)
            ?: throw UserNotFoundException()
    }

    override fun getByEmail(email: String): User {
        return this.userRepository.findByEmail(email)
            ?: throw UserNotFoundException()
    }
}