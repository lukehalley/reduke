package org.wit.reduke.activities.posts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.VISIBLE
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_post.*
import org.jetbrains.anko.*
import org.wit.reduke.activities.users.RedukeSharedPreferences
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.posts.PostModel
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


class LinkPostActivity : AppCompatActivity(), AnkoLogger {

    val subreddits = listOf("Android", "Education", "Fashion", "Funny", "Ireland", "Music", "News", "Photography", "Technology", "Video")

    var post = PostModel()
    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.wit.reduke.R.layout.activity_post)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        // Show the up the button to allow the user to navigate back to the feed.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        app = application as MainApp

        // Create a boolean to track if the user is editing or adding a post.
        var edit = false

        // If the intent contains post_edit the user is editing a post.
        if (intent.hasExtra("post_edit")) {
            // Set edit mode true
            edit = true

            // Set toolbar title to Edit Post.
            toolbarAdd.title = "Edit Post"
            setSupportActionBar(toolbarAdd)
            post = intent.extras.getParcelable<PostModel>("post_edit")

            // Fill the fields with existing data.
            postTitleField.setText(post.title)
            postDescriptionField.setText(post.text)
            subredditSpinner.setSelection(subreddits.indexOf(post.subreddit))
            subredditSpinner.isEnabled = false

            deletePostBtn.visibility = VISIBLE

            // Set the button to display save instead of add.
            addPostBtn.setText(org.wit.reduke.R.string.button_savePost)
        }

        // If the user is adding a post.
        addPostBtn.setOnClickListener {
            // Get the data from all the post fields.
            post.title = postTitleField.text.toString()
            post.type = "Link"
            post.text = postDescriptionField.text.toString()
            // Create a timestamp for when the post has been created.
            post.timestamp = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy HH:mm:ss.SSSSSS")
                    .withZone(ZoneOffset.UTC)
                    .format(Instant.now())
            // Get the value from the subreddit spinner.
            val subredditSpinner = findViewById<Spinner>(org.wit.reduke.R.id.subredditSpinner)
            post.subreddit = subredditSpinner.selectedItem.toString()

            // Get an instance of the RedukeSharedPreferences.
            val redukeSharedPref = RedukeSharedPreferences(this)
            post.owner = redukeSharedPref.getCurrentUserName()

            // If the fields are empty tell the user they need to fill them.
            if (post.title.isEmpty() or post.text.isEmpty()) {
                toast(org.wit.reduke.R.string.hint_EnterPostTitle)
            } else {
                if (edit) {
                    // If the user has edited a post update it.
                    app.posts.update(post.copy())
                } else {
                    // If the user has added a post add it to the posts.
                    app.posts.create(post.copy())
                }
                // Return a result of all ok and finish activity.
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }

        }

        // Set up the delete buttons functionality.
        deletePostBtn.setOnClickListener {
            // Make sure the user actually wants to the delete the post with a yes or no popup.
            alert(org.wit.reduke.R.string.deletePrompt) {
                yesButton {
                    app.posts.delete(post)
                    finish()
                }
                noButton {}
            }.show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(org.wit.reduke.R.menu.menu_post_add_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Set up the cancel button functionality.
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            org.wit.reduke.R.id.item_cancel -> {
                // Make sure the user actually wants to discard post with a yes or no popup.
                alert(org.wit.reduke.R.string.unsavedPrompt) {
                    yesButton {
                        finish()
                    }
                    noButton {}
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Make sure the user actually wants to discard post with a yes or no popup.
    override fun onBackPressed() {
        alert(org.wit.reduke.R.string.unsavedPrompt) {
            yesButton {
                finish()
                super.onBackPressed()
            }
            noButton {}
        }.show()
    }


}

