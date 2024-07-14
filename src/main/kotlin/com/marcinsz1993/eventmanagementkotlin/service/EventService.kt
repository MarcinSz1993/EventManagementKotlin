package com.marcinsz1993.eventmanagementkotlin.service

import com.marcinsz1993.eventmanagementkotlin.dto.EventDto
import com.marcinsz1993.eventmanagementkotlin.exception.EventNotFoundException
import com.marcinsz1993.eventmanagementkotlin.exception.NotYourEventException
import com.marcinsz1993.eventmanagementkotlin.exception.UserNotFoundException
import com.marcinsz1993.eventmanagementkotlin.mapper.EventMapper
import com.marcinsz1993.eventmanagementkotlin.mapper.UserMapper
import com.marcinsz1993.eventmanagementkotlin.model.EventStatus
import com.marcinsz1993.eventmanagementkotlin.model.User
import com.marcinsz1993.eventmanagementkotlin.repository.EventRepository
import com.marcinsz1993.eventmanagementkotlin.repository.UserRepository
import com.marcinsz1993.eventmanagementkotlin.request.CreateEventRequest
import com.marcinsz1993.eventmanagementkotlin.request.JoinEventRequest
import com.marcinsz1993.eventmanagementkotlin.request.UpdateEventRequest
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
data class EventService(
    val eventRepository: EventRepository,
    val userRepository: UserRepository,
    val jwtService: JwtService,
    val kafkaMessageProducer: KafkaMessageProducer
) {
    @Transactional
    fun createEvent(createEventRequest: CreateEventRequest, user: User):EventDto {
        val event = EventMapper.convertCreateEventRequestToEvent(createEventRequest)
        event.organizer = user
        eventRepository.save(event)
        val eventDto = EventMapper.convertCreateEventRequestToEventDto(createEventRequest, user)
        kafkaMessageProducer.sendCreatedEventMessageToAllEventsTopic(eventDto)
        return eventDto
    }

    fun updateEvent(updateEventRequest: UpdateEventRequest, eventId: Long, token: String):EventDto {
        val foundEvent = eventRepository.findById(eventId).orElseThrow { EventNotFoundException(eventId) }

        val organiserUsername = foundEvent?.organizer?.usernameField
        val usernameExtractedFromToken = jwtService.extractUsername(token)

        val eventName = updateEventRequest.eventName
        val eventDescription = updateEventRequest.eventDescription
        val location = updateEventRequest.location
        val maxAttendees = updateEventRequest.maxAttendees
        val eventDate = updateEventRequest.eventDate
        val ticketPrice = updateEventRequest.ticketPrice
        val eventTarget = updateEventRequest.eventTarget

        if (organiserUsername != usernameExtractedFromToken) {
            throw NotYourEventException()
        }

        if (!eventName.isNullOrEmpty()) {
            foundEvent.eventName = eventName

        }
        if (!eventDescription.isNullOrEmpty()) {
            foundEvent.eventDescription = eventDescription

        }
        if (!location.isNullOrEmpty()) {
            foundEvent.location = location
        }
        if (maxAttendees != null) {
            foundEvent.maxAttendees = maxAttendees
        }
        if (eventDate != null) {
            foundEvent.eventDate = eventDate
        }
        if (ticketPrice != null) {
            foundEvent.ticketPrice = ticketPrice
        }
        if (eventTarget != null) {
            foundEvent.eventTarget = eventTarget
        }

        foundEvent.modifiedDate = LocalDateTime.now()
        eventRepository.save(foundEvent)
        return EventMapper.convertEventToEventDto(foundEvent)

    }

    fun showAllOrganizerEvents(username:String):List<EventDto>{
        val user = userRepository.findByUsernameField(username).orElseThrow { UserNotFoundException.forUsername(username) }
        val allByOrganizer = eventRepository.findAllByOrganizer(user)
        return EventMapper.convertListEventToListEventDto(allByOrganizer)
    }
    @Transactional
    fun joinEvent(joinEventRequest: JoinEventRequest, eventName:String, token:String){
        val username = jwtService.extractUsername(token)
        val foundUserByToken = userRepository.findByUsernameField(username).orElseThrow()
        if(foundUserByToken.email != joinEventRequest.email){
            throw IllegalArgumentException("You can use your email only!")
        }
        val foundEvent = eventRepository.findByEventName(eventName)?.orElseThrow { EventNotFoundException(eventName) }
        val user = userRepository.findByEmail(joinEventRequest.email).orElseThrow { UserNotFoundException(joinEventRequest.email) }

        UserMapper.convertUserToUserDto(user)
        if (foundEvent?.participants?.contains(user) == true){
            throw IllegalArgumentException("You already joined to this event!")
        } else if (!isUserAdult(user.birthDate)){
            throw IllegalArgumentException("You are too young to join this event!")
        } else if (foundEvent?.eventStatus == EventStatus.COMPLETED){
            throw IllegalArgumentException("Sorry, this event is full.")
        } else if (foundEvent?.eventStatus == EventStatus.CANCELLED){
            throw IllegalArgumentException("You cannot join to the event because this event has been cancelled.")
        }
        val updatedParticipants = foundEvent?.participants?.toMutableList()
        updatedParticipants?.add(user)
        foundEvent?.participants = updatedParticipants

        if(foundEvent?.participants?.size == foundEvent?.maxAttendees){
            foundEvent?.eventStatus = EventStatus.COMPLETED
        }
        eventRepository.save(foundEvent!!)
    }
    @Transactional
    fun deleteEvent(eventId: Long,token: String):String{
        val eventToDelete = eventRepository.findById(eventId).orElseThrow { EventNotFoundException(eventId) }
        val organiserUsername = eventToDelete?.organizer?.usernameField
        val usernameLoggedUser = jwtService.extractUsername(token)
        val eventName = eventToDelete?.eventName
        val eventDto = EventMapper.convertEventToEventDto(eventToDelete!!)
        eventDto.eventStatus = EventStatus.CANCELLED
        if (usernameLoggedUser != organiserUsername){
            throw IllegalArgumentException("You can delete your events only!")
        }
        eventRepository.deleteById(eventId)
        return eventName!!
    }

    fun findByUsername(username: String): User {
            return userRepository.findByUsernameField(username).orElseThrow()
        }

        fun isUserAdult(dateOfBirth:LocalDate):Boolean{
            return dateOfBirth.isBefore(LocalDate.now().minusYears(18))
        }
    }



