package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser

@AddToEndSingle
interface UserView : MvpView {
    fun loadUser(user: GithubUser)
    fun init()
    fun startLoading()
    fun stopLoading()
    fun updateList()
    fun showError(message: String)
}