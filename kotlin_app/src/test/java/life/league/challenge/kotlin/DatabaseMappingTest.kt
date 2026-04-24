package life.league.challenge.kotlin

import life.league.challenge.kotlin.db.PostEntity
import life.league.challenge.kotlin.db.toDomain
import life.league.challenge.kotlin.db.toEntity
import life.league.challenge.kotlin.model.Post
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
