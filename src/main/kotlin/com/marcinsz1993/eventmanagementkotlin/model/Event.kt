package com.marcinsz1993.eventmanagementkotlin.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(unique = true)
    var eventName: String,
    var eventDescription: String,
    var location:String,
    var maxAttendees:Int,
    var eventDate:LocalDate,
    @Enumerated(value = EnumType.STRING)
    var eventStatus:EventStatus,
    var ticketPrice:Double,
    @Enumerated(value = EnumType.STRING)
    var eventTarget: EventTarget,
    val createdDate:LocalDateTime,
    var modifiedDate: LocalDateTime?,
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "participants_events",
        joinColumns = [
            JoinColumn(name = "event_id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "user_id")
        ]
    )
    @JsonManagedReference
    var participants:List<User?>?,
    @ManyToOne
    @JoinColumn(name = "organizer_id")
    @JsonBackReference
    var organizer:User?
) {

    constructor(
        eventName: String,
        eventDescription: String,
        location: String,
        maxAttendees: Int,
        eventDate: LocalDate,
        eventStatus: EventStatus,
        ticketPrice: Double,
        eventTarget: EventTarget,
        createdDate: LocalDateTime,
        modifiedDate: LocalDateTime?,
        participants: List<User>?,
        organizer: User?
    ) :this(
        id = null,
        eventName = eventName,
        eventDescription = eventDescription,
        location = location,
        maxAttendees = maxAttendees,
        eventDate = eventDate,
        eventStatus = eventStatus,
        ticketPrice = ticketPrice,
        eventTarget = eventTarget,
        createdDate = createdDate,
        modifiedDate = modifiedDate,
        participants = participants,
        organizer = organizer
    )
}






