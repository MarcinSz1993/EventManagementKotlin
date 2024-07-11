package com.marcinsz1993.eventmanagementkotlin.model

import com.marcinsz1993.eventmanagementkotlin.dto.UserDto

data class CreateUserResponse(
    val userDto: UserDto,
    val token:String?
)