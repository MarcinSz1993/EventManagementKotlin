package com.marcinsz1993.eventmanagementkotlin.request

import com.marcinsz1993.eventmanagementkotlin.model.EventTarget
import java.time.LocalDate

data class UpdateEventRequest(
    val eventName:String?,
    val eventDescription:String?,
    val location:String?,
    val maxAttendees:Int?,
    val eventDate: LocalDate?,
    val ticketPrice:Double?,
    val eventTarget: EventTarget?
)