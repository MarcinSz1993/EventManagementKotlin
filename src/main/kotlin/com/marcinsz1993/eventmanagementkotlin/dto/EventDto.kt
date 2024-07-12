package com.marcinsz1993.eventmanagementkotlin.dto

import com.marcinsz1993.eventmanagementkotlin.model.EventStatus
import com.marcinsz1993.eventmanagementkotlin.model.EventTarget
import java.time.LocalDate
import java.time.LocalDateTime

data class EventDto(
    val eventName:String,
    val eventDescription:String,
    val eventLocation:String,
    val maxAttendees:Int,
    val eventDate:LocalDate,
    var eventStatus: EventStatus,
    val ticketPrice:Double,
    val eventTarget: EventTarget,
    val createdDate:LocalDateTime,
    val organiser:OrganiserDto,
    val participants: List<UserDto?>?
)