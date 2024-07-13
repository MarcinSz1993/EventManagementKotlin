package com.marcinsz1993.eventmanagementkotlin.request

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class AuthenticationRequest(
    @Length(min = 5, max = 10)
    @NotBlank
    val username:String,
    @Length(min = 5)
    @NotBlank
    val password:String
)