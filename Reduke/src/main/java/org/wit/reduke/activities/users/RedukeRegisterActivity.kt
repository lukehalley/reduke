package org.wit.reduke.activities.users

import android.os.Bundle
import android.support.test.espresso.idling.CountingIdlingResource
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
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var mIdlingRes = CountingIdlingResource("loginIdling")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_register)
        toolbarRegister.title = title
        setSupportActionBar(toolbarRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp
        registerButton.setOnClickListener {
            // App is busy until further notice enteredRegisterPassword
            // Show loading indicator after the user clicks the register button.
            showProgress()
            EspressoIdlingResource.increment()
            auth.createUserWithEmailAndPassword(enteredRegisterEmail.text.toString(), enteredRegisterPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (enteredRegisterEmail.text.toString().isEmpty() or enteredRegisterPassword.text.toString().isEmpty()) {
                            toast(org.wit.reduke.R.string.hint_EnterAllFields)
                        } else {
                            if (enteredRegisterPassword.text.toString() == enteredRegisterPasswordConfirm.text.toString()) {
                                if (task.isSuccessful) {
                                    val mypreference = RedukeSharedPreferences(this)
                                    mypreference.setCurrentUserName(enteredRegisterUsername.text.toString())
                                    // Sign in success, update UI with the signed-in user's information
                                    toast(org.wit.reduke.R.string.hint_SucessfullRegister)
                                    val user = auth.currentUser
                                    // Hide loading indicator after the user registers.
                                    hideProgress()
                                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                                        EspressoIdlingResource.decrement()
                                    }
                                    finish()

                                } else {
                                    toast("User Registration Failed!" + task.exception)
                                }
                            } else {
                                toast("Passwords Do Not Match!")
                            }
                        }


                    }

        }

    }

    fun showProgress() {
        loadingRegisterIndicator.visibility = View.VISIBLE
    }

    fun hideProgress() {
        loadingRegisterIndicator.visibility = View.GONE
    }

    fun getIdlingResourceInTest(): CountingIdlingResource {
        return mIdlingRes
    }

}

