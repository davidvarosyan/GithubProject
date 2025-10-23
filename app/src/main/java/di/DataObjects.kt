package di

import android.content.Context
import com.varosyan.data.db.AppDatabase
import com.varosyan.data.db.dao.UserDao
import com.varosyan.data.db.dao.UserDetailDao
import com.varosyan.data.db.getDatabase
import com.varosyan.data.network.getClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataObjects {
    @Provides
    @Singleton
    fun provideClient(): HttpClient {
        return getClient()
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return getDatabase(context)
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideUserDetailDao(db: AppDatabase): UserDetailDao {
        return db.userDetailDao()
    }
}
