package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserView

class UserPresenter(private val router: Router, val githubUser: GithubUser?) : MvpPresenter<UserView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (githubUser == null) return
        viewState.loadUser(githubUser)
    }

    fun backClick() : Boolean {
        router.exit()
        return true
    }
}