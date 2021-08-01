package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module

import dagger.Module
import dagger.Provides
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App

@Module
class AppModule(val app: App) {

    @Provides
    fun app(): App = app

}