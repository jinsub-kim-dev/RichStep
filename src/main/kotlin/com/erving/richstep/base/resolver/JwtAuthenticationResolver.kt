package com.erving.richstep.base.resolver

import com.erving.richstep.domain.auth.domain.LoginedUser
import com.erving.richstep.domain.auth.domain.UnauthorizedException
import com.erving.richstep.domain.auth.service.JwtTokenProvider
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class JwtAuthenticationResolver(
    private val jwtTokenProvider: JwtTokenProvider
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == LoginedUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val httpServletRequest = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?: throw UnauthorizedException("요청이 유효하지 않습니다")

        val token = resolveToken(httpServletRequest)
            ?: throw UnauthorizedException("Authorization 헤더가 없습니다")

        val userId = jwtTokenProvider.getUserId(token)
        val email = jwtTokenProvider.getEmail(token)

        return LoginedUser(userId, email)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearer = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return if (bearer.startsWith("Bearer ")) bearer.substring(7) else null
    }
}