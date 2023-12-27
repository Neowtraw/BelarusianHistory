package com.codingub.belarusianhistory.data.remote

import com.codingub.belarusianhistory.data.remote.network.EndPoints.ACHIEVE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.AUTHENTICATE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.DELETE_USER_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.EVENTS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INVITE_USER_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.ROLE_CHANGE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNIN
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.TQ
import com.codingub.belarusianhistory.sdk.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.requests.CreateGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.DeletePqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertPqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RoleRequest
import com.codingub.belarusianhistory.data.remote.network.requests.GroupRequest
import com.codingub.belarusianhistory.data.remote.network.responses.AchieveResponse
import com.codingub.belarusianhistory.data.remote.network.responses.EventResponse
import com.codingub.belarusianhistory.data.remote.network.responses.GroupResponse
import com.codingub.belarusianhistory.data.remote.network.responses.PqResponse
import com.codingub.belarusianhistory.data.remote.network.responses.TicketResponse
import com.codingub.belarusianhistory.data.remote.network.responses.TokenResponse
import com.codingub.belarusianhistory.data.remote.network.responses.TqResponse
import com.codingub.belarusianhistory.sdk.models.Event
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

//interface for HTTP requests MongoDb
interface HistoryAppApi {

    /*
        Authentication
     */
    @POST(SIGNUP)
    suspend fun signUp(
        @Body request: RegisterRequest
    )

    @POST(SIGNIN)
    suspend fun signIn(
        @Body request: LoginRequest
    ) : TokenResponse

    @GET(AUTHENTICATE)
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

    /*
        Users
     */
    @POST(ROLE_CHANGE)
    suspend fun changeRoleByLogin(
        @Body request: RoleRequest
    )

    /*
        Groups
     */
    @POST(INSERT_GROUP)
    suspend fun createGroup(
        @Body request: CreateGroupRequest
    )

    @POST(RESET_GROUP)
    suspend fun deleteGroup(
        @Body request: GroupRequest
    )

    @POST(INVITE_USER_GROUP)
    suspend fun inviteUserToGroup(
        @Body request: GroupRequest
    )

    @POST(DELETE_USER_GROUP)
    suspend fun deleteUserFromGroup(
        @Body request: GroupRequest
    )

    @GET("$GROUP/")
    suspend fun getAllGroups(
        @Query("user") login: String
    ) : GroupResponse

    /*
       Ticket
    */

    @GET(TICKET)
    suspend fun getAllTickets() : TicketResponse

    @POST(INSERT_TICKET)
    suspend fun insertTicket(
        @Body request: InsertTicketRequest
    )

    @POST(RESET_TICKET)
    suspend fun resetTicket(
        @Body request: DeleteTicketRequest
    )

    /*
      TicketQuestion
   */

    @GET(TQ)
    suspend fun getAllTq() : TqResponse

    @GET("$TQ/")
    suspend fun getTqByTicketId(
        @Query("id") ticketId: String
    ) : TqResponse

    @POST(INSERT_TICKET)
    suspend fun insertTq(
        @Body request: InsertTqRequest
    )

    @POST(RESET_TICKET)
    suspend fun deleteTq(
        @Body request: DeleteTqRequest
    )

    /*
      PracticeQuestion
   */

    @GET("$PQ/")
    suspend fun getPqByTqId(
        @Query("id") tqId: String
    ) : PqResponse

    @POST(INSERT_PQ)
    suspend fun insertPq(
        @Body request: InsertPqRequest
    )

    @POST(RESET_PQ)
    suspend fun deletePq(
        @Body request: DeletePqRequest
    )

    /*
      Achieves
   */

    @GET(ACHIEVE)
    suspend fun getAllAchieves() : List<Achieve>

    @GET("$ACHIEVE/")
    suspend fun getTypeAchieves(
        @Query("type") type: Int
    ): AchieveResponse

    /*
        Events
     */

    @GET(EVENTS)
    suspend fun getAllEvents() : EventResponse


}