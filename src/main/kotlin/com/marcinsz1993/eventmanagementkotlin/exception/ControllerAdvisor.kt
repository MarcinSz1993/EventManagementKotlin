package com.marcinsz1993.eventmanagementkotlin.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.function.Consumer

@ControllerAdvice
class ControllerAdvisor {
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        UserNotFoundException::class
    )
    fun userNotFoundHandler(ex: UserNotFoundException): String? {
        return ex.message
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsHandler(ex:BadCredentialsException):String?{
        return ex.message
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EventNotFoundException::class)
    fun eventNotFoundHandler(ex:EventNotFoundException):String?{
        return ex.message
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.LOCKED)
    @ExceptionHandler(EventForecastTooEarlyException::class)
    fun eventExceptionHandler(ex:EventForecastTooEarlyException):String?{
        return ex.message
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NotYourEventException::class)
    fun notYourEventExceptionHandler(ex: NotYourEventException): String? {
        return ex.message
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(ex: MethodArgumentNotValidException): ResponseEntity<*>? {
        val errors = HashMap<String, String?>()
        ex.bindingResult.allErrors
            .forEach(Consumer { error ->
                val field = (error as FieldError).field
                val message = error.getDefaultMessage()
                errors[field] = message
            })
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

}