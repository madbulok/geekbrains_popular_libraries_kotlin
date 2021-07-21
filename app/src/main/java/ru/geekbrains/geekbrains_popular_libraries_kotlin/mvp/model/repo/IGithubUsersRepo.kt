package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.ReposItem

interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
    fun getRepositories(url: String) : Single<List<ReposItem>>
}