package life.league.challenge.kotlin.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import life.league.challenge.kotlin.model.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String,
    val title: String,
    val description: String
)

fun PostEntity.toDomain() = Post(
    id = id,
    username = username,
    avatarUrl = avatarUrl,
    title = title,
    description = description
)

fun Post.toEntity() = PostEntity(
    id = id,
    username = username,
    avatarUrl = avatarUrl,
    title = title,
    description = description
)
