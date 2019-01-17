package org.wit.reduke.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostModel(var id: Long = 0,
                     var type: String = "",
                     var title: String = "",
                     var text: String = "",
                     var tag: String = "") : Parcelable

