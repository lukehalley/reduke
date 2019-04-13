package org.wit.reduke.models.users

interface UserStore {
    // Get all users.
    fun findAll(): List<UserModel>

    // Create a user.
    fun create(user: UserModel)

    // Update a user.
    fun update(user: UserModel)

    // Delete a user.
    fun delete(user: UserModel)
}