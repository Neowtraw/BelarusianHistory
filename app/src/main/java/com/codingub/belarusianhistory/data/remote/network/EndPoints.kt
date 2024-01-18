package com.codingub.belarusianhistory.data.remote.network

object EndPoints {

    const val SIGNUP = "users/signup"
    const val SIGNIN = "users/signin"
    const val AUTHENTICATE = "users/authenticate"

    const val ROLE_CHANGE = "users/changerole"

    const val TICKET = "tickets"
    const val INSERT_TICKET = "tickets/insert"
    const val RESET_TICKET = "tickets/reset/name"
    const val RESET_TICKETS = "tickets/reset/ids"

    const val TQ = "tq"
    const val INSERT_TQ = "tq/insert"
    const val RESET_TQ = "tq/reset/id"
    const val RESET_TQS = "tq/reset/ids"

    const val PQ = "pq"
    const val INSERT_PQ = "pq/insert"
    const val RESET_PQ = "pq/reset/id"
    const val RESET_PQS = "pq/reset/ids"

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

    const val MAP_TYPE = "maptype"
    const val MAP = "maptype/period/map"
    const val DELETE_MAP = "maptype/period/map/delete"
    const val ADD_LABEL = "maptype/period/map/label/add"
    const val UPDATE_LABEL = "maptype/period/map/label/update"
}