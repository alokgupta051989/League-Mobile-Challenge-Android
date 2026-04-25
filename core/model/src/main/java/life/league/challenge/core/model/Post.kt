package life.league.challenge.core.model

/**
 * Domain model representing a social media post.
 * This model is used by the Domain and Presentation layers to display post data.
 * @property id Unique identifier of the post.
 * @property username The username of the post's author.
 * @property avatarUrl URL to the author's profile image.
 * @property title The headline or subject of the post.
 * @property description The main content body of the post.
 */
data class Post(
    val id: Int,
    val username: String,
    val avatarUrl: String,
    val title: String,
    val description: String
)
