package org.wit.reduke.activities.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.reduke.R.layout
import org.wit.reduke.main.MainApp
import org.wit.reduke.tools.EspressoIdlingResource


class RedukeRegisterActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    // Create instance of FirebaseAuth
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_register)
        toolbarRegister.title = title
        setSupportActionBar(toolbarRegister)
        // Show the up arrow in the toolbar allowing the user to cancel their register.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        app = application as MainApp
        registerButton.setOnClickListener {
            // Show the loading indicator when registering with cloud.

            // Tell Espresso test to wait.
            EspressoIdlingResource.increment()
            // Tell the user to fill in all fields if they leave them blank.
            if (enteredRegisterEmail.text.toString().isEmpty() or enteredRegisterPassword.text.toString().isEmpty()) {
                hideProgress()
                toast(org.wit.reduke.R.string.hint_EnterAllFields)
            } else {
                if (enteredRegisterPassword.text.toString() == enteredRegisterPasswordConfirm.text.toString()) {
                    // Create a user with the email and password entered by the user.
                    auth.createUserWithEmailAndPassword(enteredRegisterEmail.text.toString(), enteredRegisterPassword.text.toString())
                            .addOnCompleteListener(this) { task ->
                                showProgress()
                                // If register is successful.
                                if (task.isSuccessful) {
                                    val mypreference = RedukeSharedPreferences(this)
                                    mypreference.setCurrentUserName(enteredRegisterUsername.text.toString())
                                    // Sign in success, update UI with the signed-in user's information
                                    toast(org.wit.reduke.R.string.hint_SucessfullRegister)
                                    auth.currentUser
                                    // Hide loading indicator after the user registers.
                                    hideProgress()
                                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                                        EspressoIdlingResource.decrement()
                                    }
                                    finish()
                                    // If the register isn't successful.
                                } else {
                                    hideProgress()
                                    toast("User Registration Failed!" + task.exception.toString())
                                }
                                // The user is told to enter their password twice to make sure they
                                // enter the password they mean. If they do not match show a toast message.
                            }
                } else {
                    hideProgress()
                    toast("Passwords Do Not Match!")
                }
            }
        }

    }

    // Functions to show and hide loading animation.
    private fun showProgress() {
        loadingRegisterIndicator.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        loadingRegisterIndicator.visibility = View.GONE
    }

}

