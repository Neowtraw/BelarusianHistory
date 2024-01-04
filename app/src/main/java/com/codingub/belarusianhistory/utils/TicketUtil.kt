package com.codingub.belarusianhistory.utils

import com.codingub.belarusianhistory.data.models.tickets.TicketQuestionDto


object TicketUtil {

    fun groupQuestions(args: List<TicketQuestionDto>): String {
        return buildString {
            args.forEachIndexed { i, arg ->
                append("${i + 1}. ${arg.info}\n")
            }
        }
    }
}