package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IGithubRepositoriesCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IGithubUsersCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubRepositoriesRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.IGithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitGithubRepositoriesRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.RetrofitGithubUsersRepo
import javax.inject.Singleton

@Module
class RepoModule {

    @Singleton
    @Provides
    fun usersRepo(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGithubUsersCache
    ): IGithubUsersRepo =
        RetrofitGithubUsersRepo(api, networkStatus, cache)

    @Singleton
    @Provides
    fun usersRepositories(
        api: IDataSource,
        networkStatus: INetworkStatus,
        cache: IGithubRepositoriesCache
    ): IGithubRepositoriesRepo =
        RetrofitGithubRepositoriesRepo(api, networkStatus, cache)

}