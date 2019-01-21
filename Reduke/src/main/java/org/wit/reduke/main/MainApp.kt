package org.wit.reduke.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.models.PostJSONStore
import org.wit.reduke.models.PostStore
import org.wit.reduke.models.UserJSONStore
import org.wit.reduke.models.UserStore

class MainApp : Application(), AnkoLogger {

    lateinit var redukes: PostStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        redukes = PostJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
    }
}