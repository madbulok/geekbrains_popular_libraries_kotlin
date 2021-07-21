package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.api.IDataSource

class GithubUsersRepo(val api: IDataSource) : IGithubUsersRepo {
    override fun getUsers() = api.getUsers().subscribeOn(Schedulers.io())
    override fun getRepositories(url: String) = api.userRepositories(url).subscribeOn(Schedulers.io())
}