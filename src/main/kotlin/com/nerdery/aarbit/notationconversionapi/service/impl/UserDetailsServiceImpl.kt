package com.nerdery.aarbit.notationconversionapi.service.impl

import com.nerdery.aarbit.notationconversionapi.repository.UserInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl @Autowired constructor(private val userInfoRepository: UserInfoRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userInfoRepository.findByUsername(username)
        return user?.let {
            User(it.username, it.password, emptyList()) }?: throw IllegalArgumentException()
    }
}