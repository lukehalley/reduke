package org.wit.reduke.activities.other

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.reduke.R
import org.wit.reduke.activities.users.RedukeLoginActivity

class RedukeSplashscreen : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val splashDelay: Long = 1000 // 1 second

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivityForResult<RedukeLoginActivity>(0)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, splashDelay)

    }

    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}