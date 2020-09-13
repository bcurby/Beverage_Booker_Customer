package com.beveragebooker.customer_app;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.runners.MethodSorters;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.BrowseMenu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Note the @FixMethodOrder is simply to make the tests run
 * alphabetically for neater testing thus all the tests have
 * letters in their names.
 */

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedBookDeliveryTest {

    @Rule public ActivityScenarioRule<BrowseMenu> ActivityScenarioRule
            = new ActivityScenarioRule<>(BrowseMenu.class);

    /**
     * Verifies that when the checkoutButton is clicked the next page is the
     * select order type activity and then when DeliveryButton is clicked
     * the next page had AddressGroup (all the fields that the user will
     * enter information into) and ProceedToPaymentButton (the button that
     * lead to the payment page).
     * The test also makes sure the DeliveryButton does not exist after it is
     * clicked.
     */
    @Test
    public void BookDeliveryA_SelectDelivery() {
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.PickUpButton))
                .check(matches(isDisplayed()));
        onView(withId(R.id.DeliveryButton))
                .check(matches(isDisplayed()));
        onView(withId(R.id.DeliveryButton))
                .perform(click());
        onView(withId(R.id.DeliveryButton))
                .check(doesNotExist());
        onView(withId(R.id.ProceedToPaymentButton))
                .check(matches(isDisplayed()));
    }

    /**
     * This test does the same as above however this will click pick up
     * instead of delivery and make sure that payment activity is shown.
     */
    @Test
    public void BookDeliveryB_SelectPickUp() {
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.PickUpButton))
                .perform(click());
        onView(withId(R.id.PickUpButton))
                .check(doesNotExist());
        onView(withId(R.id.creditCardNumberLayout))
                .check(matches(isDisplayed()));
        onView(withId(R.id.creditCardCVVLayout))
                .check(matches(isDisplayed()));
        onView(withId(R.id.creditCardExpiryLayout))
                .check(matches(isDisplayed()));
        onView(withId(R.id.openPlaceOrderButton))
                .check(matches(isDisplayed()));
    }

    /**
     * Tests when all but street number a filled out that the program does not
     * go to the next page - this is asserted by checking and making sure the
     * components of payment activity are not showing.
     */
    @Test
    public void BookDeliveryC_FillOutInformation_ErrorAtNumber() {
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.DeliveryButton))
                .perform(click());
        //onView(withId(R.id.editTextStreetNumber))
          //      .perform(typeText("")); //This is where the error is
        onView(withId(R.id.editTextStreetName))
                .perform(typeText("StreetName"), closeSoftKeyboard());
        onView(withId(R.id.ProceedToPaymentButton))
                .perform(click());
       // onView(withId(R.id.editTextStreetNumber))
          //      .check(matches(hasErrorText("Street number field must contain a street number")));
        onView(withId(R.id.creditCardNumberLayout))
                .check(doesNotExist());
        onView(withId(R.id.creditCardCVVLayout))
                .check(doesNotExist());
        onView(withId(R.id.creditCardExpiryLayout))
                .check(doesNotExist());
        onView(withId(R.id.openPlaceOrderButton))
                .check(doesNotExist());
    }

    /**
     * Does the same as above - but the error is at streetName now
     */
    @Test
    public void BookDeliveryD_FillOutInformation_ErrorAtName() {
        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("banana"))))
                .perform(click());
        onView(withId(R.id.viewCart))
                .perform(click());
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.DeliveryButton))
                .perform(click());
       // onView(withId(R.id.editTextStreetNumber))
           //     .perform(typeText("123"));
        onView(withId(R.id.editTextStreetName))
                .perform(typeText(""), closeSoftKeyboard()); //This is where the error is
        onView(withId(R.id.ProceedToPaymentButton))
                .perform(click());
        onView(withId(R.id.editTextStreetName))
                .check(matches(hasErrorText("Street name field must contain a street name")));
        onView(withId(R.id.creditCardNumberLayout))
                .check(doesNotExist());
        onView(withId(R.id.creditCardCVVLayout))
                .check(doesNotExist());
        onView(withId(R.id.creditCardExpiryLayout))
                .check(doesNotExist());
        onView(withId(R.id.openPlaceOrderButton))
                .check(doesNotExist());
    }

}
