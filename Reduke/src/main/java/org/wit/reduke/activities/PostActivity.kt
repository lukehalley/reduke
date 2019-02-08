package org.wit.reduke.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_post.*
import org.jetbrains.anko.*
import org.wit.post.R
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.PostModel


class PostActivity : AppCompatActivity(), AnkoLogger {

    var reduke = PostModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
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
//            additionalNotes.setText(reduke.tags)
//            addressPreview.text = reduke.ownerId
            deleteRedukeBtn.visibility = View.VISIBLE
            addRedukeBtn.setText(R.string.button_savePost)
        }

        addRedukeBtn.setOnClickListener {
            reduke.title = cardPostTitle.text.toString()
            reduke.text = cardRedukeDescription.text.toString()
//            reduke.tags = additionalNotes.text.toString()
//            reduke.timestamp = dateVisited.text.toString()
            if (reduke.title.isEmpty() or reduke.text.isEmpty()) {
                toast(R.string.hint_EnterPostTitle)
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
            alert(R.string.deletePrompt) {
                yesButton {
                    app.redukes.delete(reduke)
                    finish()
                }
                noButton {}
            }.show()
        }

//        visitedSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                val current = LocalDateTime.now()
//                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
//                dateVisited.text = current.format(formatter).toString()
//                dateVisited.visibility = View.VISIBLE
//            } else {
//                dateVisited.visibility = View.INVISIBLE
//            }
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_reduke, menu)
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
                        app.redukes.delete(reduke)
                        finish()
                    }
                    noButton {}
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        alert(R.string.unsavedPrompt) {
            yesButton {
                finish()
                super.onBackPressed()
            }
            noButton {}
        }.show()
    }


}

