package com.beveragebooker.customer_app.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewAccountIntegrationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void a_IsFirstNameChangedWhenEmpty() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userFirstName))
                .perform(replaceText(""), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userFirstName))
                .check(matches(hasErrorText("First Name required")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void b_IsFirstNameChangedSuccessfullyWithValidData() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userFirstName))
                .perform(replaceText("Sergio"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userFirstName))
                .check(matches(withText("Sergio")));
        Thread.sleep(500);

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void c_IsLastNameChangedWhenEmpty() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userLastName))
                .perform(replaceText(""), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userLastName))
                .check(matches(hasErrorText("Last Name required")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void d_IsLastNameChangedSuccessfullyWithValidData() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userLastName))
                .perform(replaceText("Reguilon"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userLastName))
                .check(matches(withText("Reguilon")));
        Thread.sleep(500);

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void e_IsEmailChangedWhenEmpty() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userEmail))
                .perform(replaceText(""), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userEmail))
                .check(matches(hasErrorText("Email is required")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void f_IsEmailChangedWhenInvalidFormat() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userEmail))
                .perform(replaceText("sergio.gmail"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userEmail))
                .check(matches(hasErrorText("Enter a valid email")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void g_IsEmailChangedSuccessfully() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("janedoe@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userEmail))
                .perform(replaceText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userEmail))
                .check(matches(withText("sergio@gmail.com")));
        Thread.sleep(500);

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }


    @Test
    public void h_IsPhoneNumberChangedWhenEmpty() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userPhoneNum))
                .perform(replaceText(""), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userPhoneNum))
                .check(matches(hasErrorText("Mobile Number required")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void i_IsPhoneNumberChangedWhenNotTenDigits() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userPhoneNum))
                .perform(replaceText("123456789"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userPhoneNum))
                .check(matches(hasErrorText("Mobile Number must be 10 digits")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void j_IsPhoneNumberChangedSuccessfully() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.editButton))
                .perform(click());

        onView(withId(R.id.userPhoneNum))
                .perform(replaceText("0400230230"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.saveButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.userPhoneNum))
                .check(matches(withText("0400230230")));
        Thread.sleep(500);

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);
    }

    @Test
    public void k_IsPasswordChangedWhenFirstPasswordFieldEmpty() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.userPassword))
                .perform(click());

        onView(withId(R.id.password1))
                .perform(replaceText(""), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password2))
                .perform(replaceText("123456789"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password1))
                .check(matches(withText("")));
        Thread.sleep(500);

        onView(withId(R.id.password2))
                .check(matches(withText("123456789")));
        Thread.sleep(500);

        onView(withId(R.id.save))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.password1))
                .check(matches(hasErrorText("Password required")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

    }

    @Test
    public void l_IsPasswordChangedWhenSecondPasswordFieldEmpty() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.userPassword))
                .perform(click());

        onView(withId(R.id.password1))
                .perform(replaceText("123456789"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password2))
                .perform(replaceText(""), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password1))
                .check(matches(withText("123456789")));
        Thread.sleep(500);

        onView(withId(R.id.password2))
                .check(matches(withText("")));
        Thread.sleep(500);

        onView(withId(R.id.save))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.password2))
                .check(matches(hasErrorText("Password required")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

    }

    @Test
    public void m_IsPasswordChangedWhenPasswordFieldsDoNotMatch() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.userPassword))
                .perform(click());

        onView(withId(R.id.password1))
                .perform(replaceText("123456789"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password2))
                .perform(replaceText("987654321"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password1))
                .check(matches(withText("123456789")));
        Thread.sleep(500);

        onView(withId(R.id.password2))
                .check(matches(withText("987654321")));
        Thread.sleep(500);

        onView(withId(R.id.save))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.password1))
                .check(matches(hasErrorText("Passwords do not match")));

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextPasswordLogin))
                .perform(replaceText("987654321"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

    }

    @Test
    public void n_IsPasswordChangedSuccessfully() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.userPassword))
                .perform(click());

        onView(withId(R.id.password1))
                .perform(replaceText("123456789"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password2))
                .perform(replaceText("123456789"), closeSoftKeyboard());
        Thread.sleep(1000);

        onView(withId(R.id.password1))
                .check(matches(withText("123456789")));
        Thread.sleep(500);

        onView(withId(R.id.save))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.signOutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .check(matches(isDisplayed()));

        Thread.sleep(500);
        onView(withId(R.id.signOutButton))
                .perform(click());

    }

    @Test
    public void o_IsAccountDeletedSuccessfully() throws InterruptedException {

        //Login
        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        //Profile Landing page
        Thread.sleep(4000);
        onView(withId(R.id.continueButton))
                .perform(click());

        //Primary Menu
        Thread.sleep(500);
        onView(withId(R.id.accountButton))
                .perform(click());

        //Account page
        Thread.sleep(500);
        onView(withId(R.id.deleteAccount))
                .perform(click());

        //Delete Account confirmation
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
        onView(withText("Confirm"))
                .perform(click());

        //Check that app is now on Login page
        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextEmailLogin))
                .perform(typeText("sergio@gmail.com"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextPasswordLogin))
                .perform(typeText("123456789"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.buttonLogin))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(500);
        onView(withId(R.id.buttonLogin))
                .check(matches(isDisplayed()));

    }
}
