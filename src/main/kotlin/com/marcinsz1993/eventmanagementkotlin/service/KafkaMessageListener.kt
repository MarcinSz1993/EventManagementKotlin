package com.marcinsz1993.eventmanagementkotlin.service

import com.marcinsz1993.eventmanagementkotlin.dto.EventDto
import com.marcinsz1993.eventmanagementkotlin.model.EventTarget
import jakarta.mail.MessagingException
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import kotlin.jvm.Throws
@Service
class KafkaMessageListener(
    val notificationService: NotificationService
) {
    @Throws(MessagingException::class)
    @KafkaListener(topics = ["\${spring.kafka.config.allEventsTopicKotlin}"], groupId = "\${spring.kafka.config.groupId}")
    fun consumeCreateEventMessage(eventDto: EventDto){
        if (eventDto.eventTarget == EventTarget.ADULTS_ONLY){
            notificationService.sendNotification(eventDto)
        }
    }
    @Throws(MessagingException::class)
    @KafkaListener(topics = ["\${spring.kafka.config.cancelledEventsTopicKotlin}"], groupId = "\${spring.kafka.config.groupId}")
    fun consumeEventCancelledMessage(eventDto: EventDto){
        notificationService.sendNotification(eventDto)
    }
}