package com.codingub.belarusianhistory.utils

import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion


object TicketUtil {

    fun groupQuestions(args: List<TicketQuestion>): String {
        return buildString {
            args.forEachIndexed { i, arg ->
                append("${i + 1}. ${arg.info}\n")
            }
        }
    }
}