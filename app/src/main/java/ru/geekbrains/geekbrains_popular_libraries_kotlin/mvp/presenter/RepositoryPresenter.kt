package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.RepositoryView

class RepositoryPresenter(private val router: Router, private val text: String?) :
    MvpPresenter<RepositoryView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        text?.let {
            viewState.showRepoInfo(it)
        }
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}