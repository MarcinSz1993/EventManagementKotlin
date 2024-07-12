package com.marcinsz1993.eventmanagementkotlin.dto

import com.marcinsz1993.eventmanagementkotlin.model.Role
import java.time.LocalDate

data class UserDto(
    val userId:Long?,
    val firstName:String,
    val lastName:String,
    val email:String,
    val username:String,
    val birthDate:LocalDate,
    val role: Role,
    val phoneNumber:String,
    val accountNumber:String,
    val accountStatus:String
)