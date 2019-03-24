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

// Set name for the posts JSON file and create instances of both the gsonBuilder and the TypeToken.
const val POST_JSON_FILE = "posts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()!!
val listType = object : TypeToken<java.util.ArrayList<PostModel>>() {}.type!!

// Create a random id for each post.
fun generateRandomRedukeId(): Long {
    return Random().nextLong()
}

class PostJSONStore(val context: Context) : PostStore, AnkoLogger {
    var posts = mutableListOf<PostModel>()
    // If the JSON file for the posts deserialize it.
    init {
        if (exists(context, POST_JSON_FILE)) {
            deserialize()
        }
    }

    // Get a list of all current posts.
    override fun findAll(): MutableList<PostModel> {
        return posts
    }

    // Create a post with a random id and add it to the JSON file.
    override fun create(post: PostModel) {
        post.id = generateRandomRedukeId()
        posts.add(post)
        serialize()
    }

    // Update a existing post.
    override fun update(post: PostModel) {
        // Find the post by its id.
        val foundPost: PostModel? = posts.find { p -> p.id == post.id }
        // As long as the found post is not null update the post.
        if (foundPost != null) {
            foundPost.owner = post.owner
            foundPost.title = post.title
            foundPost.text = post.text
            foundPost.tags = post.tags
            foundPost.votes = post.votes
            foundPost.timestamp = post.timestamp
            foundPost.upvotedBy = post.upvotedBy
            serialize()
        }
    }

    // Delete a post and write to the file to update it.
    override fun delete(post: PostModel) {
        posts.remove(post)
        serialize()
    }

    // Write the posts to the JSON file.
    private fun serialize() {
        val jsonString = gsonBuilder.toJson(posts, listType)
        write(context, POST_JSON_FILE, jsonString)
    }

    // Read the posts from the JSON file.
    private fun deserialize() {
        val jsonString = read(context, POST_JSON_FILE)
        posts = Gson().fromJson(jsonString, listType)
    }
}