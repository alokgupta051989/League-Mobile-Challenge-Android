package life.league.challenge.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import life.league.challenge.core.database.AppDatabase
import life.league.challenge.core.database.PostDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "league_db"
        )
        .fallbackToDestructiveMigration() // Handle schema changes by clearing the database
        .build()
    }

    @Provides
    fun providePostDao(database: AppDatabase): PostDao {
        return database.postDao()
    }
}
