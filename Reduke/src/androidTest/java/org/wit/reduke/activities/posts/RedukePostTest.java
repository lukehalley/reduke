package org.wit.reduke.activities.posts;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.wit.reduke.R;
import org.wit.reduke.activities.feed.FeedActivity;
import org.wit.reduke.activities.users.RedukeLoginActivity;
import org.wit.reduke.activities.users.RedukeRegisterActivity;
import org.wit.reduke.tools.EspressoIdlingResource;

import java.security.SecureRandom;

import kotlin.jvm.JvmField;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.core.IsNot.not;

public class RedukePostTest {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Rule
    @JvmField
    public ActivityTestRule<RedukeLoginActivity> mActivityTestRule = new ActivityTestRule<>(RedukeLoginActivity.class);

    private RedukeLoginActivity lActivity = null;
    private String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In interdum mollis felis, in consequat nibh viverra sed.";
    private Instrumentation.ActivityMonitor registerActivityMonitor = getInstrumentation().addMonitor(RedukeRegisterActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor loginActivityMonitor = getInstrumentation().addMonitor(RedukeLoginActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor feedActivityMonitor = getInstrumentation().addMonitor(FeedActivity.class.getName(), null, false);
    private Instrumentation.ActivityMonitor postActivityMonitor = getInstrumentation().addMonitor(PostAddEditActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        lActivity = mActivityTestRule.getActivity();
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void testUserSignUpAndLogin() {

        StringBuilder sBuild = new StringBuilder();
        for (int i = 0; i < 7; ++i) {
            sBuild.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        String str = sBuild.toString();

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

        Espresso.onView((withId(R.id.enteredLoginEmail)))
                .perform(ViewActions.typeText(correctEmail));

        Espresso.closeSoftKeyboard();

        Espresso.onView((withId(R.id.enteredLoginPassword)))
                .perform(ViewActions.typeText(correctPassword));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.loginButton))
                .perform(ViewActions.click());

        Activity feedActivity = getInstrumentation().waitForMonitorWithTimeout(feedActivityMonitor, 5000);
        assertNotNull(feedActivity);

        Espresso.onView(withId(R.id.addPostFabButton))
                .perform(ViewActions.click());

        Activity postActivity = getInstrumentation().waitForMonitorWithTimeout(postActivityMonitor, 5000);
        assertNotNull(postActivity);

        Espresso.onView((withId(R.id.postTitleField)))
                .perform(ViewActions.typeText(str));

        Espresso.closeSoftKeyboard();

        Espresso.onView((withId(R.id.postDescriptionField)))
                .perform(ViewActions.typeText(lorem));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.addPostBtn))
                .perform(ViewActions.click());

        assertNotNull(feedActivity);

        // Check the post we just made exists.
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText(str))));

        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        String strEdit = str + " edit";
        String loremEdit = str + " edit";

        Espresso.onView((withId(R.id.postTitleField)))
                .perform(ViewActions.replaceText(strEdit));

        Espresso.closeSoftKeyboard();

        Espresso.onView((withId(R.id.postDescriptionField)))
                .perform(ViewActions.replaceText(loremEdit));

        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.addPostBtn))
                .perform(ViewActions.click());

        assertNotNull(feedActivity);

        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText(strEdit))));

        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Espresso.onView(withId(R.id.deletePostBtn))
                .perform(ViewActions.click());

        onView(withId(android.R.id.button1)).perform((click()));

        assertNotNull(feedActivity);

        onView(withId(R.id.recyclerView))
                .check(matches(not(hasDescendant(withText(strEdit)))));

    }

    @After
    public void tearDown() {

        lActivity = null;
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());

    }


}