package life.league.challenge.core.domain.repository

import life.league.challenge.core.model.Post

interface PostRepository {
    suspend fun getPosts(username: String, password: String): List<Post>
}
