package org.wit.reduke.models.users

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.helpers.exists
import org.wit.reduke.helpers.read
import org.wit.reduke.helpers.write
import java.util.*

// Set name for the users JSON file and create instances of both the gsonBuilder and the TypeToken.
const val USER_JSON_FILE = "users.json"
val usergsonBuilder = GsonBuilder().setPrettyPrinting().create()!!
val userListType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type!!

// Create a random id for each user.
fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore(val context: Context) : UserStore, AnkoLogger {
    var users = mutableListOf<UserModel>()

    // If the JSON file for the users deserialize it.
    init {
        if (exists(context, USER_JSON_FILE)) {
            deserialize()
        }
    }

    // Get a list of all current users.
    override fun findAll(): MutableList<UserModel> {
        return users
    }

    // Create a user with a random id and add it to the JSON file.
    override fun create(user: UserModel) {
        user.id = generateRandomUserId()
        users.add(user)
        serialize()
    }

    // Update a existing user.
    override fun update(user: UserModel) {
        val foundUser: UserModel? = users.find { p -> p.id == user.id }
        // Find the user by its id.
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.email = user.email
            foundUser.password = user.password
            serialize()
        }
    }

    // Delete a user and write to the file to update it.
    override fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }

    // Write the users to the JSON file.
    private fun serialize() {
        val jsonString = usergsonBuilder.toJson(users, userListType)
        write(context, USER_JSON_FILE, jsonString)
    }

    // Read the users from the JSON file.
    private fun deserialize() {
        val jsonString = read(context, USER_JSON_FILE)
        users = Gson().fromJson(jsonString, userListType)
    }
}