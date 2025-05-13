package com.erving.richstep.user.controller.port

import com.erving.richstep.user.domain.User
import com.erving.richstep.user.domain.UserCreate

interface UserService {

    fun create(userCreate: UserCreate): User
}