package org.wit.reduke.activities.feed

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_text_post.view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.R
import org.wit.reduke.activities.users.RedukeSharedPreferences
import org.wit.reduke.models.posts.PostModel

interface RedukeListener {
    // Create listener functions.
    fun onPostCardClick(post: PostModel)

    fun onPostUpvote(post: PostModel)
    fun onPostDownvote(post: PostModel)
    fun onOptionsItemSelected(item: MenuItem?): Boolean
}

// Adapter class with the list of posts and the listener.
class RedukeAdapter(private var posts: List<PostModel>,
                    private val listener: RedukeListener) : RecyclerView.Adapter<RedukeAdapter.MainHolder>(), AnkoLogger {

    // Inflate the current post to a card.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent.context).inflate(org.wit.reduke.R.layout.card_text_post, parent, false))
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



            itemView.textPostTitleField.text = post.title
            itemView.cardPostOwner.text = post.owner
            itemView.cardPostTimestamp.text = post.timestamp.split(" ")[0]
            itemView.cardPostPointCount.text = post.votes.toString() + " points"
            itemView.cardPostSubreddit.text = post.subreddit
            itemView.setOnClickListener { listener.onPostCardClick(post) }
            itemView.cardUpvotePost.setOnClickListener { listener.onPostUpvote(post) }
            itemView.cardDownvotePost.setOnClickListener { listener.onPostDownvote(post) }

            // Color the upvote and downvote buttons based on the current user - if he/she has up-voted or down-voted the current post.
            val userEmail = RedukeSharedPreferences(itemView.cardUpvotePost.context).getCurrentUserEmail()
            if (userEmail in post.upvotedBy) {
                itemView.cardUpvotePost.setImageResource(R.drawable.upvoteactive)
            } else {
                itemView.cardUpvotePost.setImageResource(R.drawable.upvotenotactive)
            }
            if (userEmail in post.downvotedBy) {
                itemView.cardDownvotePost.setImageResource(R.drawable.downvoteactive)
            } else {
                itemView.cardDownvotePost.setImageResource(R.drawable.downvotenotactive)
            }
        }


    }


}