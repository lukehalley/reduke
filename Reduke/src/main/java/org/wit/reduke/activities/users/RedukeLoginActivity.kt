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
import org.wit.reduke.activities.feed.FeedActivity
import org.wit.reduke.main.MainApp
import org.wit.reduke.tools.EspressoIdlingResource

class RedukeLoginActivity : AppCompatActivity(), AnkoLogger {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        app = application as MainApp

        super.onCreate(savedInstanceState)
        setContentView(org.wit.reduke.R.layout.activity_login)
        val redukeSharedPref = RedukeSharedPreferences(this)

        loginButton.setOnClickListener {
            if (enteredLoginEmail.text.toString().isNotEmpty() && enteredLoginPassword.text.toString().isNotEmpty()) {
                EspressoIdlingResource.increment()
                showProgress()
                auth.signInWithEmailAndPassword(enteredLoginEmail.text.toString(), enteredLoginPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                redukeSharedPref.setCurrentUserEmail(enteredLoginEmail.text.toString())
                                startActivityForResult(intentFor<FeedActivity>().putExtra("loggedInUser", enteredLoginEmail.text.toString()), 0)
                            } else {
                                // If sign in fails, display a message to the user.
                                toast(org.wit.reduke.R.string.toast_InvalidCreds)
                            }
                            if (!task.isSuccessful) {
                                toast(org.wit.reduke.R.string.toast_AuthFail)
                            }
                            hideProgress()
                            if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                                EspressoIdlingResource.decrement()
                            }
                        }
            } else if (enteredLoginEmail.text.toString().isEmpty()) {
                toast("Please Enter Your Email")
            } else if (enteredLoginPassword.text.toString().isEmpty()) {
                toast("Please Enter Your Email")
            } else {
                toast(org.wit.reduke.R.string.hint_EnterAllFields)
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