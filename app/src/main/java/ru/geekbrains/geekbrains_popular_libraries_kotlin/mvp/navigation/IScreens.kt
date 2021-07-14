package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.navigation

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.GithubUser
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.activity.UserFragment

interface IScreens {
    fun users(): Screen
    fun user(user: GithubUser) = FragmentScreen { UserFragment.newInstance(user) }
}