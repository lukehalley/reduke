package org.wit.reduke.activities.feed

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.card_post.*
import org.jetbrains.anko.*
import org.wit.reduke.activities.posts.PostAddEditActivity
import org.wit.reduke.activities.users.RedukeSharedPreferences
import org.wit.reduke.main.MainApp
import org.wit.reduke.models.posts.PostModel
import org.wit.reduke.models.users.UserModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class FeedActivity : AppCompatActivity(), RedukeListener, AnkoLogger {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var app: MainApp
    var sortSetting = "Top"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(org.wit.reduke.R.layout.activity_feed)
        app = application as MainApp
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(org.wit.reduke.R.drawable.ic_menu)
        }
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        var posts = app.posts.findAll()
        when (sortSetting) {
            "Top" -> {
                recyclerView.adapter = RedukeAdapter(sortByVotes(posts), this)
            }

            "Newest" -> {
                recyclerView.adapter = RedukeAdapter(sortByNewest(posts), this)
            }
            "Oldest" -> {
                recyclerView.adapter = RedukeAdapter(sortByOldest(posts), this)
            }
            "AlphabeticalAsc" -> {
                recyclerView.adapter = RedukeAdapter(sortByAlphabeticalAsc(posts), this)
            }
            "AlphabeticalDec" -> {
                recyclerView.adapter = RedukeAdapter(sortByAlphabeticalDec(posts), this)
            }
        }
        loadPosts()
        addPostFabButton.setOnClickListener {
            startActivityForResult<PostAddEditActivity>(0)
        }

        var mDrawerLayout: DrawerLayout = findViewById(org.wit.reduke.R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(org.wit.reduke.R.id.nav_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            var user = UserModel()
            when (menuItem?.itemId) {
                org.wit.reduke.R.id.nav_addReduke -> startActivityForResult<PostAddEditActivity>(0)

            }
            when (menuItem?.itemId) {
                org.wit.reduke.R.id.nav_Logout ->
                    alert(org.wit.reduke.R.string.logoutPrompt) {
                        yesButton {
                            finish()
                        }
                        noButton {}
                    }.show()
            }
            mDrawerLayout.closeDrawers()
            true
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(org.wit.reduke.R.menu.menu_feed, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val sortOptions = listOf("Top", "Newest", "Oldest", "Alphabetical (Ascending)", "Alphabetical (Descending)")


        var mDrawerLayout: DrawerLayout = findViewById(org.wit.reduke.R.id.drawer_layout)
        when (item?.itemId) {
            android.R.id.home -> mDrawerLayout.openDrawer(GravityCompat.START)
        }
        when (item?.itemId) {
            org.wit.reduke.R.id.item_sort_feed ->
                selector(
                        "Sort Feed By",
                        sortOptions
                ) { _, i ->
                    when {
                        sortOptions[i] == "Top" -> sortData("Top")
                        sortOptions[i] == "Newest" -> sortData("Newest")
                        sortOptions[i] == "Oldest" -> sortData("Oldest")
                        sortOptions[i] == "Alphabetical (Ascending)" -> sortData("AlphabeticalAsc")
                        sortOptions[i] == "Alphabetical (Descending)" -> sortData("AlphabeticalDec")
                    }
                }
        }
        when (item?.itemId) {
            org.wit.reduke.R.id.item_logout ->
                alert(org.wit.reduke.R.string.logoutPrompt) {
                    yesButton {
                        auth.signOut()
                        finish()
                    }
                    noButton {}
                }.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sortData(sortOption: String) {
        var posts = app.posts.findAll()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        when (sortOption) {
            "Top" -> {
                toast("Sorting By Upvotes")
                sortSetting = "Top"
                recyclerView.adapter = RedukeAdapter(sortByVotes(posts), this)
            }

            "Newest" -> {
                toast("Sorting By Newest")
                sortSetting = "Newest"
                recyclerView.adapter = RedukeAdapter(sortByNewest(posts), this)
            }
            "Oldest" -> {
                toast("Sorting By Oldest")
                sortSetting = "Oldest"
                recyclerView.adapter = RedukeAdapter(sortByOldest(posts), this)
            }
            "AlphabeticalAsc" -> {
                toast("Sorting By Alphabetical (Ascending)")
                sortSetting = "AlphabeticalAsc"
                recyclerView.adapter = RedukeAdapter(sortByAlphabeticalAsc(posts), this)
            }
            "AlphabeticalDec" -> {
                toast("Sorting By Alphabetical (Descending)")
                sortSetting = "AlphabeticalDec"
                recyclerView.adapter = RedukeAdapter(sortByAlphabeticalDec(posts), this)

            }
        }
        runLayoutAnimation(recyclerView)
    }

    override fun onPostCardClick(post: PostModel) {
        startActivityForResult(intentFor<PostAddEditActivity>().putExtra("post_edit", post), 0)
    }

    override fun onPostUpvote(post: PostModel) {
        val mypreference = RedukeSharedPreferences(this)
        val userEmail = mypreference.getCurrentUserEmail()
        if (userEmail in post.downvotedBy) {
            post.downvotedBy.remove(userEmail)
            recyclerView.adapter?.notifyDataSetChanged()
        }
        if (userEmail !in post.upvotedBy) {
            post.upvotedBy.add(userEmail)
            post.votes = post.votes + 1
            cardUpvotePost.isEnabled = false
            cardDownvotePost.isEnabled = true
            recyclerView.adapter?.notifyDataSetChanged()
        }
        info { }
    }

    override fun onPostDownvote(post: PostModel) {
        if (post.votes > 0) {
            val userEmail = RedukeSharedPreferences(this).getCurrentUserEmail()
            if (userEmail in post.upvotedBy) {
                post.upvotedBy.remove(userEmail)
                recyclerView.adapter?.notifyDataSetChanged()
            }
            if (userEmail !in post.downvotedBy) {
                post.downvotedBy.add(userEmail)
                post.votes = post.votes - 1
                cardDownvotePost.isEnabled = false
                cardUpvotePost.isEnabled = true
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        loadPosts()
        runLayoutAnimation(recyclerView)

    }

    private fun loadPosts() {
        showPosts(app.posts.findAll())
    }

    // Sorting functions
    fun sortByVotes(list: List<PostModel>): List<PostModel> {
        return list.sortedByDescending { post -> post.votes }
    }

    fun sortByNewest(list: List<PostModel>): List<PostModel> {
        return list.sortedWith(compareByDescending { LocalDateTime.parse(it.timestamp, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSSSSS")) })
    }

    fun sortByOldest(list: List<PostModel>): List<PostModel> {
        return list.sortedWith(compareBy { LocalDateTime.parse(it.timestamp, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSSSSS")) })
    }

    fun sortByAlphabeticalAsc(list: List<PostModel>): List<PostModel> {
        return list.sortedBy { post -> post.title }
    }

    fun sortByAlphabeticalDec(list: List<PostModel>): List<PostModel> {
        return list.sortedByDescending { post -> post.title }
    }

    fun showPosts(posts: List<PostModel>) {
        val mypreference = RedukeSharedPreferences(this)
        val userName = mypreference.getCurrentUserName()
        val userEmail = mypreference.getCurrentUserEmail()
        val parentView = nav_view.getHeaderView(0)
        val navHeaderUser = parentView.findViewById(org.wit.reduke.R.id.current_user_nav_header) as TextView
        val navHeaderEmail = parentView.findViewById(org.wit.reduke.R.id.current_email_nav_header) as TextView
        navHeaderUser.text = userName
        navHeaderEmail.text = userEmail
        mypreference.setCurrentRedukeCount(posts.size)
        recyclerView.adapter = RedukeAdapter(posts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        alert(org.wit.reduke.R.string.logoutPrompt) {
            yesButton {
                auth.signOut()
                finish()
                super.onBackPressed()
            }
            noButton {}
        }.show()
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView) {
        loadPosts()
        val context = recyclerView.context
        val controller = AnimationUtils.loadLayoutAnimation(context, org.wit.reduke.R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}