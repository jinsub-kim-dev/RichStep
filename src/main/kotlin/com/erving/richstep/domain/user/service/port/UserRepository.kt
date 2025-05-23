package com.erving.richstep.domain.user.service.port

import com.erving.richstep.domain.user.domain.User

interface UserRepository {

    fun save(user: User): User

    fun findById(id: Long): User?
}