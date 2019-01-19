package org.wit.reduke.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_feed.*
import org.jetbrains.anko.*
import org.wit.post.R
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.PostModel
import org.wit.reduke.models.UserModel

class FeedActivity : AppCompatActivity(), RedukeListener, AnkoLogger {

    lateinit var app: MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        app = application as MainApp
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = RedukeAdapter(app.posts.findAll(), this)
        loadRedukes()
        addRedukeFab.setOnClickListener() {
            startActivityForResult<CreatePostActivity>(0)
        }

        var mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            var user = UserModel()
//            when (menuItem?.itemId) {
//                R.id.pos -> startActivityForResult<CreatePostActivity>(0)
//            }
//            when (menuItem?.itemId) {
//                R.id.nav_Logout ->
//                    alert(R.string.logoutPrompt) {
//                        yesButton {
//                            finish()
//                        }
//                        noButton {}
//                    }.show()
//            }
            mDrawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feed, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var mDrawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        var user = UserModel()
        when (item?.itemId) {
            android.R.id.home -> mDrawerLayout.openDrawer(GravityCompat.START)
        }
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<CreatePostActivity>(0)
        }
        when (item?.itemId) {
            R.id.item_logout ->
                alert(R.string.logoutPrompt) {
                    yesButton {
                        finish()
                    }
                    noButton {}
                }.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRedukeClick(post: PostModel) {
        startActivityForResult(intentFor<CreatePostActivity>().putExtra("reduke_edit", post), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadRedukes()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadRedukes() {
        showRedukes(app.posts.findAll())
    }

    fun showRedukes(redukes: List<PostModel>) {
        val mypreference = RedukeSharedPreferences(this)
        val userName = mypreference.getCurrentUserName()
        val userEmail = mypreference.getCurrentUserEmail()
        mypreference.setCurrentRedukeCount(redukes.size)
        var visCounted = 0
        mypreference.setCurrentVisitRedukeCount(visCounted)
        recyclerView.adapter = RedukeAdapter(redukes, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        alert(R.string.logoutPrompt) {
            yesButton {
                finish()
                super.onBackPressed()
            }
            noButton {}
        }.show()
    }
}