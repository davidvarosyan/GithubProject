package di

import android.content.Context
import com.varosyan.data.db.AppDatabase
import com.varosyan.data.db.dao.UserDao
import com.varosyan.data.db.dao.UserDetailDao
import com.varosyan.data.db.getDatabase
import com.varosyan.data.network.getClient
import com.varosyan.data.repo.GetUserDetailRepoImpl
import com.varosyan.data.repo.GetUsersRepoImpl
import com.varosyan.data.service.UserApiService
import com.varosyan.data.service.UserApiServiceImpl
import com.varosyan.data.service.UserDetailApiService
import com.varosyan.data.service.UserDetailApiServiceImpl
import com.varosyan.domain.repo.GetUserDetailRepo
import com.varosyan.domain.repo.GetUsersRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun getUserApiService(impl: UserApiServiceImpl): UserApiService

    @Singleton
    @Binds
    abstract fun getUserDetailApiService(impl: UserDetailApiServiceImpl): UserDetailApiService

    @Binds
    abstract fun getGetUserRepo(impl: GetUsersRepoImpl): GetUsersRepo

    @Binds
    abstract fun getUserDetailRepo(impl: GetUserDetailRepoImpl): GetUserDetailRepo

}


