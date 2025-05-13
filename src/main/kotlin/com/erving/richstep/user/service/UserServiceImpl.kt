package com.erving.richstep.user.service

import com.erving.richstep.user.controller.port.UserService
import com.erving.richstep.user.domain.User
import com.erving.richstep.user.domain.UserCreate
import com.erving.richstep.user.service.port.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    @Transactional
    override fun create(userCreate: UserCreate): User {
        var user = User.from(userCreate)
        user = this.userRepository.save(user)
        return user
    }
}