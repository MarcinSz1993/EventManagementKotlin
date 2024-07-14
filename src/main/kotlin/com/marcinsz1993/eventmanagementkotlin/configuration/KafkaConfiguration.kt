package com.marcinsz1993.eventmanagementkotlin.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
@ConfigurationProperties(prefix = "spring.kafka.config")
data class KafkaConfiguration(
    val allEventsTopicKotlin:String,
    val cancelledEventsTopicKotlin:String,
    val groupId:String
)