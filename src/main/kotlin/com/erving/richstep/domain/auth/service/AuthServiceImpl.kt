package com.erving.richstep.domain.auth.service

import com.erving.richstep.domain.auth.controller.port.AuthService
import com.erving.richstep.domain.auth.domain.LoginRequest
import com.erving.richstep.domain.auth.domain.LoginResponse
import com.erving.richstep.domain.auth.domain.UnauthorizedException
import com.erving.richstep.domain.user.service.port.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) : AuthService {

    override fun login(loginRequest: LoginRequest): LoginResponse {
        val user = this.userRepository.findByEmail(loginRequest.email)
            ?: throw UnauthorizedException("등록되지 않은 이메일입니다")

        val accessToken = this.jwtTokenProvider.issueToken(user.id!!, user.email)
        return LoginResponse(accessToken)
    }
}