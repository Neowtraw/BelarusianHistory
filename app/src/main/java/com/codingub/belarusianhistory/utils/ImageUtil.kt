package com.codingub.belarusianhistory.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.codingub.belarusianhistory.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ImageUtil {

    private val context: Context get() = App.getInstance()

    fun load(@DrawableRes drawableRes: Int, imageView: ImageView) {
        Glide.with(context).load(drawableRes).into(imageView)
    }

    fun load(drawable: Drawable, imageView: ImageView) {
        Glide.with(context).load(drawable).into(imageView)
    }

    fun load(uri: Uri, imageView: ImageView) {
        Glide.with(context).load(uri).into(imageView)
    }

    fun load(@DrawableRes drawableRes: Int, onLoaded: (Drawable) -> Unit) {
        Glide.with(context).load(drawableRes).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            onLoaded(it)
                        }
                    }
                    return false
                }
        }).submit()
    }

    fun load(drawable: Drawable, onLoaded: (Drawable) -> Unit) {
        Glide.with(context).load(drawable).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            onLoaded(it)
                        }
                    }
                    return false
                }
            }).submit()
    }

    fun load(uri: Uri, onLoaded: (Drawable) -> Unit) {
        Glide.with(context)
            .load(uri)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        CoroutineScope(Dispatchers.Main).launch {
                            onLoaded(it)
                        }
                    }
                    return false
                }
            })
            .submit()
    }

}