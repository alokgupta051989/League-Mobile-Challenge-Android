package life.league.challenge.core.network.api

import life.league.challenge.core.model.Account
import life.league.challenge.core.network.model.PostDto
import life.league.challenge.core.network.model.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface Api {

    @GET("login")
    suspend fun login(@Header("Authorization") credentials: String?): Account

    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

}
