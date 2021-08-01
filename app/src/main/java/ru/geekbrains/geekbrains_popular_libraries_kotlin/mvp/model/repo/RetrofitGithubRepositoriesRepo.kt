package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IGithubRepositoriesCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.network.INetworkStatus
import java.lang.RuntimeException

class RetrofitGithubRepositoriesRepo(val api: IDataSource, val networkStatus: INetworkStatus, val cache: IGithubRepositoriesCache) :
    IGithubRepositoriesRepo {
    override fun getRepositories(user: GithubUser) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            user.reposUrl?.let { url ->
                api.getRepositories(url)
                    .flatMap { repositories ->
                        cache.putUserRepos(user, repositories).toSingleDefault(repositories)
                    }
            } ?: Single.error<List<GithubRepository>>(RuntimeException("User has no repos url")).subscribeOn(Schedulers.io())
        } else {
            cache.getUserRepos(user)
        }
    }.subscribeOn(Schedulers.io())
}
