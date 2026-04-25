package life.league.challenge.core.domain.repository

import life.league.challenge.core.model.Post

interface PostRepository {
    suspend fun login(username: String, password: String)
    suspend fun getPosts(): List<Post>
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
}
