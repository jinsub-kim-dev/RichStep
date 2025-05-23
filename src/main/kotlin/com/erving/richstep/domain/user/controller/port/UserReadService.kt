package com.erving.richstep.domain.user.controller.port

import com.erving.richstep.domain.user.domain.User

interface UserReadService {

    fun getById(id: Long): User
}