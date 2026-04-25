package life.league.challenge.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import life.league.challenge.core.model.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val username: String,
    val avatarUrl: String,
    val title: String,
    val description: String,
    val sortOrder: Int // Added to preserve API order
)

fun PostEntity.toDomain() = Post(
    id = id,
    username = username,
    avatarUrl = avatarUrl,
    title = title,
    description = description
)

fun Post.toEntity(sortOrder: Int) = PostEntity(
    id = id,
    username = username,
    avatarUrl = avatarUrl,
    title = title,
    description = description,
    sortOrder = sortOrder
)
