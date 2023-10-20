package com.codingub.belarusianhistory.data.remote

import com.codingub.belarusianhistory.data.remote.network.EndPoints.AUTHENTICATE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.ROLE_CHANGE
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNIN
import com.codingub.belarusianhistory.data.remote.network.EndPoints.SIGNUP
import com.codingub.belarusianhistory.data.remote.network.ServerResponse
import com.codingub.belarusianhistory.data.remote.network.requests.LoginRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RegisterRequest
import com.codingub.belarusianhistory.data.remote.network.requests.RoleRequest
import com.codingub.belarusianhistory.data.remote.network.responses.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

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


    /*
      TicketQuestion
   */

    /*
      PracticeQuestion
   */

    /*
      Achieves
   */

}