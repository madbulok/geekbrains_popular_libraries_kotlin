package ru.geekbrains.geekbrains_popular_libraries_kotlin.ui.image

import android.graphics.Bitmap
import android.os.Environment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.entity.room.dao.CacheFileDao
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.AndroidConverter
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.CachedImage
import ru.geekbrains.geekbrains_popular_libraries_kotlin.mvp.model.image.interfaces.IImageLoader
import java.io.File
import java.util.*

class GlideImageLoader(val imageDao: CacheFileDao, val converter: AndroidConverter, val scheduler: Scheduler) :
    IImageLoader<ImageView> {

    override fun loadInto(url: String, container: ImageView) {
        Glide.with(container.context)
            .asBitmap()
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                    imageDao.getImageByUrl(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(scheduler)
                        .subscribe({ file ->
                            Glide.with(container.context)
                                .load(File(file.imagePath))
                                .into(container)
                        }, {
                            it.printStackTrace()
                        })
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    target: Target<Bitmap>,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    val nameFile =
                        Date().time.toString()
                    converter.convert(resource, nameFile)
                        .subscribe {
                            Completable.fromAction {
                                imageDao.insert(CachedImage(url, "${Environment.getExternalStorageDirectory()}/cached_pictures/${nameFile}.png"))
                            }.subscribeOn(Schedulers.io()).subscribe()
                        }
                    return false
                }
            })
            .into(container)
    }
}