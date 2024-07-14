package com.marcinsz1993.eventmanagementkotlin.service

import com.marcinsz1993.eventmanagementkotlin.exception.BadCredentialsException
import com.marcinsz1993.eventmanagementkotlin.exception.UserNotFoundException
import com.marcinsz1993.eventmanagementkotlin.mapper.UserMapper
import com.marcinsz1993.eventmanagementkotlin.model.AuthenticationResponse
import com.marcinsz1993.eventmanagementkotlin.model.CreateUserResponse
import com.marcinsz1993.eventmanagementkotlin.repository.UserRepository
import com.marcinsz1993.eventmanagementkotlin.request.AuthenticationRequest
import com.marcinsz1993.eventmanagementkotlin.request.CreateUserRequest
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtService: JwtService,
    val authenticationManager: AuthenticationManager
) {
    @Transactional
    fun createUser(createUserRequest: CreateUserRequest): CreateUserResponse {
        val newUser = UserMapper.convertCreateUserRequestToUser(createUserRequest)
        newUser.passwordField = passwordEncoder.encode(createUserRequest.password)
        userRepository.save(newUser)
        val userDto = UserMapper.convertUserToUserDto(newUser)
        val token = jwtService.generateToken(newUser)
        return CreateUserResponse(userDto, token)
    }

    @Throws(BadCredentialsException::class)
    fun login(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationRequest.username,
                    authenticationRequest.password
                )
            )
        } catch (exception: AuthenticationException) {
            throw BadCredentialsException()
        }

        val user = userRepository.findByUsernameField(authenticationRequest.username).orElseThrow {
            UserNotFoundException(authenticationRequest.username)
        }
        val token = jwtService.generateToken(user)
        return AuthenticationResponse(token)
    }

}