package org.wit.reduke.activities.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.reduke.R
import org.wit.reduke.activities.feed.FeedActivity
import org.wit.reduke.main.MainApp


class RedukeLoginActivity : AppCompatActivity(), AnkoLogger {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        app = application as MainApp

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val redukeSharedPref = RedukeSharedPreferences(this)

        loginButton.setOnClickListener {
            if (enteredRegisterEmail.text.toString().isNotEmpty() && enteredRegisterPassword.text.toString().isNotEmpty()) {
                showProgress()
                auth.signInWithEmailAndPassword(enteredRegisterEmail.text.toString(), enteredRegisterPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                redukeSharedPref.setCurrentUserEmail(enteredRegisterEmail.text.toString())
                                startActivityForResult(intentFor<FeedActivity>().putExtra("loggedInUser", enteredRegisterEmail.text.toString()), 0)
                            } else {
                                // If sign in fails, display a message to the user.
                                toast(R.string.toast_InvalidCreds)
                            }
                            if (!task.isSuccessful) {
                                toast(R.string.toast_AuthFail)
                            }
                            hideProgress()
                        }
            } else if (enteredRegisterEmail.text.toString().isEmpty()) {
                toast("Please Enter Your Email")
            } else if (enteredRegisterPassword.text.toString().isEmpty()) {
                toast("Please Enter Your Email")
            } else {
                toast(R.string.hint_EnterAllFields)
            }
        }

        navToRegisterButton.setOnClickListener {
            startActivityForResult<RedukeRegisterActivity>(0)
        }
    }

    fun showProgress() {
        loadingLoginIndicator.visibility = View.VISIBLE
    }

    fun hideProgress() {
        loadingLoginIndicator.visibility = View.GONE
    }

}