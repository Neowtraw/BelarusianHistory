package com.codingub.belarusianhistory.data.remote

import com.codingub.belarusianhistory.network.EndPoints.AUTHENTICATE
import com.codingub.belarusianhistory.network.EndPoints.SIGNIN
import com.codingub.belarusianhistory.network.EndPoints.SIGNUP
import com.codingub.belarusianhistory.network.requests.LoginRequest
import com.codingub.belarusianhistory.network.requests.RegisterRequest
import com.codingub.belarusianhistory.network.responses.TokenResponse
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