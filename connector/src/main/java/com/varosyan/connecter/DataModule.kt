package com.varosyan.connecter

import com.varosyan.data.db.AppDatabase
import com.varosyan.data.db.dao.UserDao
import com.varosyan.data.db.dao.UserDetailDao
import com.varosyan.data.db.getDatabase
import com.varosyan.data.mapper.UserDetailMapper
import com.varosyan.data.network.getClient
import com.varosyan.data.repo.GetUserDetailRepoImpl
import com.varosyan.data.repo.GetUsersRepoImpl
import com.varosyan.data.service.UserApiService
import com.varosyan.data.service.UserApiServiceImpl
import com.varosyan.data.service.UserDetailApiService
import com.varosyan.data.service.UserDetailApiServiceImpl
import com.varosyan.domain.repo.GetUserDetailRepo
import com.varosyan.domain.repo.GetUsersRepo
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal fun dataModule() = module {
    single<HttpClient> {
        getClient()
    }
    single<UserApiService> { UserApiServiceImpl(get()) }
    single<UserDetailApiService> {
        UserDetailApiServiceImpl(get())
    }
    single { UserDetailMapper() }
    single<AppDatabase> { getDatabase(androidContext()) }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<UserDetailDao> { get<AppDatabase>().userDetailDao() }
    factory<GetUsersRepo> { GetUsersRepoImpl(get(), get()) }
    factory<GetUserDetailRepo> { GetUserDetailRepoImpl(get(), get(), get()) }
}