package com.codingub.belarusianhistory.utils

import android.net.Uri

object AssetUtil {
    private fun assetPath(filePath: String, fileExt: String): String {
        return "file:///android_asset/$filePath.$fileExt"
    }

    fun menuImageUri(name: String): Uri {
        val path = "menu/$name"
        val assetPath = assetPath(path, FileExtension.PNG)

        return Uri.parse(assetPath)
    }

    private object FileExtension{
        const val PNG: String = "png"
    }
}