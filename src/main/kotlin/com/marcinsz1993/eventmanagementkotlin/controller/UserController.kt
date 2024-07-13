package com.marcinsz1993.eventmanagementkotlin.controller

import com.marcinsz1993.eventmanagementkotlin.model.AuthenticationResponse
import com.marcinsz1993.eventmanagementkotlin.model.CreateUserResponse
import com.marcinsz1993.eventmanagementkotlin.request.AuthenticationRequest
import com.marcinsz1993.eventmanagementkotlin.request.CreateUserRequest
import com.marcinsz1993.eventmanagementkotlin.service.UserService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("users/")
class UserController(
    val userService: UserService
){
    @PostMapping("/")
    fun createUser(@RequestBody @Valid createUserRequest: CreateUserRequest,
                   servletResponse:HttpServletResponse):CreateUserResponse{
        val createUserResponse = userService.createUser(createUserRequest)
        val token = createUserResponse.token
        if (token != null) {
            addTokenToCookie(token,servletResponse)
        }
        return createUserResponse
    }
    @PostMapping("/login")
    fun login(@RequestBody @Valid authenticationRequest: AuthenticationRequest,
              servletResponse: HttpServletResponse):AuthenticationResponse{
        val login = userService.login(authenticationRequest)
        val token = login.token
        if (token != null) {
            addTokenToCookie(token,servletResponse)
        }
        return login
    }

   private fun addTokenToCookie(token:String, servletResponse: HttpServletResponse){
        val cookie = Cookie("token", token)
        cookie.isHttpOnly = true
        cookie.maxAge = 24*60*60
        cookie.path = "/"
        servletResponse.addCookie(cookie)
    }

}
