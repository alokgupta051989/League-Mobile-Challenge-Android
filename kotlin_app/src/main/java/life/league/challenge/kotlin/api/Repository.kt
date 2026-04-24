package life.league.challenge.kotlin.api

import android.util.Log
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.mapToDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Repository @Inject constructor(private val api: Api) {

    open suspend fun getPosts(username: String, password: String): List<Post> {
        Log.d("Repository", "Starting login...")
        val account = api.login(username, password)
        val token = account.apiKey ?: throw Exception("Login failed: No API key returned")
        
        Log.d("Repository", "Login success, fetching users...")
        val users = api.getUsers(token)
        
        Log.d("Repository", "Fetching posts...")
        val posts = api.getPosts(token)

        Log.d("Repository", "Mapping to domain...")
        return mapToDomain(posts, users)
    }
}
