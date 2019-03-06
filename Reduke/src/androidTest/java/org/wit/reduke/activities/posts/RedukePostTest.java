package org.wit.reduke.activities.posts;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;
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
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class RedukePostTest {

    // ALPHABET for random data generation
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    // Creating instance of
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
    private Instrumentation.ActivityMonitor postActivityMonitor = getInstrumentation().addMonitor(TextPostActivity.class.getName(), null, false);

    // Setup the EspressoIdlingResource to allow waiting on network processes like login and register.
    @Before
    public void setUp() {
        lActivity = mActivityTestRule.getActivity();
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

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

        // Check register button exists.
        View navToRegisterButton = lActivity.findViewById(R.id.navToRegisterButton);
        assertNotNull(navToRegisterButton);
        // Click the register button.
        onView(withId(R.id.navToRegisterButton)).perform(click());
        // Check we are at the register screen.
        Activity registerActivity = getInstrumentation().waitForMonitorWithTimeout(registerActivityMonitor, 5000);
        assertNotNull(registerActivity);
        // Signing up.
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

        // Click the add post FAB button.
        Espresso.onView(withId(R.id.addPostFabButton))
                .perform(ViewActions.click());

        // We should be brought to the add post activity.
        Activity postActivity = getInstrumentation().waitForMonitorWithTimeout(postActivityMonitor, 5000);
        assertNotNull(postActivity);

        // Enter the posts title.
        Espresso.onView((withId(R.id.textPostTitleField)))
                .perform(ViewActions.typeText(str));

        Espresso.closeSoftKeyboard();

        // Enter the posts description.
        String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In interdum mollis felis, in consequat nibh viverra sed.";
        Espresso.onView((withId(R.id.textPostDescriptionField)))
                .perform(ViewActions.typeText(lorem));

        Espresso.closeSoftKeyboard();

        // Choose the Android sub reddit from the spinner.
        onView(withId(R.id.textPostSubredditSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Android"))).perform(click());

        // Create the post
        Espresso.onView(withId(R.id.addTextPostBtn))
                .perform(ViewActions.click());

        // We should be brought back to the feed activity.
        assertNotNull(feedActivity);

        // Check the post we just made exists.
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText(str))));

        // Click the first item in the feed.
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Strings to edit the title and description.
        String strEdit = str + " edit";
        String loremEdit = str + " edit";

        // Edit the title.
        Espresso.onView((withId(R.id.textPostTitleField)))
                .perform(ViewActions.replaceText(strEdit));

        Espresso.closeSoftKeyboard();

        // Edit the description.
        Espresso.onView((withId(R.id.textPostDescriptionField)))
                .perform(ViewActions.replaceText(loremEdit));

        Espresso.closeSoftKeyboard();

        // Press the save button
        Espresso.onView(withId(R.id.addTextPostBtn))
                .perform(ViewActions.click());

        // We should have been brought back to the feed activity.
        assertNotNull(feedActivity);

        // Check the post we just edited was edited.
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText(strEdit))));

        // Check that upvotes are zero
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText("0 points"))));

        // Click the upvote button
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.cardUpvotePost)));

        // Check that upvotes are zero
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText("1 points"))));

        // Click the downvote button
        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.cardDownvotePost)));

        // Check that downvote are zero
        onView(withId(R.id.recyclerView))
                .check(matches(hasDescendant(withText("0 points"))));

        // Click the first item in the feed again.
        onView(withId(R.id.recyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Press the delete button to delete the post.
        Espresso.onView(withId(R.id.deleteTextPostBtn))
                .perform(ViewActions.click());

        // Press ok on the pop up prompt
        onView(withId(android.R.id.button1)).perform((click()));

        // We should have been brought back to the feed activity.
        assertNotNull(feedActivity);

        // Check the post we just made doesn't exists.
        onView(withId(R.id.recyclerView))
                .check(matches(not(hasDescendant(withText(strEdit)))));

    }

    @After
    public void tearDown() {
        // Tear down the EspressoIdlingResource and nullify lActivity.
        lActivity = null;
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());

    }


}

class MyViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}