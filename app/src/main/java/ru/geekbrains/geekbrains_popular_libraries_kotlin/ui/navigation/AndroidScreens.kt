package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.navigation


import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.navigation.IScreens
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment.UserFragment
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment.UsersFragment

class AndroidScreens : IScreens {

    override fun users() = FragmentScreen { UsersFragment.getInstance() }
    override fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }

}