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
public class InstrumentedCreateUserTest {

    @Rule
    public ActivityScenarioRule<CreateUserActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(CreateUserActivity.class);

    /**
     * Verifies that all the input fields of the Create User Activity are displaying correctly
     */
    @Test
    public void createUserTest_checkFields() {

        onView(withId(R.id.linearLayout))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextEmail))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextPassword))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextFirstName))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextLastName))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextPhone))
                .check(matches(isDisplayed()));

        onView(withId(R.id.textViewLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.buttonSignUp))
                .check(matches(isDisplayed()));
    }

    /**
     * Verifies that when a user tries to sign up with an empty email field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void createUserTest_EmptyEmail() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText(""));

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());

        onView(withId(R.id.editTextEmail))
                .check(matches(hasErrorText("Email is required")));
    }

    /**
     * Verifies that when a user tries to sign up with an empty password field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void createUserTest_EmptyPassword() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPassword))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());

        onView(withId(R.id.editTextPassword))
                .check(matches(hasErrorText("Password required")));
    }

    /**
     * Verifies that when a user tries to sign up with an empty first name field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void createUserTest_EmptyFirstName() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());

        onView(withId(R.id.editTextFirstName))
                .check(matches(hasErrorText("First Name required")));
    }

    /**
     * Verifies that when a user tries to sign up with an empty last name field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void createUserTest_EmptyLastName() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());

        onView(withId(R.id.editTextLastName))
                .check(matches(hasErrorText("Last Name required")));
    }

    /**
     * Verifies that when a user tries to sign up with an empty phone field the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void createUserTest_EmptyPhone() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());

        onView(withId(R.id.editTextPhone))
                .check(matches(hasErrorText("Phone Number required")));
    }

    /**
     * Verifies that when a user tries to sign up with an incorrectly formatted email the sign up is
     * unsuccessful and the correct error message is displayed.
     */
    @Test
    public void createUserTest_IncorrectEmailFormat() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe.com"));

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());

        onView(withId(R.id.editTextEmail))
                .check(matches(hasErrorText("Enter a valid email")));
    }

    /**
     * Verifies that when a user tries to sign up with a password less than 6 characters the sign up
     * is unsuccessful and the correct error message is displayed.
     */
    @Test
    public void createUserTest_PasswordUnderRequiredLength() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPassword))
                .perform(typeText("12345"), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());

        onView(withId(R.id.editTextPassword))
                .check(matches(hasErrorText("Password should be at least 6 characters long")));
    }

    /**
     * Verifies that when a user fills all fields correctly the sign up is successful.
     */
    @Test
    public void createUserTest_SuccessfulAccountCreation() {

        onView(withId(R.id.editTextEmail))
                .perform(typeText("janedoe@gmail.com"));

        onView(withId(R.id.editTextPassword))
                .perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.editTextFirstName))
                .perform(typeText("Jane"), closeSoftKeyboard());

        onView(withId(R.id.editTextLastName))
                .perform(typeText("Doe"), closeSoftKeyboard());

        onView(withId(R.id.editTextPhone))
                .perform(typeText("0402555444"), closeSoftKeyboard());

        onView(withId(R.id.buttonSignUp))
                .perform(click());
    }
}


