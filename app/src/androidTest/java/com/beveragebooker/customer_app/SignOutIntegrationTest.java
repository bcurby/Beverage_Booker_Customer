package com.beveragebooker.customer_app;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignOutIntegrationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This first test checks the functionality of the Sign Out Button
     * on the Profile Activity Page.
     */
    @Test
    public void isLoginSignOutSuccessful() throws InterruptedException {

        // Login
        Thread.sleep(500);
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());

        Thread.sleep(500);
        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .perform(click());

        // Profile Activity Sign Out
        Thread.sleep(1000);
        onView(withId(R.id.textViewWelcome))
                .check(matches(isDisplayed()));

        Thread.sleep(500);
        onView(withId(R.id.signOutButton))
                .perform(click());

        // Checking we are back on Login Screen
        Thread.sleep(1000);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

        // Login
        Thread.sleep(500);
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());

        Thread.sleep(500);
        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .perform(click());

        // Account Button
        Thread.sleep(1000);
        onView(withId(R.id.textViewWelcome))
                .check(matches(isDisplayed()));

        onView(withId(R.id.accountButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.accountButton))
                .perform(click());

        // Home Button
        Thread.sleep(1000);
        onView(withId(R.id.homeButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.homeButton))
                .perform(click());

        // Cart Button
        Thread.sleep(1000);
        onView(withId(R.id.cartButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.cartButton))
                .perform(click());

        // Sign Out Button
        Thread.sleep(1000);
        onView(withId(R.id.signOutButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.signOutButton))
                .perform(click());
        
        // Checking we are back on Login Screen
        Thread.sleep(1000);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));
    }
}
