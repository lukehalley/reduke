package org.wit.reduke.activities.users;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.wit.reduke.R;
import org.wit.reduke.activities.feed.FeedActivity;
import org.wit.reduke.tools.EspressoIdlingResource;

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
    Instrumentation.ActivityMonitor feedActivityMonitor = getInstrumentation().addMonitor(FeedActivity.class.getName(), null, false);

    @Before
    public void setUp() {
        lActivity = mActivityTestRule.getActivity();
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void testUserSignUpAndLogin() {

        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();

        String str = randomStringGenerator.generate(7);
        String correctUsername = "reedfgdfgf";
        String correctEmail = "reedfgdfgf@email.com";
        String correctPassword = "reedfgdfgfefrrf";


        Log.e("@Test", "Using Email: " + correctEmail + " and Password: " + correctPassword);

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

        Activity feedActivity = getInstrumentation().waitForMonitorWithTimeout(feedActivityMonitor, 5000);
        assertNotNull(feedActivity);
    }

    @After
    public void tearDown() {

        lActivity = null;
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());

    }


}