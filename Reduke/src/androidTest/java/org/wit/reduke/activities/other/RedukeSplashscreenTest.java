package org.wit.reduke.activities.other;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.wit.reduke.R;

import kotlin.jvm.JvmField;

import static junit.framework.TestCase.assertNotNull;

public class RedukeSplashscreenTest {
    // Creating rule for the RedukeSplashscreen.
    @Rule
    @JvmField
    public ActivityTestRule<RedukeSplashscreen> mActivityTestRule = new ActivityTestRule<>(RedukeSplashscreen.class);

    // Init the RedukeSplashscreen activity.
    private RedukeSplashscreen sActivity = null;

    @Before
    public void setUp() {
        // Get the splashscreen activity.
        sActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {
        // Check on launch that the splashscreen is seen with the correct image.
        View splashscreenImage = sActivity.findViewById(R.id.splashscreenImage);
        assertNotNull(splashscreenImage);
    }

    @After
    public void tearDown() {
        // Nullify the activity for teardown.
        sActivity = null;

    }
}