package life.league.challenge.core.data

import android.util.Base64
import android.util.Log
import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.model.Post
import life.league.challenge.core.network.api.Api
import life.league.challenge.core.network.model.PostDto
import life.league.challenge.core.network.model.UserDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val api: Api,
    private val tokenManager: TokenManager
) : PostRepository {

    override suspend fun getPosts(username: String, password: String): List<Post> {
        var token = tokenManager.getToken()
        
        if (token == null) {
            Log.d("Repository", "No token found, logging in...")
            val auth = Base64.encodeToString("$username:$password".toByteArray(), Base64.NO_WRAP)
            val account = api.login("Basic $auth")
            token = account.apiKey ?: throw Exception("Login failed: No API key returned")
            tokenManager.saveToken(token)
            Log.d("Repository", "Login success and token saved.")
        } else {
            Log.d("Repository", "Using cached token.")
        }
        
        Log.d("Repository", "Fetching users...")
        val users = api.getUsers()
        
        Log.d("Repository", "Fetching posts...")
        val posts = api.getPosts()

        Log.d("Repository", "Mapping to domain...")
        return mapToDomain(posts, users)
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
