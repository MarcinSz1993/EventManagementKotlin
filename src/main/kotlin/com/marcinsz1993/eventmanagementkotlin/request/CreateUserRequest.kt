package com.marcinsz1993.eventmanagementkotlin.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Past
import org.hibernate.validator.constraints.Length
import java.time.LocalDate

data class CreateUserRequest(
    @NotBlank(message = "First name is required")
    val firstName:String,
    @NotBlank(message = "Last name is required")
    val lastName:String,
    @Email(message = "Acceptable pattern is: example@example.com")
    @NotBlank(message = "Email is required")
    val email:String,
    @NotBlank(message = "Username is required")
    @Length(min = 5,max = 10,message = "Username must have between 5 and 10 characters")
    val username:String,
    @NotBlank(message = "Password is required")
    @Length(min = 5,message = "Password must have at least 5 characters")
    val password:String,
    @Past(message = "A day of birth must be past")
    @NotNull(message = "A day of birth is required")
    val birthDate: LocalDate,
    @Length(min = 9,max = 9,message = "Phone number must have exactly 9 numbers")
    @NotBlank(message = "Phone number is required")
    val phoneNumber:String,
    @Length(min = 10,max = 10,message = "Account number must have exactly 10 numbers")
    @NotBlank(message = "Account number is required")
    val accountNumber:String
)