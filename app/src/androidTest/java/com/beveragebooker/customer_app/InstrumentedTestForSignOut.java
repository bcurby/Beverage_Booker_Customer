package com.beveragebooker.customer_app;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.MainActivity;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedTestForSignOut {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This first test checks the functionality of the Sign Out Button
     * on the Profile Activity Page. Similar to the Integration Test however
     * it checks for the fields that it uses before it tests the functionality
     * to makes sure the field is there.
     */
    @Test
    public void a_IsSignOutSuccessful() throws InterruptedException {
        // Login

        onView(withId(R.id.editTextEmailLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.editTextPasswordLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        // Profile Activity Sign Out
        Thread.sleep(4000);
        onView(withId(R.id.textViewWelcome))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signOutButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signOutButton))
                .perform(click());

        // Checking we are back on Login Screen
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

    }

    /**
     * This second test checks the functionality of all the buttons on the
     * menu bar and eventually signing out of the app. Similar to the Integration Test however
     * it checks for the fields that it uses before it tests the functionality
     * to makes sure the field is there.
     */
    @Test
    public void b_IsMenuBarSignOutSuccessful() throws InterruptedException {

        // Login
        Thread.sleep(2000);
        onView(withId(R.id.editTextEmailLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.editTextPasswordLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        // Account Button
        Thread.sleep(4000);
        onView(withId(R.id.textViewWelcome))
                .check(matches(isDisplayed()));

        onView(withId(R.id.accountButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.accountButton))
                .perform(click());

        // Home Button
        onView(withId(R.id.homeButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.homeButton))
                .perform(click());

        // Cart Button
        onView(withId(R.id.cartButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.cartButton))
                .perform(click());

        // Sign Out Button
        onView(withId(R.id.signOutButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);

        // Checking we are back on Login Screen
        Thread.sleep(4000);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));
    }
}
