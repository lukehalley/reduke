package org.wit.reduke.activities.other

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.reduke.R
import org.wit.reduke.activities.users.RedukeLoginActivity

class RedukeSplashscreen : AppCompatActivity() {
    // Create a mDelayHandler for the delay.
    private var mDelayHandler: Handler? = null

    // Create the delay value
    private val splashDelay: Long = 1000

    // Finish the splashscreen after delay is done.
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

    // Nullify the mDelayHandler for future launches.
    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }

}