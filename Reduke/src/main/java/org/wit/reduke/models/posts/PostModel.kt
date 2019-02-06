package org.wit.reduke.models.posts

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostModel(var id: Long = 0,
                     var postOwner: String = "",
                     var title: String = "",
                     var text: String = "",
                     var tags: String = "",
                     var votes: Int = 0,
                     var timestamp: String = "",
                     var upvotedBy: MutableList<String> = mutableListOf(),
                     var downvotedBy: MutableList<String> = mutableListOf()
) : Parcelable

