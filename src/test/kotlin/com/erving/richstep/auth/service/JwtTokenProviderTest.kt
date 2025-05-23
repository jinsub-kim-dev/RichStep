package com.erving.richstep.auth.service

import com.erving.richstep.domain.auth.service.JwtTokenProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.Date

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest(
    @Autowired private val jwtTokenProvider: JwtTokenProvider
) {

    @Test
    fun 토큰을_발급하고_다시_파싱할_수_있다() {
        val token = jwtTokenProvider.issueToken(1L, "test@example.com")

        val parsedId = jwtTokenProvider.getUserId(token)
        val parsedEmail = jwtTokenProvider.getEmail(token)

        Assertions.assertEquals(1L, parsedId)
        Assertions.assertEquals("test@example.com", parsedEmail)
    }

    @Test
    fun 만료된_토큰은_예외가_발생한다() {
        val expiredToken = Jwts.builder()
            .setSubject("1")
            .setIssuedAt(Date(System.currentTimeMillis() - 3600_000))
            .setExpiration(Date(System.currentTimeMillis() - 1000))
            .claim("email", "test@example.com")
            .signWith(Keys.hmacShaKeyFor("my-super-secret-key-that-should-be-very-long".toByteArray()), SignatureAlgorithm.HS256)
            .compact()

        assertThrows<ExpiredJwtException> {
            jwtTokenProvider.parseToken(expiredToken)
        }
    }
}