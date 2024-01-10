package com.codingub.belarusianhistory.data.remote

import com.codingub.belarusianhistory.data.models.achieves.AchieveDto
import com.codingub.belarusianhistory.data.remote.network.Cacheable
import com.codingub.belarusianhistory.data.remote.network.EndPoints.ACHIEVE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.AUTHENTICATE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.DELETE_RESULTS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.DELETE_USER_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.EVENTS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_RESULTS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_TQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INVITE_USER_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_GROUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_PQS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_TICKETS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_TQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_TQS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESULTS
import com.codingub.belarusianhistory.data.remote.network.EndPoints.ROLE_CHANGE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNIN
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.TQ
import com.codingub.belarusianhistory.data.remote.network.requests.AddResultRequest
import com.codingub.belarusianhistory.data.remote.network.requests.CreateGroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.GetAllResultsRequest
import com.codingub.belarusianhistory.data.remote.network.requests.GroupRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertPqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetPqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetPqsRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetResultRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTicketsRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.ResetTqsRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RoleRequest
import com.codingub.belarusianhistory.data.remote.network.responses.AchieveResponse
import com.codingub.belarusianhistory.data.remote.network.responses.EventResponse
import com.codingub.belarusianhistory.data.remote.network.responses.GroupResponse
import com.codingub.belarusianhistory.data.remote.network.responses.PqResponse
import com.codingub.belarusianhistory.data.remote.network.responses.ResultResponse
import com.codingub.belarusianhistory.data.remote.network.responses.TicketResponse
import com.codingub.belarusianhistory.data.remote.network.responses.TokenResponse
import com.codingub.belarusianhistory.data.remote.network.responses.TqResponse
import com.codingub.belarusianhistory.sdk.AchieveType
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
    ): TokenResponse

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
    ): GroupResponse

    /*
       Ticket
    */

    @GET(TICKET)
    suspend fun getAllTickets(): TicketResponse

    @POST(INSERT_TICKET)
    suspend fun insertTicket(
        @Body request: InsertTicketRequest
    )

    @POST(RESET_TICKETS)
    suspend fun resetTicketsByIds(
        @Body request: ResetTicketsRequest
    )

    @POST(RESET_TICKET)
    suspend fun resetTicket(
        @Body request: ResetTicketRequest
    )

    /*
      TicketQuestion
   */

    @Cacheable
    @GET(TQ)
    suspend fun getAllTq(): TqResponse

    @GET("$TQ/")
    suspend fun getTqByTicketId(
        @Query("id") ticketId: String
    ): TqResponse

    @POST(INSERT_TQ)
    suspend fun insertTq(
        @Body request: InsertTqRequest
    )

    @POST(RESET_TQ)
    suspend fun resetTq(
        @Body request: ResetTqRequest
    )

    @POST(RESET_TQS)
    suspend fun resetTqsByIds(
        @Body request: ResetTqsRequest
    )

    /*
      PracticeQuestion
   */

    @GET("$PQ/")
    suspend fun getPqByTqId(
        @Query("id") tqId: String
    ): PqResponse

    @POST(INSERT_PQ)
    suspend fun insertPq(
        @Body request: InsertPqRequest
    )

    @POST(RESET_PQ)
    suspend fun resetPq(
        @Body request: ResetPqRequest
    )

    @POST(RESET_PQS)
    suspend fun resetPqsByIds(
        @Body request: ResetPqsRequest
    )

    /*
      Achieves
   */

    @Cacheable
    @GET(ACHIEVE)
    suspend fun getAllAchieves(): AchieveResponse

    @GET("$ACHIEVE/")
    suspend fun getTypeAchieves(
        @Query("type") type: AchieveType
    ): AchieveResponse

    /*
        Results
     */

    @GET("$RESULTS/")
    suspend fun getTypeResults(
        @Query("type") type: AchieveType,
        @Query("login") login: String
    ): ResultResponse

    @POST(RESULTS)
    suspend fun getAllResults(
        @Body request: GetAllResultsRequest
    ): ResultResponse

    @POST(INSERT_RESULTS)
    suspend fun setResult(
        @Body request: AddResultRequest
    )

    @POST(DELETE_RESULTS)
    suspend fun deleteResult(
        @Body request: ResetResultRequest
    )

    /*
        Events
     */

    @Cacheable
    @GET(EVENTS)
    suspend fun getAllEvents(): EventResponse


}