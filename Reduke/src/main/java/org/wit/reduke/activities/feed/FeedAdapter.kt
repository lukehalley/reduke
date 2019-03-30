package org.wit.reduke.activities.feed

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.PopupMenu
import io.github.ponnamkarthik.richlinkpreview.ViewListener
import kotlinx.android.synthetic.main.card.view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.R.*
import org.wit.reduke.activities.posts.ImagePostActivity
import org.wit.reduke.activities.posts.LinkPostActivity
import org.wit.reduke.activities.posts.TextPostActivity
import org.wit.reduke.activities.users.RedukeSharedPreferences
import org.wit.reduke.helpers.readImageFromPath
import org.wit.reduke.models.posts.PostModel


interface RedukeListener {
    // Create listener functions.
    fun onTextPostCardClick(post: PostModel)

    fun onImagePostCardClick(post: PostModel)
    fun onLinkPostCardClick(post: PostModel)

    fun onPostUpvote(post: PostModel)
    fun onPostDownvote(post: PostModel)
    fun onOptionsItemSelected(item: MenuItem?): Boolean
}

// Adapter class with the list of posts and the listener.
class RedukeAdapter(private var posts: List<PostModel>,
                    private val listener: RedukeListener) : RecyclerView.Adapter<RedukeAdapter.MainHolder>(), AnkoLogger {


    // Inflate the current post to a card.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(layout.card, parent, false))
    }

    // Bind current post to the feed.
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val post = posts[holder.adapterPosition]
        holder.bind(post, listener)
    }

    // Get the number of posts.
    override fun getItemCount(): Int = posts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {

        // Bind the post data to all of the fields.
        @SuppressLint("SetTextI18n")
        fun bind(post: PostModel, listener: RedukeListener) {

            var postToEdit: String


            var url = post.link
            if (!url.startsWith("https://") && !URLUtil.isValidUrl(url)) {
                url = "https://$url"
            }

            itemView.textPostTitleField.text = post.title
            itemView.cardPostOwner.text = post.owner
            itemView.cardPostTimestamp.text = post.timestamp.split(" ")[0]
            itemView.cardPostPointCount.text = post.votes.toString() + " points"
            itemView.cardPostSubreddit.text = post.subreddit

            itemView.cardUpvotePost.setOnClickListener { listener.onPostUpvote(post) }
            itemView.cardDownvotePost.setOnClickListener { listener.onPostDownvote(post) }

            // Color the upvote and downvote buttons based on the current user - if he/she has up-voted or down-voted the current post.
            val userEmail = RedukeSharedPreferences(itemView.cardUpvotePost.context).getCurrentUserEmail()
            if (userEmail in post.upvotedBy) {
                itemView.cardUpvotePost.setImageResource(drawable.upvoteactive)
            } else {
                itemView.cardUpvotePost.setImageResource(drawable.upvotenotactive)
            }
            if (userEmail in post.downvotedBy) {
                itemView.cardDownvotePost.setImageResource(drawable.downvoteactive)
            } else {
                itemView.cardDownvotePost.setImageResource(drawable.downvotenotactive)
            }

            // Set Card Design Based On Post Type
            when (post.type) {
                "Text" -> {
                    postToEdit = "text"
                    itemView.imagePostCardImage.visibility = View.GONE
                    itemView.imagePostCardLink.visibility = View.GONE
                }
                "Image" -> {
                    postToEdit = "image"
                    itemView.imagePostCardImage.setImageBitmap(readImageFromPath(itemView.context, post.image))
                    itemView.imagePostCardImage.visibility = View.VISIBLE
                    itemView.imagePostCardLink.visibility = View.GONE
                }
                "Link" -> {
                    postToEdit = "link"
                    itemView.imagePostCardLink.setLink(url, object : ViewListener {
                        override fun onSuccess(status: Boolean) {

                        }

                        override fun onError(e: Exception) {
                            error { "Couldnt get URL Preview: $e" }
                        }
                    })
                    itemView.imagePostCardImage.visibility = View.GONE
                    itemView.imagePostCardLink.visibility = View.VISIBLE
                }
                else -> error { "Unknown Post Type, Can't Set Card Design!" }
            }



            fun showPopup(view: View) {
                val popup: PopupMenu?
                popup = PopupMenu(itemView.cardPostMenu.context, view)
                popup.inflate(org.wit.reduke.R.menu.card_menu)

                if (postToEdit == "text") {
                    popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                        when (item!!.itemId) {
                            org.wit.reduke.R.id.editPopup -> {
                                val intent = Intent(itemView.cardPostMenu.context, TextPostActivity::class.java).putExtra("post_edit", post)
                                (itemView.cardPostMenu.context as Activity).startActivityForResult(intent, 0)
                            }
                        }

                        true
                    })

                    popup.show()
                } else if (postToEdit == "image") {
                    popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                        when (item!!.itemId) {
                            org.wit.reduke.R.id.editPopup -> {
                                val intent = Intent(itemView.cardPostMenu.context, ImagePostActivity::class.java).putExtra("post_edit", post)
                                (itemView.cardPostMenu.context as Activity).startActivityForResult(intent, 0)
                            }
                        }

                        true
                    })

                    popup.show()
                } else if (postToEdit == "link") {
                    popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                        when (item!!.itemId) {
                            org.wit.reduke.R.id.editPopup -> {
                                val intent = Intent(itemView.cardPostMenu.context, LinkPostActivity::class.java).putExtra("post_edit", post)
                                (itemView.cardPostMenu.context as Activity).startActivityForResult(intent, 0)
                            }
                        }

                        true
                    })

                    popup.show()
                }


            }

            val clickListener = View.OnClickListener { view ->
                when (view.id) {
                    id.cardPostMenu -> {
                        showPopup(view)
                    }
                }
            }

            itemView.cardPostMenu.setOnClickListener(clickListener)


        }


    }


}