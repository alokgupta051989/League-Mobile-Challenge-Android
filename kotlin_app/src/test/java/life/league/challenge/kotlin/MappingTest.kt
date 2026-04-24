package life.league.challenge.kotlin

import life.league.challenge.kotlin.model.PostDto
import life.league.challenge.kotlin.model.UserDto
import life.league.challenge.kotlin.model.mapToDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class MappingTest {

    @Test
    fun `mapToDomain correctly joins posts and users`() {
        val users = listOf(
            UserDto(1, "Name 1", "user1", "avatar1"),
            UserDto(2, "Name 2", "user2", "avatar2")
        )
        val posts = listOf(
            PostDto(1, 101, "title 1", "body 1"),
            PostDto(2, 102, "title 2", "body 2"),
            PostDto(3, 103, "title 3", "body 3") // User 3 doesn't exist
        )

        val result = mapToDomain(posts, users)

        assertEquals(2, result.size)
        
        assertEquals(101, result[0].id)
        assertEquals("user1", result[0].username)
        assertEquals("avatar1", result[0].avatarUrl)
        
        assertEquals(102, result[1].id)
        assertEquals("user2", result[1].username)
    }

    @Test
    fun `mapToDomain returns empty list when no users match`() {
        val users = listOf(UserDto(1, "Name 1", "user1", "avatar1"))
        val posts = listOf(PostDto(2, 102, "title 2", "body 2"))

        val result = mapToDomain(posts, users)

        assertTrue(result.isEmpty())
    }

    private fun assertTrue(condition: Boolean) {
        org.junit.Assert.assertTrue(condition)
    }
}
