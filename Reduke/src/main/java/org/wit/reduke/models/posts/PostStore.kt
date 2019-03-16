package org.wit.reduke.models.posts

interface PostStore {
    // Get all posts.
    fun findAll(): List<PostModel>

    // Create a post.
    fun create(post: PostModel)

    // Update a post.
    fun update(post: PostModel)

    // Delete a post.
    fun delete(post: PostModel)

    // Clear the posts.
    fun clear()
}