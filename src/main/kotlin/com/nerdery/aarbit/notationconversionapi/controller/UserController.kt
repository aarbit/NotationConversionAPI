package com.nerdery.aarbit.notationconversionapi.controller

import com.nerdery.aarbit.notationconversionapi.helper.StringConstant
import com.nerdery.aarbit.notationconversionapi.model.User
import com.nerdery.aarbit.notationconversionapi.repository.UserInfo
import com.nerdery.aarbit.notationconversionapi.repository.UserInfoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController @Autowired constructor(private val userInfoRepository: UserInfoRepository) {
    @PostMapping(StringConstant.SIGNUP_URL)
    fun signup(@RequestBody user: User) {
        val userToSave = UserInfo(0,user.username, BCryptPasswordEncoder().encode(user.password))
        userInfoRepository.save(userToSave)
    }
}