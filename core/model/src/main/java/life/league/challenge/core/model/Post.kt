package life.league.challenge.core.model

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
