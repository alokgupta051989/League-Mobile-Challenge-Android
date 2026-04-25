package life.league.challenge.core.network.api

import life.league.challenge.core.model.Account
import life.league.challenge.core.network.model.PostDto
import life.league.challenge.core.network.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Retrofit API definitions for the League Challenge backend.
 */
interface Api {

    /**
     * Authenticates the user with Basic credentials.
     * @param credentials Base64 encoded "username:password" string with "Basic " prefix.
     * @return [Account] containing the session API key.
     */
    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Account

    /**
     * Retrieves a list of all users from the backend.
     */
    @GET("users")
    suspend fun getUsers(): List<UserDto>

    /**
     * Retrieves a list of all posts from the backend.
     * Note: This request is intercepted by [AuthInterceptor] to add the session token.
     */
    @GET("posts")
    suspend fun getPosts(): List<PostDto>

}
