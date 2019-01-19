package org.wit.reduke.activities
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.feed_card.view.*
import org.jetbrains.anko.AnkoLogger
import org.wit.post.R
import org.wit.reduke.models.PostModel

interface RedukeListener {
    fun onRedukeClick(post: PostModel)
    fun onOptionsItemSelected(item: MenuItem?): Boolean
}

class RedukeAdapter(private var posts: List<PostModel>,
                    private val listener: RedukeListener) : RecyclerView.Adapter<RedukeAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.feed_card, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val post = posts[holder.adapterPosition]
        holder.bind(post, listener)
    }

    override fun getItemCount(): Int = posts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView), AnkoLogger {

        fun bind(post: PostModel, listener: RedukeListener) {
            itemView.cardRedukeTitle.text = post.title
            itemView.cardRedukeDescription.text = post.text
            itemView.setOnClickListener { listener.onRedukeClick(post) }

//            if (post.type == "Text"){
//                itemView.visitedIndicator.setBackgroundColor(Color.parseColor("#5db761"))
//                itemView.visitedIndicator.setText(R.string.isVisited)
//            } else if (post.type == "Link") {
//                itemView.visitedIndicator.setBackgroundColor(Color.parseColor("#FF9E9E9E"))
//                itemView.visitedIndicator.setText(R.string.notVisited)
//            }

        }
    }
}