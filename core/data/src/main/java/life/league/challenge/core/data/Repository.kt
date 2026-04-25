package life.league.challenge.core.data

import android.util.Base64
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import life.league.challenge.core.database.PostDao
import life.league.challenge.core.database.toDomain
import life.league.challenge.core.database.toEntity
import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.model.Post
import life.league.challenge.core.network.api.Api
import life.league.challenge.core.network.model.PostDto
import life.league.challenge.core.network.model.UserDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api: Api,
    private val postDao: PostDao,
    private val tokenManager: TokenManager
) : PostRepository {

    override suspend fun login(username: String, password: String) {
        try {
            Log.d("Repository", "Logging in...")
            val auth = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
            val account = api.login("Basic $auth")
            val token = account.apiKey ?: throw Exception("Login failed: No API key returned")
            tokenManager.saveToken(token)
            Log.d("Repository", "Login success and token saved.")
        } catch (e: HttpException) {
            val message = when (e.code()) {
                401 -> "Invalid username or password"
                else -> "Server error: ${e.code()}"
            }
            throw Exception(message)
        } catch (e: IOException) {
            throw Exception("Network error. Please check your connection.")
        }
    }

    override fun getPosts(): Flow<List<Post>> {
        return postDao.getAllPosts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getPagedPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { postDao.getPagedPosts() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override suspend fun syncPosts() {
        try {
            if (!isLoggedIn()) {
                login("hello", "world")
            }

            Log.d("Repository", "Syncing posts: fetching users...")
            val users = api.getUsers()
            
            Log.d("Repository", "Syncing posts: fetching posts...")
            val posts = api.getPosts()

            Log.d("Repository", "Mapping and caching...")
            val domainPosts = mapToDomain(posts, users)
            
            postDao.deleteAllPosts()
            postDao.insertPosts(domainPosts.map { it.toEntity() })
            
        } catch (e: HttpException) {
            if (e.code() == 401) {
                tokenManager.clearToken()
                throw Exception("Session expired. Please log in again.")
            }
            throw Exception("Failed to sync posts: ${e.code()}")
        } catch (e: IOException) {
            throw Exception("Network error. Please check your connection.")
        }
    }

    override suspend fun logout() {
        tokenManager.clearToken()
        postDao.deleteAllPosts()
    }

    override suspend fun isLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }

    private fun mapToDomain(posts: List<PostDto>, users: List<UserDto>): List<Post> {
        val userMap = users.associateBy { it.id }
        return posts.mapNotNull { postDto ->
            val user = userMap[postDto.userId]
            user?.let {
                Post(
                    id = postDto.id,
                    username = it.username,
                    avatarUrl = it.avatar,
                    title = postDto.title,
                    description = postDto.body
                )
            }
        }
    }
}
