package com.marcinsz1993.eventmanagementkotlin.repository

import com.marcinsz1993.eventmanagementkotlin.model.Event
import com.marcinsz1993.eventmanagementkotlin.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface EventRepository : JpaRepository<Event?, Long?> {
    fun findAllByOrganizer(organizer: User?): List<Event?>?
    fun findByEventName(eventName: String?): Optional<Event?>?
}
