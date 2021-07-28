package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.interfaces

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable

interface IConverter {
    fun convert(bitmap: Bitmap, nameFile: String): Completable
}