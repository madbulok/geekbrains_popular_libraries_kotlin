package ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image

import android.graphics.Bitmap
import android.os.Environment
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.interfaces.IConverter
import java.io.File
import java.io.FileOutputStream

class AndroidConverter(private val schedulers: Scheduler) : IConverter {

    override fun convert(bitmap: Bitmap, nameFile: String): Completable {
        return Completable
            .fromAction {

                val directory = "${Environment.getExternalStorageDirectory()}/cached_pictures/"
                val dir = File(directory)
                dir.mkdirs()

                val outputFile = File(dir, "${nameFile}.png")

                if (!outputFile.exists()) outputFile.createNewFile()
                else outputFile.delete()

                try {
                    val out = FileOutputStream(outputFile)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                    out.flush()
                    out.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .observeOn(schedulers)
            .subscribeOn(Schedulers.io())
    }
}