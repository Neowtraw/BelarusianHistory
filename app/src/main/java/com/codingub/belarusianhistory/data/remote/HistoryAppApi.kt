package com.codingub.belarusianhistory.data.remote

import com.codingub.belarusianhistory.data.remote.network.EndPoints.ACHIEVE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.AUTHENTICATE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.INSERT_TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_PQ
import com.codingub.belarusianhistory.data.remote.network.EndPoints.RESET_TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.ROLE_CHANGE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNIN
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNUP
import com.codingub.belarusianhistory.data.remote.network.EndPoints.TICKET
import com.codingub.belarusianhistory.data.remote.network.EndPoints.TQ
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.models.achieves.Achieve
import com.codingub.belarusianhistory.data.remote.network.models.practices.PracticeQuestion
import com.codingub.belarusianhistory.data.remote.network.models.tickets.Ticket
import com.codingub.belarusianhistory.data.remote.network.models.tickets.TicketQuestion
import com.codingub.belarusianhistory.data.remote.network.requests.DeletePqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.DeleteTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertPqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTicketRequest
import com.codingub.belarusianhistory.data.remote.network.requests.InsertTqRequest
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RoleRequest
import com.codingub.belarusianhistory.data.remote.network.responses.TokenResponse
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
    ) : ServerResponse<Any>

    /*
       Ticket
    */

    @GET(TICKET)
    suspend fun getAllTickets() : ServerResponse<List<Ticket>>

    @POST(INSERT_TICKET)
    suspend fun insertTicket(
        @Body request: InsertTicketRequest
    ) : ServerResponse<Any>

    @POST(RESET_TICKET)
    suspend fun resetTicket(
        @Body request: DeleteTicketRequest
    ) : ServerResponse<Any>

    /*
      TicketQuestion
   */

    @GET(TQ)
    suspend fun getAllTq(
        @Body ticketId: String
    ) : ServerResponse<List<TicketQuestion>>

    @POST(INSERT_TICKET)
    suspend fun insertTq(
        @Body request: InsertTqRequest
    ) : ServerResponse<Any>

    @POST(RESET_TICKET)
    suspend fun deleteTq(
        @Body request: DeleteTqRequest
    ) : ServerResponse<Any>

    /*
      PracticeQuestion
   */

    @GET(PQ)
    suspend fun getAllPq(
        @Body tqId: String
    ) : ServerResponse<List<PracticeQuestion>>

    @POST(INSERT_PQ)
    suspend fun insertPq(
        @Body request: InsertPqRequest
    ) : ServerResponse<Any>

    @POST(RESET_PQ)
    suspend fun deletePq(
        @Body request: DeletePqRequest
    ) : ServerResponse<Any>

    /*
      Achieves
   */

    @GET(ACHIEVE)
    suspend fun getAllAchieves() : ServerResponse<List<Achieve>>

    @GET(ACHIEVE)
    suspend fun getTypeAchieves(
        @Query("type") type: Int
    ): ServerResponse<List<Achieve>>


}