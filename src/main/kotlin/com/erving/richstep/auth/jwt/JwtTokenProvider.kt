package com.erving.richstep.auth.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expirationMillis: Long
) {

    private val key: Key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun issueToken(userId: Long, email: String): String {
        val now = Date()
        val expiry = Date(now.time + expirationMillis)

        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(expiry)
            .claim("email", email)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun parseToken(token: String): Jws<Claims> {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
    }

    fun getUserId(token: String): Long {
        return parseToken(token).body.subject.toLong()
    }

    fun getEmail(token: String): String {
        return parseToken(token).body["email"] as String
    }
}