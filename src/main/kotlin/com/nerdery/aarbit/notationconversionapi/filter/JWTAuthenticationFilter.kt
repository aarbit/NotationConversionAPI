package com.nerdery.aarbit.notationconversionapi.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.nerdery.aarbit.notationconversionapi.helper.EnvVarUtil
import com.nerdery.aarbit.notationconversionapi.helper.StringConstant
import com.nerdery.aarbit.notationconversionapi.helper.getEnv
import com.nerdery.aarbit.notationconversionapi.model.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(private val authMan: AuthenticationManager): UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val creds = ObjectMapper().registerModule(KotlinModule()).readValue(request.inputStream, User::class.java)
            return authMan.authenticate(
                UsernamePasswordAuthenticationToken(creds.username, creds.password)
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val token = Jwts.builder().setSubject((authResult.principal as UserDetails).username)
            .setExpiration(Date.from(Instant.now().plus(5, ChronoUnit.MINUTES)))
            .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(getEnv(EnvVarUtil.SECRET_KEY).toByteArray())).compact()
        response.addHeader(StringConstant.AUTHORIZATION_HEADER_NAME, "${StringConstant.AUTHORIZATION_HEADER_PREFIX}$token")
    }
}