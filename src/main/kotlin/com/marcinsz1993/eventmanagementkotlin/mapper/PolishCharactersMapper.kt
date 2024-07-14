package com.marcinsz1993.eventmanagementkotlin.mapper

import org.springframework.stereotype.Component

@Component
object PolishCharactersMapper {
    fun removePolishCharactersFromLocationField(input:String?):String{
        if (input?.isEmpty() == true || input == null){
            return "The location is not correct."
        }
        return input
                    .replace("[ąĄ]".toRegex(), "a")
                    .replace("[ęĘ]".toRegex(), "e")
                    .replace("[ćĆ]".toRegex(), "c")
                    .replace("[łŁ]".toRegex(), "l")
                    .replace("[ńŃ]".toRegex(), "n")
                    .replace("[óÓ]".toRegex(), "o")
                    .replace("[śŚ]".toRegex(), "s")
                    .replace("[źŹżŻ]".toRegex(), "z")
    }
}