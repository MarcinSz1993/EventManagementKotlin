package com.marcinsz1993.eventmanagementkotlin.service

import com.marcinsz1993.eventmanagementkotlin.configuration.KafkaConfiguration
import com.marcinsz1993.eventmanagementkotlin.dto.EventDto
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
@EnableConfigurationProperties(KafkaConfiguration::class)
class KafkaMessageProducer(
    val kafkaConfiguration: KafkaConfiguration,
    val eventDtoKafkaTemplate: KafkaTemplate<String,EventDto>
) {
    fun sendCreatedEventMessageToAllEventsTopic(eventDto: EventDto) {
        try {
            val allEventsKotlin = eventDtoKafkaTemplate.send(kafkaConfiguration.allEventsTopicKotlin, eventDto)
            allEventsKotlin.whenComplete { result: SendResult<String?, EventDto?>, ex: Throwable? ->
                if (ex == null) {
                    println("Sent message: $eventDto" + "with offset: " + result.recordMetadata.offset())
                } else { println("Unable to send message: $eventDto")
                }
            }
        } catch (ex: Exception) {
            println("Error: " + ex.message)
        }
    }
    fun sendCancelledMessageToEventCancelledTopic(eventDto: EventDto) {
        try {
            val cancelledEventsKotlin: CompletableFuture<SendResult<String?, EventDto?>> =
                eventDtoKafkaTemplate.send(kafkaConfiguration.cancelledEventsTopicKotlin, eventDto)
            cancelledEventsKotlin.whenComplete { result: SendResult<String?, EventDto?>, ex: Throwable? ->
                if (ex == null) {
                    println("Sent message:  + $eventDto with offset: " + result.recordMetadata.offset())
                } else {
                    println("Unable to send message: $eventDto")
                }
            }
        } catch (ex: java.lang.Exception) {
            println("Error: " + ex.message)
        }
    }
}