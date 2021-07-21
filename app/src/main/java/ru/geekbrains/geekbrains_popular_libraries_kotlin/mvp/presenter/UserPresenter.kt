package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserView

        override fun getCount(): Int = repos.size

        fun clearLoadedRepositories() = repos.clear()
        fun addRepositories(repositories : Collection<ReposItem>) {
            repos.addAll(repositories)
        }
    }

    val reposPresenterList = RepoListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (githubUser == null) return
        viewState.init()
        viewState.loadUser(githubUser)
        loadRepositories()
    }

    fun backClick() : Boolean {
        router.exit()
        return true
    }

    private fun loadRepositories() {
        viewState.startLoading()
        githubUser?.reposUrl?.let {url->
            usersRepo.getRepositories(url)
                .observeOn(scheduler)
                .subscribe({ repos ->
                    reposPresenterList.clearLoadedRepositories()
                    reposPresenterList.addRepositories(repos)
                    viewState.updateList()
                    viewState.stopLoading()
                }, {
                    viewState.showError(it.message ?: "Unknown error")
                    viewState.stopLoading()
                })
        }
    }
}