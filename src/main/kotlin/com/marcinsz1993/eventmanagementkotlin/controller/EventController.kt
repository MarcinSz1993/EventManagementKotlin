package com.marcinsz1993.eventmanagementkotlin.controller

import com.marcinsz1993.eventmanagementkotlin.dto.EventDto
import com.marcinsz1993.eventmanagementkotlin.request.CreateEventRequest
import com.marcinsz1993.eventmanagementkotlin.request.JoinEventRequest
import com.marcinsz1993.eventmanagementkotlin.request.UpdateEventRequest
import com.marcinsz1993.eventmanagementkotlin.service.EventService
import com.marcinsz1993.eventmanagementkotlin.service.JwtService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("events/")
class EventController (
    val eventService: EventService,
    val jwtService: JwtService
){
    @PostMapping
    fun createEvent(@RequestBody
                    @Valid createEventRequest:CreateEventRequest,
                    @CookieValue token:String):EventDto
    {
        val username = jwtService.extractUsername(token)
        val user = eventService.findByUsername(username)
        return eventService.createEvent(createEventRequest, user)

    }
    @PutMapping
    fun updateEvent(@RequestBody updateEventRequest: UpdateEventRequest,
                    @RequestParam eventId:Long,
                    @CookieValue token:String):ResponseEntity<EventDto>
    {
        val eventDto = eventService.updateEvent(updateEventRequest, eventId, token)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(eventDto)
    }
    @GetMapping
    fun showAllUserEvents(@RequestParam username:String):List<EventDto>
    {
        return eventService.showAllOrganizerEvents(username)
    }

    @PutMapping("/join")
    fun joinEvent(@RequestBody @Valid joinEventRequest: JoinEventRequest,
                  @RequestParam eventName:String,
                  @CookieValue token:String):ResponseEntity<String>
    {
        return try {
            eventService.joinEvent(joinEventRequest, eventName, token)
            ResponseEntity.ok("You joined to the event ${eventName.uppercase()}.")
        } catch (exception:IllegalArgumentException){
            ResponseEntity.badRequest().body(exception.message)
        } catch (e:Throwable){
            throw RuntimeException(e)
        }
    }
    @DeleteMapping
    fun deleteEvent(@RequestParam eventId: Long,
                    @CookieValue token: String):ResponseEntity<String>
    {
        return try {
            val eventName = eventService.deleteEvent(eventId, token)
            ResponseEntity.ok("You deleted event $eventName")
        } catch (exception:IllegalArgumentException)
        {
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.message)
        }
    }

}