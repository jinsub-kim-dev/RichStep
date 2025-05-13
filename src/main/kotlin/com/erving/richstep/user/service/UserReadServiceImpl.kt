package com.erving.richstep.user.service

import com.erving.richstep.user.controller.port.UserReadService
import com.erving.richstep.user.domain.User
import com.erving.richstep.user.exception.UserNotFoundException
import com.erving.richstep.user.service.port.UserRepository
import org.springframework.stereotype.Service

@Service
class UserReadServiceImpl(
    private val userRepository: UserRepository
) : UserReadService {

    override fun getById(id: Long): User {
        return this.userRepository.findById(id)
            ?: throw UserNotFoundException()
    }
}