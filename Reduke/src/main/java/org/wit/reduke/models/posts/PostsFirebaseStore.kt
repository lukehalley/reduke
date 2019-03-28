package org.wit.reduke.models.posts

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.reduke.Posts
import org.wit.reduke.helpers.exists
import org.wit.reduke.helpers.readImageFromPath
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

val post_JSON_FILE = "posts.json"


fun generateRandompostId(): Long {
    return Random().nextLong()
}

class PostsFirebaseStore : PostStore, AnkoLogger {

    var postDatabase: DatabaseReference = FirebaseDatabase.getInstance().reference
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var st: StorageReference

    val context: Context

    val posts = ArrayList<PostModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, post_JSON_FILE)) {
        }
    }

    override fun findAll(): MutableList<PostModel> {
        return posts
    }

    override fun create(post: PostModel) {

        post.id = generateRandompostId()

        info { "KEY: " + auth.uid.toString() }

        var key = postDatabase.child("users").child(auth.uid.toString()).child(Posts.FIREBASE_TASK).push().key

        post.fbId = key!!

        updateImage(post)

        posts.add(post)

        postDatabase.child("users").child(auth.uid.toString()).child(Posts.FIREBASE_TASK).child(key).setValue(post)

    }

    override fun clear() {
        posts.clear()
    }

    override fun update(post: PostModel) {
        var foundpost: PostModel? = posts.find { p -> p.id == post.id }
        if (foundpost != null) {
            foundpost.type = post.type
            foundpost.owner = post.owner
            foundpost.title = post.title
            foundpost.text = post.text
            foundpost.tags = post.tags
            foundpost.votes = post.votes
            foundpost.subreddit = post.subreddit
            foundpost.timestamp = post.timestamp
            foundpost.upvotedBy = post.upvotedBy
            foundpost.downvotedBy = post.downvotedBy
        }
        postDatabase.child("users").child(auth.uid.toString()).child(Posts.FIREBASE_TASK).child(post.fbId).setValue(post)
    }

    fun updateImage(post: PostModel) {
        if (post.image != "") {

            val imageToUpload = File(post.image)


            val imageName = imageToUpload.name

            val imageRef = st.child(auth.uid.toString() + '/' + "firstimages" + '/' + imageName)

            val baos = ByteArrayOutputStream()

            val firstImageBitmap = readImageFromPath(context, post.image)

            firstImageBitmap?.let {
                firstImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    info { it }
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        post.image = it.toString()
                        postDatabase.child("users").child(auth.uid.toString()).child(Posts.FIREBASE_TASK).child(post.fbId).setValue(post)
                    }
                }
            }

            var foundpost: PostModel? = posts.find { p -> p.id == post.id }
            if (foundpost != null) {
                foundpost.type = post.type
                foundpost.owner = post.owner
                foundpost.title = post.title
                foundpost.text = post.text
                foundpost.tags = post.tags
                foundpost.votes = post.votes
                foundpost.subreddit = post.subreddit
                foundpost.timestamp = post.timestamp
                foundpost.upvotedBy = post.upvotedBy
                foundpost.downvotedBy = post.downvotedBy
            }

        }
    }

    override fun delete(post: PostModel) {
        postDatabase.child("users").child(auth.uid.toString()).child(Posts.FIREBASE_TASK).child(post.fbId).removeValue()
        posts.remove(post)
    }

    fun fetchposts(postsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(posts) { it.getValue<PostModel>(PostModel::class.java) }
                postsReady()
            }
        }
        posts.clear()
        st = FirebaseStorage.getInstance().reference
        postDatabase.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child(Posts.FIREBASE_TASK).addListenerForSingleValueEvent(valueEventListener)

        info { "GOT THESE posts: " + posts }

    }
}