package com.aniketkadam.appod

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private inline fun <reified T : Any> Context.readAssetsFile(fileName: String): T =
    assets.open(fileName).bufferedReader().use { it.readText() }.let {
        Gson().fromJson(it, object : TypeToken<T>() {}.type)
    }
