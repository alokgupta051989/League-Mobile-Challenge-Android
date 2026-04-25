package life.league.challenge.core.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import life.league.challenge.core.model.Post

interface PostRepository {
    suspend fun login(username: String, password: String)
    fun getPosts(): Flow<List<Post>>
    fun getPagedPosts(): Flow<PagingData<Post>>
    suspend fun syncPosts()
    suspend fun logout()
    suspend fun isLoggedIn(): Boolean
}
