package com.grumpyshoe.beertasticcmp.data.source.preferences

import android.content.Context
import org.koin.java.KoinJavaComponent.inject

actual fun createDataStore(): PrefsDataStore {

    val context by inject<android.content.Context>(Context::class.java)
    return createDataStoreByPath(
        producePath = {
            context.filesDir.resolve(dataStoreFileName).absolutePath
        },
    )
}