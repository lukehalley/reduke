package org.wit.reduke.activities.users;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.wit.reduke.R;
import org.wit.reduke.activities.feed.FeedActivity;
import org.wit.reduke.tools.EspressoIdlingResource;

import java.security.SecureRandom;

import kotlin.jvm.JvmField;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;

public class RedukeFirebaseAccountTest {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Rule
    @JvmField
    // Creating rule for the RedukeLoginActivity.
    public ActivityTestRule<RedukeLoginActivity> mActivityTestRule = new ActivityTestRule<>(RedukeLoginActivity.class);
    private RedukeLoginActivity lActivity = null;
    // Creating all the ActivityMonitors we need for the test below.
    private Instrumentation.ActivityMonitor registerActivityMonitor = getInstrumentation().addMonitor(RedukeRegisterActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor loginActivityMonitor = getInstrumentation().addMonitor(RedukeLoginActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor feedActivityMonitor = getInstrumentation().addMonitor(FeedActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        lActivity = mActivityTestRule.getActivity();
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    // Setup the EspressoIdlingResource to allow waiting on network processes like login and register.
    @Test
    public void testUserSignUpAndLogin() {
        // Start creation of random data
        StringBuilder sBuild = new StringBuilder();
        for (int i = 0; i < 7; ++i) {
            sBuild.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        String str = sBuild.toString();
        // Assign random string we just created to the variables.
        String correctUsername = str;
        String correctEmail = str + "@email.com";
        String correctPassword = str + "23434";

        // Check register button exists
        View navToRegisterButton = lActivity.findViewById(R.id.navToRegisterButton);
        assertNotNull(navToRegisterButton);
        // Click the register button
        onView(withId(R.id.navToRegisterButton)).perform(click());
        // Check we are at the register screen
        Activity registerActivity = getInstrumentation().waitForMonitorWithTimeout(registerActivityMonitor, 5000);
        assertNotNull(registerActivity);
        // Signing up
        Log.e("@Test", "Performing login success test");
        Espresso.onView((withId(R.id.enteredRegisterUsername)))
                .perform(ViewActions.typeText(correctUsername));
        // Close the keyboard
        Espresso.closeSoftKeyboard();
        // Enter the email we want to sign up with.
        Espresso.onView((withId(R.id.enteredRegisterEmail)))
                .perform(ViewActions.typeText(correctEmail));

        Espresso.closeSoftKeyboard();
        // Enter the password we want to sign up with.
        Espresso.onView(withId(R.id.enteredRegisterPassword))
                .perform(ViewActions.typeText(correctPassword));

        Espresso.closeSoftKeyboard();
        // Re-enter the password we want to sign up with.
        Espresso.onView(withId(R.id.enteredRegisterPasswordConfirm))
                .perform(ViewActions.typeText(correctPassword));

        Espresso.closeSoftKeyboard();
        // Click the register button to register the user.
        Espresso.onView(withId(R.id.registerButton))
                .perform(ViewActions.click());
        // After register we should be brought back to the login activity.
        Activity loginActivity = getInstrumentation().waitForMonitorWithTimeout(loginActivityMonitor, 5000);
        assertNotNull(loginActivity);
        // Enter the email we just registered with.
        Espresso.onView((withId(R.id.enteredLoginEmail)))
                .perform(ViewActions.typeText(correctEmail));

        Espresso.closeSoftKeyboard();

        // Enter the password we just registered with.
        Espresso.onView((withId(R.id.enteredLoginPassword)))
                .perform(ViewActions.typeText(correctPassword));

        Espresso.closeSoftKeyboard();

        // Click the login button to login the user.
        Espresso.onView(withId(R.id.firebaseLoginButton))
                .perform(ViewActions.click());

        // After login we should be brought to the feed activity.
        Activity feedActivity = getInstrumentation().waitForMonitorWithTimeout(feedActivityMonitor, 5000);
        assertNotNull(feedActivity);
    }

    @After
    public void tearDown() {
        // Nullify lActivity.
        lActivity = null;
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());

    }


}