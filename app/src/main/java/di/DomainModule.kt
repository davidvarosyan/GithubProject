package di

import com.varosyan.domain.usecase.GetUserDetailUseCase
import com.varosyan.domain.usecase.GetUsersUseCase
import com.varosyan.domain.usecaseimpl.GetUserDetailUseCaseImpl
import com.varosyan.domain.usecaseimpl.GetUsersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {
    @Binds
    abstract fun bindGetUserUseCase(impl: GetUsersUseCaseImpl): GetUsersUseCase
    @Binds
    abstract fun bindGetUserDetailUseCase(impl: GetUserDetailUseCaseImpl): GetUserDetailUseCase
}
