package com.marcinsz1993.eventmanagementkotlin.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class JoinEventRequest(
    @Email(message = "Acceptable pattern is: example@example.com")
    @NotBlank(message = "Email is required")
    val email:String
)