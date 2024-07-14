package com.marcinsz1993.eventmanagementkotlin.service

import com.marcinsz1993.eventmanagementkotlin.api.WeatherFromApi
import com.marcinsz1993.eventmanagementkotlin.configuration.WeatherApiConfiguration
import com.marcinsz1993.eventmanagementkotlin.dto.WeatherDto
import com.marcinsz1993.eventmanagementkotlin.exception.EventForecastTooEarlyException
import com.marcinsz1993.eventmanagementkotlin.exception.EventNotFoundException
import com.marcinsz1993.eventmanagementkotlin.mapper.PolishCharactersMapper
import com.marcinsz1993.eventmanagementkotlin.mapper.WeatherMapper
import com.marcinsz1993.eventmanagementkotlin.repository.EventRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class WeatherService(
    val webClient: WebClient,
    val eventRepository: EventRepository,
    val weatherApiConfiguration: WeatherApiConfiguration
) {
    fun weatherFromApi(eventId: Long): WeatherDto? {
        val foundEvent = eventRepository.findById(eventId).orElseThrow { EventNotFoundException(eventId) }
        if (validateDate(foundEvent!!.eventDate)) {
            throw EventForecastTooEarlyException("You can check the forecast only 14 days before the event.")
        }
        return webClient
            .get()
            .uri {
                UriComponentsBuilder.fromUriString(weatherApiConfiguration.baseUrl)
                    .queryParam(
                        "q",
                        PolishCharactersMapper.removePolishCharactersFromLocationField(foundEvent.location)
                    )
                    .queryParam("dt", foundEvent.eventDate)
                    .queryParam("key", weatherApiConfiguration.apiKey)
                    .build()
                    .toUri()
            }
            .header("accept", "application/json")
            .retrieve()
            .bodyToMono(WeatherFromApi::class.java)
            .map { WeatherMapper.convertWeatherFromApiToWeatherDto(it) }
            .block()
    }
}

    private fun validateDate(eventDate: LocalDate): Boolean {
        val days = ChronoUnit.DAYS.between(LocalDate.now(), eventDate)
        return days >= 14
}


