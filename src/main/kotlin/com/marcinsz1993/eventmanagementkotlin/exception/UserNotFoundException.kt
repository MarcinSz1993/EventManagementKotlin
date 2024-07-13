package com.marcinsz1993.eventmanagementkotlin.exception


class UserNotFoundException(message: String?) : RuntimeException(message) {
    companion object {
        fun forUsername(username:String):UserNotFoundException{
            return UserNotFoundException("There is no user with username: + $username")
        }

        fun forEmail(email:String):UserNotFoundException{
            return UserNotFoundException("There is no user with email: $email")
        }
    }
}
