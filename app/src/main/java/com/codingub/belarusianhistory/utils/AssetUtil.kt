package com.codingub.belarusianhistory.utils

import android.net.Uri

object AssetUtil {
    private fun assetPath(filePath: String, fileExt: String): String {
        return "file:///android_asset/$filePath.$fileExt"
    }

    fun imagesImageUri(name: String): Uri {
        val path = "images/$name"
        val assetPath = assetPath(path, FileExtension.PNG)

        return Uri.parse(assetPath)
    }

    private object FileExtension{
        const val PNG: String = "png"
    }
}