package com.codingub.belarusianhistory.data.remote.network

object EndPoints {

    const val SIGNUP = "users/signup"
    const val SIGNIN = "users/signin"
    const val AUTHENTICATE = "users/authenticate"

    const val ROLE_CHANGE = "users/changerole"

    const val TICKET = "tickets"
    const val INSERT_TICKET = "tickets/insert"
    const val RESET_TICKET = "tickets/reset"

    const val TQ = "tq"
    const val INSERT_TQ = "tq/insert"
    const val RESET_TQ = "tq/reset"

    const val PQ = "pq"
    const val INSERT_PQ = "pq/insert"
    const val RESET_PQ = "pq/reset"

    const val ACHIEVE = "achieves"

    const val RESULTS = "results"
    const val INSERT_RESULTS = "results/insert"
    const val DELETE_RESULTS = ""

    const val EVENTS = "events"

    const val GROUP = "groups"
    const val INSERT_GROUP = "groups/insert"
    const val RESET_GROUP = "groups/reset"
    const val INVITE_USER_GROUP = "groups/users/invite"
    const val DELETE_USER_GROUP = "groups/users/reset"
}