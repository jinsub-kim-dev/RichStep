package com.erving.richstep.domain.auth.controller.port

import com.erving.richstep.domain.auth.domain.LoginRequest
import com.erving.richstep.domain.auth.domain.LoginResponse

interface AuthService {

    fun login(loginRequest: LoginRequest): LoginResponse
}