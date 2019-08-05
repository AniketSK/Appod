package com.aniketkadam.appod.data

fun getTextInFile(fileName: String): String? =
    ClassLoader.getSystemResourceAsStream(fileName)?.bufferedReader().use { it?.readText() }
