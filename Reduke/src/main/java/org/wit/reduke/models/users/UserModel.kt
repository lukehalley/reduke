package org.wit.reduke.models.users

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Model of each User
//
// id: unique id of the user
// username: the username of the user.
// email: the email of the user.
// password: the password of the user.
@Parcelize
data class UserModel(var id: Long = 0,
                     var username: String = "",
                     var email: String = "",
                     var password: String = "") : Parcelable

