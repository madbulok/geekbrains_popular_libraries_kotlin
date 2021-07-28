package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubRepository
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.ReposItem
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.GithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.navigation.IScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UserView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.IReposListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.RepoItemView

class UserPresenter(
    private val usersRepo: GithubUsersRepo,
    private val router: Router,
    val githubUser: GithubUser?,
    private val scheduler: Scheduler,
    val screens: IScreens
) : MvpPresenter<UserView>() {
    class RepoListPresenter : IReposListPresenter {
        private val repos = mutableListOf<GithubRepository>()
        override var itemClickListener: ((RepoItemView) -> Unit)? = null

        override fun bindView(view: RepoItemView) {
            val repo = repos[view.pos]
            repo.name.let { view.setName(it) }
        }

        override fun getCount(): Int = repos.size

        fun clearLoadedRepositories() = repos.clear()
        fun addRepositories(repositories: List<GithubRepository>) {
            repos.addAll(repositories)
        }

        fun getRepo(pos: Int) = repos[pos]
    }

    val reposPresenterList = RepoListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        if (githubUser == null) return
        reposPresenterList.itemClickListener = {
            val reposItem = reposPresenterList.getRepo(it.pos)
            router.navigateTo(screen = screens.openRepositoryInfo(reposItem.name))
        }
        viewState.init()
        viewState.loadUser(githubUser)
        loadRepositories()
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }

    private fun loadRepositories() {
        viewState.startLoading()
        usersRepo.getRepositories(githubUser!!)
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