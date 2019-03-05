package org.wit.reduke.activities.users

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.wit.reduke.activities.feed.FeedActivity
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.posts.PostsFirebaseStore
import org.wit.reduke.tools.EspressoIdlingResource


class RedukeLoginActivity : AppCompatActivity(), AnkoLogger {

    // Get instance of the Firebase Auth
    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Get instance of the Google Sign In
    val RC_SIGN_IN = 1

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()


    // Get instance of the Firebase Database
    var fireStore: PostsFirebaseStore? = null

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        app = application as MainApp

        if (app.posts is PostsFirebaseStore) {
            fireStore = app.posts as PostsFirebaseStore
        }

        super.onCreate(savedInstanceState)
        setContentView(org.wit.reduke.R.layout.activity_login)
        val redukeSharedPref = RedukeSharedPreferences(this)

        // Setup the login button functionality.
        firebaseLoginButton.setOnClickListener {
            // Make sure the fields are not empty, if they are tell the user to fill them.
            if (enteredLoginEmail.text.toString().isNotEmpty() && enteredLoginPassword.text.toString().isNotEmpty()) {
                // Tell Espresso to wait for UI testing as we do not know how long the Firebase login will take.
                EspressoIdlingResource.increment()
                // Show the loading indicator.
                showProgress()
                // Use the details entered by the user to sign in to the firebase auth cloud service.
                auth.signInWithEmailAndPassword(enteredLoginEmail.text.toString(), enteredLoginPassword.text.toString())
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                if (fireStore != null) {
                                    // Sign in success, update UI with the signed-in user's information
                                    fireStore!!.fetchPosts {
                                        // If the user logs in, set their email in the redukeSharedPref for use later on.
                                        redukeSharedPref.setCurrentUserEmail(enteredLoginEmail.text.toString())
                                        startActivityForResult(intentFor<FeedActivity>().putExtra("loggedInUser", enteredLoginEmail.text.toString()), 0)
                                    }

                                }
                            } else {
                                // If sign in fails due to incorrect details, display a message to the user.
                                toast(org.wit.reduke.R.string.toast_InvalidCreds)
                            }
                            // If user isn't found display this message.
                            if (!task.isSuccessful) {
                                toast(org.wit.reduke.R.string.toast_AuthFail)
                            }
                            hideProgress()
                            // Tell Espresso to it cant continue now.
                            if (!EspressoIdlingResource.getIdlingResource().isIdleNow) {
                                EspressoIdlingResource.decrement()
                            }
                        }
                // Tell the user to enter their email if blank.
            } else if (enteredLoginEmail.text.toString().isEmpty()) {
                toast("Please Enter Your Email")
                // Tell the user to enter their password if blank.
            } else if (enteredLoginPassword.text.toString().isEmpty()) {
                toast("Please Enter Your Password")
            } else {
                toast(org.wit.reduke.R.string.hint_EnterAllFields)
            }
        }

        // Set the register button to navigate to the RedukeRegisterActivity.
        navToRegisterButton.setOnClickListener {
            startActivityForResult<RedukeRegisterActivity>(0)
        }

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        googleLoginButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

    }

    // Functions to show and hide loading animation.
    fun showProgress() {
        loadingLoginIndicator.visibility = View.VISIBLE
    }

    fun hideProgress() {
        loadingLoginIndicator.visibility = View.GONE
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        val redukeSharedPref = RedukeSharedPreferences(this)
        try {
            val account = completedTask.getResult(ApiException::class.java)

            if (account != null) {
                val personName = account.displayName
                val personGivenName = account.givenName
                val personFamilyName = account.familyName
                val personEmail = account.email
                val personId = account.id
                val personPhoto = account.photoUrl

                if (personEmail != null) {
                    redukeSharedPref.setCurrentUserEmail(personEmail)
                }

                if (fireStore != null) {
                    // Sign in success, update UI with the signed-in user's information
                    fireStore!!.fetchPosts {
                        // If the user logs in, set their email in the redukeSharedPref for use later on.
                        redukeSharedPref.setCurrentUserEmail(enteredLoginEmail.text.toString())
                        startActivityForResult(intentFor<FeedActivity>().putExtra("loggedInUser", personEmail), 0)
                    }

                }
            }
        } catch (e: ApiException) {
            toast(org.wit.reduke.R.string.toast_GoogleAuthFail)
        }

    }

}