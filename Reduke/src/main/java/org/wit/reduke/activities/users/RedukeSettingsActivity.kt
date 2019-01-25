package org.wit.reduke.activities.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.*
import org.wit.post.R
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.users.UserModel


class RedukeSettingsActivity : AppCompatActivity(), AnkoLogger {

    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbarSettings.title = "Settings"
        setSupportActionBar(toolbarSettings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val mypreference = RedukeSharedPreferences(this)
        val currEmail = mypreference.getCurrentUserEmail()
        val currPass = mypreference.getCurrentUserPassword()
//        val currHillCnt = mypreference.getCurrentRedukeCount()
        app = application as MainApp
        var users = app.users.findAll()
        user = intent.extras.getParcelable<UserModel>("user_edit")
        editUserEmail.setText(currEmail)
        editUserPassword.setText(currPass)
//        redukeCount.text = "Number of Redukes: " + currHillCnt.toString()
        var foundUser: UserModel? = users.find { p -> p.email == currEmail }
        saveEditUser.setOnClickListener {
            if (foundUser != null) {
                user.id = foundUser.id
                user.username = foundUser.username
            }
            user.email = editUserEmail.text.toString()
            user.password = editUserPassword.text.toString()
            if (user.email.isEmpty() or user.password.isEmpty()) {
                toast(R.string.hint_EnterPostTitle)
            } else {
                if (editUserPassword.text.toString() == editUserPasswordConfirm.text.toString()) {
                    alert(R.string.confirmUserEditSave) {
                        yesButton {
                            mypreference.setCurrentUserEmail(editUserEmail.text.toString())
                            mypreference.setCurrentUserPassword(editUserPassword.text.toString())
                            app.users.update(user)
                            finish()
                        }
                        noButton {}
                    }.show()
                } else {
                    toast("Passwords Do Not Match!")
                }
            }

        }

    }
}

