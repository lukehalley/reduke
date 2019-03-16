package org.wit.reduke.tools;

// EspressoIdlingResource allows testing of asynchronous functions in the
// Reduke app such as Login and Register.

public class EspressoIdlingResource {

    // Make EspressoIdlingResource globally available.
    private static final String RESOURCE = "GLOBAL";

    // Create an instance of EspressoIdlingResource.
    private static SimpleCountingIdlingResource mCountingIdlingResource =
            new SimpleCountingIdlingResource(RESOURCE);

    // This function is called when we need to pause the tests and
    // wait for asynchronous function to finish.
    public static void increment() {
        mCountingIdlingResource.increment();
    }

    // This function is called when we need to un-pause the tests and
    // allow the testing to continue.
    public static void decrement() {
        mCountingIdlingResource.decrement();
    }

    // Function to allow class to access the IdlingResource.
    public static android.support.test.espresso.IdlingResource getIdlingResource() {
        return mCountingIdlingResource;
    }
}