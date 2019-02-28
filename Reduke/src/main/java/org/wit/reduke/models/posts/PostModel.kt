package org.wit.reduke.models.posts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Model of each Post
//
// id: unique id of the post
// postOwner: user who created the post
// title: title of the post
// text: body text of the post.
// tags: tags set by the user set when created - EG: Question, Answer...
// votes: number of votes the post has decided by users upvoting and downvoting the post.
// subreddit: subreddit the post belongs too.
// timestamp: time the post was created.
// upvotedBy: list of user emails who upvoted the post.
// downvotedBy: list of user emails who downvoted the post.
@Parcelize
data class PostModel(var id: Long = 0,
                     var postOwner: String = "",
                     var title: String = "",
                     var text: String = "",
                     var tags: String = "",
                     var votes: Int = 0,
                     var subreddit: String = "",
                     var timestamp: String = "",
                     var upvotedBy: MutableList<String> = mutableListOf(),
                     var downvotedBy: MutableList<String> = mutableListOf()
) : Parcelable

