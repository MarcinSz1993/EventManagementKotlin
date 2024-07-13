package com.marcinsz1993.eventmanagementkotlin.exception


class EventNotFoundException : RuntimeException {
    constructor(id: Long) : super("The event with id: $id does not exist.")
    constructor(eventName: String) : super("The event with event name: ${eventName.uppercase()} does not exist.")
}

