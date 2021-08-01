package ru.geekbrains.geekbrains_popular_libraries_kotlin.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Versioned(val version: Int)