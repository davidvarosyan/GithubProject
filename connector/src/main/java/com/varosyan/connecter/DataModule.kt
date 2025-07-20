package com.varosyan.connecter

import com.varosyan.data.network.getRetrofitInstance
import com.varosyan.data.repo.GetUserDetailRepoImpl
import com.varosyan.data.repo.GetUsersRepoImpl
import com.varosyan.data.service.UserApiService
import com.varosyan.data.service.UserDetailApiService
import com.varosyan.data.service.UserSearchApiService
import com.varosyan.domain.repo.GetUserDetailRepo
import com.varosyan.domain.repo.GetUsersRepo
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
    factory<GetUsersRepo> { GetUsersRepoImpl(get()) }
    factory<GetUserDetailRepo> { GetUserDetailRepoImpl(get()) }
}