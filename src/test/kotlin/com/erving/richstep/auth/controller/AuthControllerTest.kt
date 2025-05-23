package com.erving.richstep.auth.controller

import com.erving.richstep.domain.auth.domain.LoginRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest(
    @Autowired val mockMvc: MockMvc
) {

    @Test
    fun `정상 로그인 시 토큰이 반환된다`() {
        // 사전: 이메일 유저가 DB에 존재해야 함
        val request = LoginRequest("less8430@naver.com")

        mockMvc.post("/api/auth/login") {
            contentType = APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            jsonPath("$.accessToken") { exists() }
        }
    }

    @Test
    fun `등록되지 않은 이메일이면 401이 반환된다`() {
        val request = LoginRequest("unknown@example.com")

        mockMvc.post("/api/auth/login") {
            contentType = APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
            status { isUnauthorized() }
            jsonPath("$.message") { value("등록되지 않은 이메일입니다") }
        }
    }
}