package org.wit.reduke.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.models.posts.PostJSONStore
import org.wit.reduke.models.posts.PostStore
import org.wit.reduke.models.users.UserJSONStore
import org.wit.reduke.models.users.UserStore

class MainApp : Application(), AnkoLogger {

    lateinit var posts: PostStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        posts = PostJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
    }
}