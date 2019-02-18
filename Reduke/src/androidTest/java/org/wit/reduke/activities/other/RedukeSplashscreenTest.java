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

    @Rule
    @JvmField
    public ActivityTestRule<RedukeSplashscreen> mActivityTestRule = new ActivityTestRule<RedukeSplashscreen>(RedukeSplashscreen.class);

    private RedukeSplashscreen sActivity = null;

    @Before
    public void setUp() throws Exception {

        sActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch() {

        View splashscreenImage = sActivity.findViewById(R.id.splashscreenImage);
        assertNotNull(splashscreenImage);

    }

    @After
    public void tearDown() {

        sActivity = null;

    }
}