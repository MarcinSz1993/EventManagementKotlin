@file:Suppress("SpringJavaInjectionPointsAutowiringInspection")

package com.marcinsz1993.eventmanagementkotlin.service

import com.marcinsz1993.eventmanagementkotlin.dto.EventDto
import com.marcinsz1993.eventmanagementkotlin.model.EventStatus
import com.marcinsz1993.eventmanagementkotlin.repository.UserRepository
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.time.LocalDate
import java.util.*

@Service
data class NotificationService(
    val mailSender: JavaMailSender,
    val userRepository: UserRepository,
    val templateEngine: TemplateEngine,
    ) {
    @Async
    fun sendNotification(eventDto: EventDto) {
        val message = createNewMessage(eventDto)
        mailSender.send(message)
    }
    @Throws(MessagingException::class)
    fun createNewMessage(eventDto: EventDto): MimeMessage? {
        var emailsFromAdultUsers = userRepository.getEmailsFromAdultUsers(LocalDate.now().minusYears(18))
        val mutableListOfEmailsFromAdultUsers = emailsFromAdultUsers?.toMutableList()
        mutableListOfEmailsFromAdultUsers?.remove(eventDto.organiser.email)
        emailsFromAdultUsers = mutableListOfEmailsFromAdultUsers

        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        val context = setThymeleafContext(eventDto)
        var htmlContent = templateEngine.process("email/NewEventNotification", context)
        var subject = "New event for you is waiting  ${eventDto.eventName}"
        if (eventDto.eventStatus == EventStatus.CANCELLED) {
            htmlContent = templateEngine.process("email/EventCancelledNotification", context)
            subject = "Cancellation ${eventDto.eventName.uppercase()}"
        }
        helper.setSubject(subject)
        helper.setText(htmlContent, true)
        for (email: String? in emailsFromAdultUsers!!) {
            helper.addTo(email!!)
        }
        return message
    }
    private fun setThymeleafContext(eventDto: EventDto): Context {
        val context = Context()
        context.setVariable("eventName", eventDto.eventName)
        context.setVariable("eventDescription", eventDto.eventDescription)
        context.setVariable("eventLocation", eventDto.eventLocation)
        context.setVariable("eventDate", eventDto.eventDate)
        context.setVariable("organiserFirstName", eventDto.organiser.firstName)
        context.setVariable("organiserLastName", eventDto.organiser.lastName)
        context.setVariable("organiserUserName", eventDto.organiser.userName)
        context.setVariable("organiserPhoneNumber", eventDto.organiser.phoneNumber)
        return context
    }
}