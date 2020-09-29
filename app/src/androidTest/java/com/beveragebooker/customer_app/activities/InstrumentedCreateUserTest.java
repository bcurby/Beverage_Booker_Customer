package com.beveragebooker.customer_app.activities;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

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
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedCreateUserTest {

    @Rule
    public ActivityScenarioRule<CreateUserActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(CreateUserActivity.class);

    /**
     * Verifies that all the input fields of the Create User Activity are displaying correctly
     */
    @Test
    public void a_IsCreateUserFieldsDisplayed() throws InterruptedException {

        onView(withId(R.id.relativeLayout5))
                .check(matches(isDisplayed()));
        Thread.sleep(500);

        onView(withId(R.id.editTextEmail))
                .check(matches(isDisplayed()));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .check(matches(isDisplayed()));
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .check(matches(isDisplayed()));
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .check(matches(isDisplayed()));
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .check(matches(isDisplayed()));
        Thread.sleep(500);

        onView(withId(R.id.textViewLogin))
                .check(matches(isDisplayed()));
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .check(matches(isDisplayed()));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user tries to sign up with an empty email field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void b_IsUserCreatedWithEmailEmpty() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText(""));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextEmail))
                .check(matches(hasErrorText("Email is required")));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user tries to sign up with an empty password field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void c_IsUserCreatedWithPasswordEmpty() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText(""), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .check(matches(hasErrorText("Password required")));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user tries to sign up with an empty first name field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void d_IsUserCreatedWithFirstNameEmpty() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText(""), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .check(matches(hasErrorText("First Name required")));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user tries to sign up with an empty last name field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void e_IsUserCreatedWithLastNameEmpty() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText(""), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .check(matches(hasErrorText("Last Name required")));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user tries to sign up with an empty phone field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void f_IsUserCreatedWithPhoneEmpty() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText(""), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .check(matches(hasErrorText("Mobile Number required")));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user tries to sign up with an incorrectly formatted email the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void g_IsUserCreatedWithEmailIncorrectFormat() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe.com"));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextEmail))
                .check(matches(hasErrorText("Enter a valid email")));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user tries to sign up with a password less than 6 characters the sign up
     * is unsuccessful and the correct error message is displayed.
     */
    @Test
    public void h_IsUserCreatedWithPasswordNotRequiredLength() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText("12345"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .check(matches(hasErrorText("Password should be at least 6 characters long")));
        Thread.sleep(500);
    }

    /**
     * Verifies that when a user fills all fields correctly the sign up is successful.
     */
    @Test
    public void i_IsUserCreatedWithValidData() throws InterruptedException {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));
        Thread.sleep(500);

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonSignUp))
                .perform(click());
        Thread.sleep(500);
    }
}


