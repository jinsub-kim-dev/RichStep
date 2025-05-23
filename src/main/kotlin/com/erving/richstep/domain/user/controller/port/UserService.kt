package com.erving.richstep.domain.user.controller.port

import com.erving.richstep.domain.user.domain.User
import com.erving.richstep.domain.user.domain.UserCreate

interface UserService {

    fun create(userCreate: UserCreate): User
}