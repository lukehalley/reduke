package org.wit.reduke.models.posts

interface PostStore {
    fun findAll(): List<PostModel>
    fun create(post: PostModel)
    fun update(post: PostModel)
    fun delete(post: PostModel)
}