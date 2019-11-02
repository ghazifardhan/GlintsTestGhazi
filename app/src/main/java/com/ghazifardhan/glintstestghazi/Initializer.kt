package com.ghazifardhan.glintstestghazi

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class Initializer: Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        ctx = applicationContext

        Realm.init(this)
        val configuration: RealmConfiguration = RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(configuration)
    }
}