package life.league.challenge.core.database

import life.league.challenge.core.model.Post
import org.junit.Assert.assertEquals
import org.junit.Test

class DatabaseMappingTest {

    @Test
    fun `PostEntity toDomain mapping`() {
        val entity = PostEntity(1, "user", "url", "title", "desc")
        val domain = entity.toDomain()

        assertEquals(entity.id, domain.id)
        assertEquals(entity.username, domain.username)
        assertEquals(entity.avatarUrl, domain.avatarUrl)
        assertEquals(entity.title, domain.title)
        assertEquals(entity.description, domain.description)
    }

    @Test
    fun `Post toEntity mapping`() {
        val domain = Post(1, "user", "url", "title", "desc")
        val entity = domain.toEntity()

        assertEquals(domain.id, entity.id)
        assertEquals(domain.username, entity.username)
        assertEquals(domain.avatarUrl, entity.avatarUrl)
        assertEquals(domain.title, entity.title)
        assertEquals(domain.description, entity.description)
    }
}
