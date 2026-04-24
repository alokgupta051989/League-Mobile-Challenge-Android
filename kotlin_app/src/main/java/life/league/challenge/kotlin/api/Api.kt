package life.league.challenge.kotlin.api

import life.league.challenge.kotlin.model.Account
import life.league.challenge.kotlin.model.PostDto
import life.league.challenge.kotlin.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Retrofit API interface definition using coroutines. Feel free to change this implementation to
 * suit your chosen architecture pattern and concurrency tools
 */
interface Api {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Account

    @GET("users")
    suspend fun getUsers(@Header("x-access-token") token: String): List<UserDto>

    @GET("posts")
    suspend fun getPosts(@Header("x-access-token") token: String): List<PostDto>

}

/**
 * Overloaded Login API extension function to handle authorization header encoding
 */
suspend fun Api.login(username: String, password: String)
        = login("Basic " + android.util.Base64.encodeToString("$username:$password".toByteArray(), android.util.Base64.NO_WRAP))
