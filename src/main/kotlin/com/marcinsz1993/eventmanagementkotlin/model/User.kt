package com.marcinsz1993.eventmanagementkotlin.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val firstName:String,
    val lastName:String,
    @Column(unique = true)
    val email:String,
    val usernameField:String,
    var passwordField:String,
    val birthDate:LocalDate,
    @Enumerated(value = EnumType.STRING)
    val role:Role,
    val phoneNumber:String,
    val accountNumber:String,
    val accountStatus:String,
    @ManyToMany(mappedBy = "participants", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference
    val events:List<Event> = emptyList(),
    @OneToMany(mappedBy = "organizer")
    val organizedEvents:List<Event> = emptyList()
) : UserDetails
{
    constructor(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        birthDate: LocalDate,
        role: Role,
        phoneNumber: String,
        accountNumber: String,
        accountStatus: String
    ) : this (
        id = null,
        firstName = firstName,
        lastName = lastName,
        email = email,
        usernameField = username,
        passwordField = password,
        birthDate = birthDate,
        role = role,
        phoneNumber = phoneNumber,
        accountNumber = accountNumber,
        accountStatus = accountStatus,
        events = emptyList(),
        organizedEvents = emptyList()

    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return passwordField
    }

    override fun getUsername(): String {
        return usernameField
    }

}