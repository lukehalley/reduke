package org.wit.reduke.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.reduke.models.PostJSONStore
import org.wit.reduke.models.PostStore
import org.wit.reduke.models.UserJSONStore
import org.wit.reduke.models.UserStore

class MainApp : Application(), AnkoLogger {

    lateinit var users: UserStore
    lateinit var posts: PostStore

    override fun onCreate() {
        super.onCreate()
        users = UserJSONStore(applicationContext)
        posts = PostJSONStore(applicationContext)
        info("Reduke Started!")
    }
}