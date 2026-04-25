package life.league.challenge.core.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import life.league.challenge.core.model.Post

/**
 * Interface defining the data operations for Posts.
 * This is part of the Domain layer and is agnostic of the underlying data source (Network, DB, etc.).
 */
interface PostRepository {
    
    /**
     * Authenticates the user and saves the session token.
     */
    suspend fun login(username: String, password: String)
    
    /**
     * Returns a reactive stream of all posts from the local cache.
     */
    fun getPosts(): Flow<List<Post>>
    
    /**
     * Returns a PagingData stream for efficient loading of large post datasets.
     */
    fun getPagedPosts(): Flow<PagingData<Post>>
    
    /**
     * Synchronizes local cache with the remote network source.
     * Fetches users and posts, maps them, and updates the database.
     */
    suspend fun syncPosts()
    
    /**
     * Clears user session and local data.
     */
    suspend fun logout()
    
    /**
     * Checks if a valid session exists.
     */
    suspend fun isLoggedIn(): Boolean
}
