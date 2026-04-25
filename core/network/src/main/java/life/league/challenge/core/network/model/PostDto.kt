package life.league.challenge.core.network.model

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
