package org.wit.reduke.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.models.posts.PostStore
import org.wit.reduke.models.posts.PostsFirebaseStore
import org.wit.reduke.models.users.UserStore

class MainApp : Application(), AnkoLogger {

    // Create the list variables for both the posts and users.
    lateinit var posts: PostStore
    lateinit var users: UserStore

    override fun onCreate() {
        // When the app is run.
        super.onCreate()

        // Create an instance of the JSON store for both posts and users.
        posts = PostsFirebaseStore(applicationContext)
//        users = UserJSONStore(applicationContext)
    }
}