package com.nerdery.aarbit.notationconversionapi.filter

import com.nerdery.aarbit.notationconversionapi.helper.EnvVarUtil
import com.nerdery.aarbit.notationconversionapi.helper.StringConstant
import com.nerdery.aarbit.notationconversionapi.helper.getEnv
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(authenticationManager: AuthenticationManager) : BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(StringConstant.AUTHORIZATION_HEADER_NAME)
        if(header == null || !header.startsWith(StringConstant.AUTHORIZATION_HEADER_PREFIX)) {
            chain.doFilter(request, response)
            return
        }
        val token = header.replace(StringConstant.AUTHORIZATION_HEADER_PREFIX, "")
        val user = Jwts.parser()
            .setSigningKey(Base64.getEncoder().encodeToString(getEnv(EnvVarUtil.SECRET_KEY).toByteArray()))
            .parseClaimsJws(token).body.subject
        val authentication = if(user != null) {
            UsernamePasswordAuthenticationToken(user, null, emptyList())
        } else {
            null
        }
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}