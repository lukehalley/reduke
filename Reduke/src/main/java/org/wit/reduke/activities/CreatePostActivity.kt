package org.wit.reduke.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.feed_card.*
import org.jetbrains.anko.*
import org.wit.post.R
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.PostModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CreatePostActivity : AppCompatActivity(), AnkoLogger {

    var post = PostModel()
    lateinit var app: MainApp

    var mCurrentPhotoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        info("Reduke Activity started..")

        app = application as MainApp
        var edit = false


        if (intent.hasExtra("reduke_edit")) {
            edit = true
            toolbarAdd.title = "Edit Reduke"
            setSupportActionBar(toolbarAdd)
            post = intent.extras.getParcelable<PostModel>("reduke_edit")
            deleteRedukeBtn.visibility = View.VISIBLE
            addRedukeBtn.setText(R.string.button_saveReduke)
        }

        deleteRedukeBtn.setOnClickListener {
            alert(R.string.deletePrompt) {
                yesButton {
                    app.posts.delete(post)
                    finish()
                }
                noButton {}
            }.show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                alert(R.string.unsavedPrompt) {
                    yesButton {
                        finish()
                    }
                    noButton {}
                }.show()
            }
            R.id.item_deleteReduke -> {
                alert(R.string.deletePrompt) {
                    yesButton {
                        app.posts.delete(post)
                        finish()
                    }
                    noButton {}
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

        }



