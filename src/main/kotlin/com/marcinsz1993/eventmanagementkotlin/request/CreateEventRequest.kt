package com.marcinsz1993.eventmanagementkotlin.request

import com.marcinsz1993.eventmanagementkotlin.model.EventTarget
import jakarta.persistence.Column
import jakarta.validation.constraints.*
import java.time.LocalDate

data class CreateEventRequest(
    @Column(unique = true)
    @NotBlank(message = "Event name is required")
    val eventName:String,
    @NotBlank(message = "You must describe your event")
    val  eventDescription:String,
    @NotBlank(message = "Location is required")
    val location:String,
    @NotNull(message = "Max attendees is required")
    @Positive(message = "A number must be greater than 0")
    val maxAttendees :Int,
    @FutureOrPresent(message = "A event date cannot be past")
    val eventDate: LocalDate,
    @PositiveOrZero(message = "A price must not be below 0")
    val ticketPrice:Double,
    @NotNull(message = "Event target is required")
    val eventTarget: EventTarget
)
