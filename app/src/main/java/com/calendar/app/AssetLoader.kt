package com.calendar.app

import android.content.Context

class AssetLoader(private val context: Context) {

    fun getJsonString(fileName: String):String? {
        return kotlin.runCatching {
            loadAsset(fileName)
        }.getOrNull()
    }

    private fun loadAsset(fileName: String): String {
        return context.assets.open(fileName).use {
            val size = it.available()
            val bytes = ByteArray(size)
            it.read(bytes)
            String(bytes)
        }
    }
}