package org.wit.reduke.activities.feed

import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.card_post.view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.reduke.models.posts.PostModel


interface RedukeListener {
    fun onPostCardClick(post: PostModel)
    fun onPostUpvote(post: PostModel)
    fun onPostDownvote(post: PostModel)
    fun onOptionsItemSelected(item: MenuItem?): Boolean
    fun setCardUpvoteColor(post: PostModel): Int
    fun setCardDownvoteColor(post: PostModel): Int
}

class RedukeAdapter(private var posts: List<PostModel>,
                    private val listener: RedukeListener) : RecyclerView.Adapter<RedukeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(org.wit.reduke.R.layout.card_post, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val post = posts[holder.adapterPosition]
        holder.bind(post, listener)
    }

    override fun getItemCount(): Int = posts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {

        @SuppressLint("SetTextI18n")
        fun bind(post: PostModel, listener: RedukeListener) {

            itemView.postTitleField.text = post.title
            itemView.cardPostOwner.text = post.postOwner
            itemView.cardPostTimestamp.text = post.timestamp.split(" ")[0]
            itemView.cardPostPointCount.text = post.votes.toString() + " points"
            itemView.setOnClickListener { listener.onPostCardClick(post) }
            itemView.cardUpvotePost.setOnClickListener { listener.onPostUpvote(post) }
            itemView.cardDownvotePost.setOnClickListener { listener.onPostDownvote(post) }

            DrawableCompat.setTint(
                    itemView.cardUpvotePost.drawable,
                    ContextCompat.getColor(itemView.context, listener.setCardUpvoteColor(post))
            )
            DrawableCompat.setTint(
                    itemView.cardDownvotePost.drawable,
                    ContextCompat.getColor(itemView.context, listener.setCardDownvoteColor(post))
            )

//            itemView.cardUpvotePost.setColorFilter(listener.setCardUpvoteColor(post))
//            itemView.cardDownvotePost.setColorFilter(listener.setCardDownvoteColor(post))
        }


    }


}