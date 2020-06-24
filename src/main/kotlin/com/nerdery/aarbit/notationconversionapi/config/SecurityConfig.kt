package com.nerdery.aarbit.notationconversionapi.config

import com.nerdery.aarbit.notationconversionapi.filter.JWTAuthenticationFilter
import com.nerdery.aarbit.notationconversionapi.filter.JWTAuthorizationFilter
import com.nerdery.aarbit.notationconversionapi.helper.StringConstant
import com.nerdery.aarbit.notationconversionapi.service.impl.UserDetailsServiceImpl
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
@Configuration
class SecurityConfig constructor(private val userDetailsService: UserDetailsServiceImpl) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, StringConstant.SIGNUP_URL).permitAll()
            .antMatchers(HttpMethod.GET, "/swagger-ui.html", "/webjars/**", "/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/swagger-resources/configuration/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(JWTAuthenticationFilter(authenticationManager()))
            .addFilter(JWTAuthorizationFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(BCryptPasswordEncoder())
    }
}