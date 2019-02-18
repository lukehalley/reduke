package org.wit.reduke.activities.users;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.wit.reduke.R;

import kotlin.jvm.JvmField;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

public class RedukeUserAccountTest {

    @Rule
    @JvmField
    public ActivityTestRule<RedukeLoginActivity> mActivityTestRule = new ActivityTestRule<>(RedukeLoginActivity.class);

    private RedukeLoginActivity lActivity = null;

    Instrumentation.ActivityMonitor registerActivityMonitor = getInstrumentation().addMonitor(RedukeRegisterActivity.class.getName(), null, false);
    Instrumentation.ActivityMonitor loginActivityMonitor = getInstrumentation().addMonitor(RedukeLoginActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        lActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testUserSignUpAndLogin() {

        // Create random data with MockNeat
//        MockNeat mock = MockNeat.threadLocal();
        String correctUsername = "weirdemail";
        String correctEmail = "weirdemail@email.com";
        String correctPassword = "djfdjkfskjdfjsdf";

        Log.e("@Test", "Using Email: " + correctEmail + " and Password: " + correctPassword);

        // Check register button exists
        View navToRegisterButton = lActivity.findViewById(R.id.navToRegisterButton);
        assertNotNull(navToRegisterButton);
        // Click it
        onView(withId(R.id.navToRegisterButton)).perform(click());

        Activity registerActivity = getInstrumentation().waitForMonitorWithTimeout(registerActivityMonitor, 5000);
        assertNotNull(registerActivity);

        Log.e("@Test", "Performing login success test");
        Espresso.onView((withId(R.id.enteredRegisterUsername)))
                .perform(ViewActions.typeText(correctUsername));

        Espresso.closeSoftKeyboard();

        Espresso.onView((withId(R.id.enteredRegisterEmail)))
                .perform(ViewActions.typeText(correctEmail));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.enteredRegisterPassword))
                .perform(ViewActions.typeText(correctPassword));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.enteredRegisterPasswordConfirm))
                .perform(ViewActions.typeText(correctPassword));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.registerButton))
                .perform(ViewActions.click());

        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(loginActivityMonitor, 5000);
        assertNotNull(loginActivity);
    }

    @After
    public void tearDown() {

        lActivity = null;

    }
}