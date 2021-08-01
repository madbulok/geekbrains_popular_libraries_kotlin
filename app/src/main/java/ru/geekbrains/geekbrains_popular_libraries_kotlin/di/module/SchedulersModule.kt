package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module

import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class SchedulersModule {

    @Singleton
    @Named("ui")
    @Provides
    fun uiThreadScheduler() : Scheduler = AndroidSchedulers.mainThread()

    @Singleton
    @Named("io")
    @Provides
    fun ioThreadSchedulers() : Scheduler = Schedulers.io()
}