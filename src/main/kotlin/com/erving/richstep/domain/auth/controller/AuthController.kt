package com.erving.richstep.domain.auth.controller

import com.erving.richstep.domain.auth.controller.port.AuthService
import com.erving.richstep.domain.auth.domain.LoginRequest
import com.erving.richstep.domain.auth.domain.LoginResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val response = this.authService.login(request)
        return ResponseEntity.ok(response)
    }
}