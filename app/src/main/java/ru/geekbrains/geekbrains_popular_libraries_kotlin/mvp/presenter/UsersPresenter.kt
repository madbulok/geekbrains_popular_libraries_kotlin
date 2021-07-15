package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter

import android.widget.Toast
import com.github.terrakok.cicerone.Router
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.repo.GithubUsersRepo
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.navigation.IScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.list.IUserListPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.UsersView
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view.list.UserItemView

class UsersPresenter(
    private val usersRepo: GithubUsersRepo,
    private val router: Router,
    val screens: IScreens,
    private val scheduler: @NonNull Scheduler
) :
    MvpPresenter<UsersView>() {

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount() = users.size
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { userSelected ->
            val user = usersListPresenter.users[userSelected.pos]
            router.navigateTo(screen = screens.user(user))
        }
    }

    private fun loadData() {
        usersListPresenter.users.clear()
        usersRepo.getUsers()
            .observeOn(scheduler)
            .subscribeOn(Schedulers.newThread())
            .subscribe({users->
                usersListPresenter.users.addAll(users)
                viewState.updateList()
            }, {
                viewState.showErrorMessage(it.message!!)
            })
    }

    fun backClick(): Boolean {
        router.exit()
        return true
    }
}