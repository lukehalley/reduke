package org.wit.reduke.activities.users

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.post.R
import org.wit.reduke.main.MainApp

class RedukeRegisterActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        toolbarRegister.title = title
        setSupportActionBar(toolbarRegister)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp
        registerButton.setOnClickListener {
            // Show loading indicator after the user clicks the register button.
            showProgress()
            auth.createUserWithEmailAndPassword(enteredEmail.text.toString(), enteredPassword.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (enteredName.text.toString().isEmpty() or enteredPassword.text.toString().isEmpty()) {
                            toast(R.string.hint_EnterAllFields)
                        } else {
                            if (enteredPassword.text.toString() == enteredPasswordConfirm.text.toString()) {
                                if (task.isSuccessful) {
                                    val mypreference = RedukeSharedPreferences(this)
                                    mypreference.setCurrentUserID(auth.uid.toString())
                                    // Sign in success, update UI with the signed-in user's information
                                    toast(R.string.hint_SucessfullRegister)
                                    val user = auth.currentUser
                                    finish()
                                } else {
                                    toast("User Registration Failed!" + task.exception)
                                    info { "User Registration Failed!" + task.exception }
                                }
                            } else {
                                toast("Passwords Do Not Match!")
                            }
                        }

                        // Hide loading indicator after the user registers.
                        hideProgress()
                    }

        }

    }

    fun showProgress() {
        loadingRegisterIndicator.visibility = View.VISIBLE
    }

    fun hideProgress() {
        loadingRegisterIndicator.visibility = View.GONE
    }

}

