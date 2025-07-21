package com.varosyan.connecter

import com.varosyan.data.db.AppDatabase
import com.varosyan.data.db.dao.UserDao
import com.varosyan.data.db.dao.UserDetailDao
import com.varosyan.data.db.getDatabase
import com.varosyan.data.mapper.UserDetailMapper
import com.varosyan.data.network.getRetrofitInstance
import com.varosyan.data.repo.GetUserDetailRepoImpl
import com.varosyan.data.repo.GetUsersRepoImpl
import com.varosyan.data.service.UserApiService
import com.varosyan.data.service.UserDetailApiService
import com.varosyan.data.service.UserSearchApiService
import com.varosyan.domain.repo.GetUserDetailRepo
import com.varosyan.domain.repo.GetUsersRepo
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

internal fun dataModule() = module {
    single<Retrofit> {
        getRetrofitInstance()
    }
    single<UserApiService> {
        get<Retrofit>().create(UserApiService::class.java)
    }
    single<UserSearchApiService> {
        get<Retrofit>().create(UserSearchApiService::class.java)
    }
    single<UserDetailApiService> {
        get<Retrofit>().create(UserDetailApiService::class.java)
    }
    single { UserDetailMapper() }
    single<AppDatabase> { getDatabase(androidContext()) }
    single<UserDao> { get<AppDatabase>().userDao() }
    single<UserDetailDao> { get<AppDatabase>().userDetailDao() }
    factory<GetUsersRepo> { GetUsersRepoImpl(get(), get()) }
    factory<GetUserDetailRepo> { GetUserDetailRepoImpl(get(), get(), get()) }
}