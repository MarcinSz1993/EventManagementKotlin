package com.marcinsz1993.eventmanagementkotlin.repository

import com.marcinsz1993.eventmanagementkotlin.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*


@Repository
interface UserRepository : JpaRepository<User?, Long?> {
    fun findByUsernameField(username: String?): Optional<User>
    fun findByEmail(email: String): Optional<User>

    @Query("SELECT u.email FROM User u WHERE u.birthDate < :date")
    fun getEmailsFromAdultUsers(@Param("date") date: LocalDate?): List<String?>?
}

