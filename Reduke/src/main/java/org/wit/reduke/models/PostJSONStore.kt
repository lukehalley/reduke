package org.wit.reduke.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.helpers.exists
import org.wit.reduke.helpers.read
import org.wit.reduke.helpers.write
import java.util.*

val POST_JSON_FILE = "posts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<PostModel>>() {}.type

fun generateRandomPostId(): Long {
    return Random().nextLong()
}

class PostJSONStore : PostStore, AnkoLogger {

    val context: Context
    var posts = mutableListOf<PostModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, POST_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PostModel> {
        return posts
    }

    override fun create(post: PostModel) {
        post.id = generateRandomPostId()
        posts.add(post)
        serialize()
    }

    override fun update(post: PostModel) {
        var fetchedPost: PostModel? = posts.find { p -> p.id == post.id }
        if (fetchedPost != null) {
            fetchedPost.title = post.title
            fetchedPost.type = post.type
            fetchedPost.text = post.text
            fetchedPost.tag = post.tag
            serialize()
        }
    }

    override fun delete(post: PostModel) {
        posts.remove(post)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(posts, listType)
        write(context, POST_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, POST_JSON_FILE)
        posts = Gson().fromJson(jsonString, listType)
    }
}