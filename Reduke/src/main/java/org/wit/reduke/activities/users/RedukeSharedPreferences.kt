package org.wit.reduke.activities.users

import android.content.Context

class RedukeSharedPreferences(context: Context) {

    val PREFERENCE_NAME = "CurrentUser"
    val PREF_VAL_USER_NAME = "CurrentUserName"
    val PREF_VAL_USER_EMAIL = "CurrentUserEmail"
    val PREF_VAL_USER_PASSWORD = "CurrentUserPassword"
    val PREF_VAL_POST_COUNT = "CurrentRedukeCount"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getCurrentUserName(): String {
        return preference.getString(PREF_VAL_USER_NAME, "Name NA")
    }

    fun setCurrentUserName(name: String) {
        val editor = preference.edit()
        editor.putString(PREF_VAL_USER_NAME, name)
        editor.apply()
    }

    fun getCurrentUserEmail(): String {
        return preference.getString(PREF_VAL_USER_EMAIL, "Email NA")
    }

    fun setCurrentUserEmail(email: String) {
        val editor = preference.edit()
        editor.putString(PREF_VAL_USER_EMAIL, email)
        editor.apply()
    }

    fun getCurrentUserPassword(): String {
        return preference.getString(PREF_VAL_USER_PASSWORD, "Password NA")
    }

    fun setCurrentUserPassword(password: String) {
        val editor = preference.edit()
        editor.putString(PREF_VAL_USER_PASSWORD, password)
        editor.apply()
    }

    fun getCurrentRedukeCount(): Int {
        return preference.getInt(PREF_VAL_POST_COUNT, 0)
    }

    fun setCurrentRedukeCount(count: Int) {
        val editor = preference.edit()
        editor.putInt(PREF_VAL_POST_COUNT, count)
        editor.apply()
    }

}