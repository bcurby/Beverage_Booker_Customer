package com.beveragebooker.customer_app.activities;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedLoginTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * These tests work in conjunction with the Create User instrumented test. The user, 'Jane Doe'
     * is created by running that instrumented test, and then this suite of tests checks different
     * scenarios before successfully logging that user in.
     */

    /**
     * Verifies that all the input fields of the Main Activity are displaying correctly
     */
    @Test
    public void loginTest_CheckFields() {

        onView(withId(R.id.loginText))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextEmailLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextPasswordLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.textViewRegister))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));
    }

    /**
     * Verifies that when a user tries to log in with an empty email field the login is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void loginTest_EmptyEmail() {

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText(""));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.buttonLogin))
                .perform(click());

    onView(withId(R.id.editTextEmailLogin))
        .check(matches(hasErrorText("Email is required")));
    }

    /**
     * Verifies that when a user tries to log in with an empty password field the login is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void loginTest_EmptyPassword() {

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.buttonLogin))
                .perform(click());

        onView(withId(R.id.editTextPasswordLogin))
                .check(matches(hasErrorText("Password required")));
    }

    /**
     * Verifies that when a user tries to log in with an incorrectly formatted email the login is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void loginTest_IncorrectEmailFormat() {

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe.com"));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.buttonLogin))
                .perform(click());

        onView(withId(R.id.editTextEmailLogin))
                .check(matches(hasErrorText("Please enter a valid email")));
    }

    /**
     * Verifies that when a user fills all fields correctly the login is successful.
     */
    @Test
    public void loginTest_SuccessfulLogin() {

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.buttonLogin))
                .perform(click());
    }
}
