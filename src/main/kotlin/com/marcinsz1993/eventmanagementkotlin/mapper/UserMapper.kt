package com.marcinsz1993.eventmanagementkotlin.mapper

import com.marcinsz1993.eventmanagementkotlin.dto.OrganiserDto
import com.marcinsz1993.eventmanagementkotlin.dto.UserDto
import com.marcinsz1993.eventmanagementkotlin.model.Role
import com.marcinsz1993.eventmanagementkotlin.model.User
import com.marcinsz1993.eventmanagementkotlin.request.CreateUserRequest

data object UserMapper {
    fun convertUserToUserDto(user: User): UserDto {
        return UserDto(
                user.id,
                user.firstName,
                user.lastName,
                user.email,
                user.username,
                user.birthDate,
                user.role,
                user.phoneNumber,
                user.accountNumber,
                user.accountStatus)
    }

    fun convertCreateUserRequestToUser(createUserRequest: CreateUserRequest):User{
        return User(
            createUserRequest.firstName,
            createUserRequest.lastName,
            createUserRequest.email,
            createUserRequest.username,
            createUserRequest.password,
            createUserRequest.birthDate,
            Role.USER,
            createUserRequest.phoneNumber,
            createUserRequest.accountNumber,
            "ACTIVE"
        )
    }

    fun convertUserToOrganiserDto(user: User?):OrganiserDto{
        return OrganiserDto(
            user!!.firstName,
            user.lastName,
            user.username,
            user.email,
            user.phoneNumber
        )
    }

    fun convertListUserToListUserDto(userList: List<User?>?): List<UserDto?>? {
        return if (userList == null) {
            emptyList<UserDto>()
        } else userList.stream()
            .map { user: User? -> convertUserToUserDto(user!!) }
            .toList()
    }
}

