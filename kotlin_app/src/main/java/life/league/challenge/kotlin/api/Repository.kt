package life.league.challenge.kotlin.api

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import life.league.challenge.kotlin.model.Post
import life.league.challenge.kotlin.model.mapToDomain

open class Repository(private val api: Api) {

    open suspend fun getPosts(username: String, password: String): List<Post> = coroutineScope {
        // 1. Login to get API key
        val account = api.login(username, password)
        val token = account.apiKey ?: throw Exception("Login failed: No API key returned")

        // 2. Fetch users and posts in parallel
        val usersDeferred = async { api.getUsers(token) }
        val postsDeferred = async { api.getPosts(token) }

        val users = usersDeferred.await()
        val posts = postsDeferred.await()

        // 3. Map to domain model
        mapToDomain(posts, users)
    }
}
