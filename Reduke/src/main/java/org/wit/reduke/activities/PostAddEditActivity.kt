package org.wit.reduke.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_post.*
import org.jetbrains.anko.*
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.PostModel


class PostAddEditActivity : AppCompatActivity(), AnkoLogger {

    var reduke = PostModel()
    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.wit.post.R.layout.activity_post)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        app = application as MainApp
        var edit = false


        if (intent.hasExtra("post_edit")) {
            edit = true
            toolbarAdd.title = "Edit Reduke"
            setSupportActionBar(toolbarAdd)
            reduke = intent.extras.getParcelable<PostModel>("post_edit")
            cardPostTitle.setText(reduke.title)
            cardRedukeDescription.setText(reduke.text)
            deleteRedukeBtn.visibility = VISIBLE
            addRedukeBtn.setText(org.wit.post.R.string.button_savePost)
        }

        addRedukeBtn.setOnClickListener {
            reduke.title = cardPostTitle.text.toString()
            reduke.text = cardRedukeDescription.text.toString()
            if (reduke.title.isEmpty() or reduke.text.isEmpty()) {
                toast(org.wit.post.R.string.hint_EnterPostTitle)
            } else {
                if (edit) {
                    app.redukes.update(reduke.copy())
                } else {
                    app.redukes.create(reduke.copy())
                }
                info("add Button Pressed: $cardPostTitle")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

        deleteRedukeBtn.setOnClickListener {
            alert(org.wit.post.R.string.deletePrompt) {
                yesButton {
                    app.redukes.delete(reduke)
                    finish()
                }
                noButton {}
            }.show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(org.wit.post.R.menu.menu_post_add_edit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            org.wit.post.R.id.item_cancel -> {
                alert(org.wit.post.R.string.unsavedPrompt) {
                    yesButton {
                        finish()
                    }
                    noButton {}
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        alert(org.wit.post.R.string.unsavedPrompt) {
            yesButton {
                finish()
                super.onBackPressed()
            }
            noButton {}
        }.show()
    }


}

