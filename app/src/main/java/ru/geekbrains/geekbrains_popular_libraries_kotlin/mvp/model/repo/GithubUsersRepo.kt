package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo

import io.reactivex.rxjava3.core.Observable
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser

class GithubUsersRepo {
    private val users = Observable.create<List<GithubUser>> { emitter ->
        emitter.onNext(
            listOf(
                GithubUser("login1"),
                GithubUser("login2"),
                GithubUser("login3"),
                GithubUser("login4"),
                GithubUser("login5"),
                GithubUser("login6"),
                GithubUser("login7"),
            )
        )
    }

    fun getUsers(): Observable<List<GithubUser>> {
        return users
    }
}