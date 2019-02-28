package org.wit.reduke.activities.users

import android.content.Context

class RedukeSharedPreferences(context: Context) {

    // Create the shared preference field names.
    val PREFERENCE_NAME = "CurrentUser"
    val PREF_VAL_USER_NAME = "CurrentUserName"
    val PREF_VAL_USER_EMAIL = "CurrentUserEmail"
    val PREF_VAL_POST_COUNT = "CurrentRedukeCount"

    // Get a instance of SharedPreferences
    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    // Getters and setters for the current users username
    fun getCurrentUserName(): String {
        return preference.getString(PREF_VAL_USER_NAME, "Name NA")
    }

    fun setCurrentUserName(name: String) {
        val editor = preference.edit()
        editor.putString(PREF_VAL_USER_NAME, name)
        editor.apply()
    }

    // Getters and setters for the current users email
    fun getCurrentUserEmail(): String {
        return preference.getString(PREF_VAL_USER_EMAIL, "Email NA")
    }
    fun setCurrentUserEmail(email: String) {
        val editor = preference.edit()
        editor.putString(PREF_VAL_USER_EMAIL, email)
        editor.apply()
    }

    fun setCurrentRedukeCount(count: Int) {
        val editor = preference.edit()
        editor.putInt(PREF_VAL_POST_COUNT, count)
        editor.apply()
    }

}