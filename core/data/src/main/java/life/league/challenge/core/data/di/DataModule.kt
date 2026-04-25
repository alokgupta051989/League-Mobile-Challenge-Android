package life.league.challenge.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import life.league.challenge.core.data.Repository
import life.league.challenge.core.data.TokenManager
import life.league.challenge.core.domain.repository.PostRepository
import life.league.challenge.core.network.TokenProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindPostRepository(
        repository: Repository
    ): PostRepository

    @Binds
    @Singleton
    abstract fun bindTokenProvider(
        tokenManager: TokenManager
    ): TokenProvider
}
