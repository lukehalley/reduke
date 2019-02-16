package org.wit.reduke.models.posts

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

fun generateRandomRedukeId(): Long {
    return Random().nextLong()
}

class PostJSONStore : PostStore, AnkoLogger {

    val context: Context
    var redukes = mutableListOf<PostModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, POST_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<PostModel> {
        return redukes
    }

    override fun create(post: PostModel) {
        post.id = generateRandomRedukeId()
        redukes.add(post)
        serialize()
    }

    override fun update(post: PostModel) {
        var foundPost: PostModel? = redukes.find { p -> p.id == post.id }
        if (foundPost != null) {
            foundPost.ownerId = post.ownerId
            foundPost.title = post.title
            foundPost.text = post.text
            foundPost.tags = post.tags
            foundPost.votes = post.votes
            foundPost.timestamp = post.timestamp
            serialize()
        }
    }

    override fun delete(placemark: PostModel) {
        redukes.remove(placemark)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(redukes, listType)
        write(context, POST_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, POST_JSON_FILE)
        redukes = Gson().fromJson(jsonString, listType)
    }
}