package ru.geekbrains.geekbrains_popular_libraries_kotlin.di

import dagger.Component
import ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module.*
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.MainPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.presenter.UsersPresenter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.activity.MainActivity
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment.RepositoryFragment
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment.UserFragment
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.fragment.UsersFragment
import javax.inject.Singleton


@Singleton
@Component (
    modules = [
        AppModule::class,
        CiceroneModule::class,
        CacheModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)


    //Надо убрать
    fun inject(usersFragment: UsersFragment)
    fun inject(userFragment: UserFragment)
    fun inject(repositoryFragment: RepositoryFragment)
}