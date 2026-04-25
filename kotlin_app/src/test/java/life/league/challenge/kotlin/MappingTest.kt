package life.league.challenge.kotlin

import life.league.challenge.kotlin.model.PostDto
import life.league.challenge.kotlin.model.UserDto
import life.league.challenge.kotlin.model.mapToDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MappingTest {

    @Test
    fun `mapToDomain correctly joins posts and users`() {
        // Arrange
        val users = listOf(
            UserDto(id = 1, name = "John", username = "john_d", avatar = "url1"),
            UserDto(id = 2, name = "Jane", username = "jane_d", avatar = "url2")
        )
        val posts = listOf(
            PostDto(userId = 1, id = 101, title = "Title 1", body = "Body 1"),
            PostDto(userId = 2, id = 102, title = "Title 2", body = "Body 2")
        )

        // Act
        val result = mapToDomain(posts, users)

        // Assert
        assertEquals(2, result.size)
        assertEquals("john_d", result[0].username)
        assertEquals("url1", result[0].avatarUrl)
        assertEquals("jane_d", result[1].username)
        assertEquals("url2", result[1].avatarUrl)
    }

    @Test
    fun `mapToDomain ignores posts without matching users`() {
        // Arrange
        val users = listOf(
            UserDto(id = 1, name = "John", username = "john_d", avatar = "url1")
        )
        val posts = listOf(
            PostDto(userId = 1, id = 101, title = "Title 1", body = "Body 1"),
            PostDto(userId = 99, id = 102, title = "Title 2", body = "Body 2") // No user with id 99
        )

        // Act
        val result = mapToDomain(posts, users)

        // Assert
        assertEquals(1, result.size)
        assertEquals(101, result[0].id)
    }

    @Test
    fun `mapToDomain returns empty list when input is empty`() {
        // Act
        val result = mapToDomain(emptyList(), emptyList())

        // Assert
        assertTrue(result.isEmpty())
    }
}
