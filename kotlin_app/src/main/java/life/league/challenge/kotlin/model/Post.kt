package life.league.challenge.kotlin.model

import com.google.gson.annotations.SerializedName

/**
 * API Data Transfer Objects
 */
data class UserDto(
    val id: Int,
    val name: String,
    val username: String,
    val avatar: String
)

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

/**
 * Domain model used in the UI
 */
data class Post(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val title: String,
    val description: String
)

/**
 * Mapper to convert API models to Domain model
 */
fun mapToDomain(posts: List<PostDto>, users: List<UserDto>): List<Post> {
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
