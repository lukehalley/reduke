package org.wit.reduke.tools;

import android.support.test.espresso.IdlingResource;

/**
 * Contains a static reference IdlingResource, and should be available only in a mock build type.
 */
public class EspressoIdlingResource {

    private static final String RESOURCE = "GLOBAL";

    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    public static void increment() {
        mCountingIdlingResource.increment();
    }

    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    public static android.support.test.espresso.IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}