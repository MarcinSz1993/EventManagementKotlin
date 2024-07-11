package com.marcinsz1993.eventmanagementkotlin.mapper

import com.marcinsz1993.eventmanagementkotlin.dto.EventDto
import com.marcinsz1993.eventmanagementkotlin.model.Event
import com.marcinsz1993.eventmanagementkotlin.model.EventStatus
import com.marcinsz1993.eventmanagementkotlin.model.User
import com.marcinsz1993.eventmanagementkotlin.request.CreateEventRequest
import java.time.LocalDateTime
import java.util.*
import java.util.stream.Collectors


data object EventMapper {
    fun convertEventToEventDto(event: Event): EventDto {
        return EventDto(
            event.eventName,
            event.eventDescription,
            event.location,
            event.maxAttendees,
            event.eventDate,
            event.eventStatus,
            event.ticketPrice,
            event.eventTarget,
            event.createdDate,
            UserMapper.convertUserToOrganiserDto(event.organizer),
            UserMapper.convertListUserToListUserDto(event.participants)
        )
    }
    fun convertCreateEventRequestToEvent(createEventRequest: CreateEventRequest): Event {
        return Event(
            createEventRequest.eventName,
            createEventRequest.eventDescription,
            createEventRequest.location,
            createEventRequest.maxAttendees,
            createEventRequest.eventDate,
            EventStatus.ACTIVE,
            createEventRequest.ticketPrice,
            createEventRequest.eventTarget,
            LocalDateTime.now(),
            null,
            null,
            null
        )
    }

    fun convertCreateEventRequestToEventDto(createEventRequest: CreateEventRequest, user: User): EventDto {
        return EventDto(
            createEventRequest.eventName,
            createEventRequest.eventDescription,
            createEventRequest.location,
            createEventRequest.maxAttendees,
            createEventRequest.eventDate,
            EventStatus.ACTIVE,
            createEventRequest.ticketPrice,
            createEventRequest.eventTarget,
            LocalDateTime.now(),
            UserMapper.convertUserToOrganiserDto(user),
            Collections.emptyList()
        )
    }
    fun convertListEventToListEventDto(eventList: List<Event?>?): List<EventDto> {
        return eventList?.stream()?.map { event: Event? -> convertEventToEventDto(event!!) }
            ?.collect(Collectors.toList()) ?: Collections.emptyList()
    }
}
