package com.erving.richstep.user.controller.port

import com.erving.richstep.user.domain.User

interface UserReadService {

    fun getById(id: Long): User
}