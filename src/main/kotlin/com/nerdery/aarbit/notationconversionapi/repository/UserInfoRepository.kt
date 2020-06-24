package com.nerdery.aarbit.notationconversionapi.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Repository
interface UserInfoRepository: JpaRepository<UserInfo, String> {
    fun findByUsername(username: String): UserInfo?
}

@Entity
@Table(name = "user_info")
data class UserInfo(
    @Id
    @GeneratedValue
    val id: Long,
    val username: String,
    val password: String
)