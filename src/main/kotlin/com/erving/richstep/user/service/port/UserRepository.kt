package com.erving.richstep.user.service.port

import com.erving.richstep.user.domain.User

interface UserRepository {

    fun save(user: User): User

    fun findById(id: Long): User?
}