package ru.geekbrains.geekbrains_popular_libraries_kotlin.di.module

import android.widget.ImageView
import dagger.Module
import dagger.Provides
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.IImageCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.cache.room.RoomImageCache
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.IImageLoader
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.App
import ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.image.GlideImageLoader
import java.io.File
import javax.inject.Singleton

@Module
class ImageModule {

    @Provides
    fun defaultPath() : File = App.instance.cacheDir

    @Provides
    fun imageCache(dir: File = defaultPath()) : IImageCache = RoomImageCache(dir)

    @Singleton
    @Provides
    fun imageLoader(): IImageLoader<ImageView> =
        GlideImageLoader()
}