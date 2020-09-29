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
import com.beveragebooker.customer_app.activities.PrimaryMenu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedBookDeliveryTest {

    @Rule public ActivityScenarioRule<PrimaryMenu> ActivityScenarioRule
            = new ActivityScenarioRule<>(PrimaryMenu.class);

    /**
     * Verifies that the fields for the delivery input page are displayed correctly, and that the
     * 'Delivery' button on the Select Order Type page is displaying and functioning as intended.
     * Starts by adding a 'Sausage Roll' to user cart.
     * The test then movies through the activities, and checks the 'Delivery' button.
     * Finally the test checks all the required fields an the 'Proceed to Payment' button are
     * displaying.
     */
    @Test
    public void a_IsFieldsForDeliverInputDisplayed() throws InterruptedException {

        onView(withId(R.id.foodMenuButton))
                .perform(click());
        Thread.sleep(500);

        onView(allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Sausage Roll"))))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.addToCartButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.cartButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.checkoutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.PickUpButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.DeliveryButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.DeliveryButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.DeliveryButton))
                .check(doesNotExist());

        onView(withId(R.id.editTextUnit))
                .check(matches(isDisplayed()));

        onView(withId(R.id.editTextStreetName))
                .check(matches(isDisplayed()));

        onView(withId(R.id.textViewCityTown))
                .check(matches(isDisplayed()));

        onView(withId(R.id.textViewPostCode))
                .check(matches(isDisplayed()));

        onView(withId(R.id.textViewDisclaimer))
                .check(matches(isDisplayed()));

        onView(withId(R.id.ProceedToPaymentButton))
                .check(matches(isDisplayed()));
        Thread.sleep(500);
    }


    /**
     * Checks that the address input page will accept an empty unit number field as intended
     */
    @Test
    public void b_IsEmptyUnitNumberAccepted() throws InterruptedException {

        onView(withId(R.id.cartButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.checkoutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.DeliveryButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextStreetName))
                .perform(typeText("17 Smith St"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.ProceedToPaymentButton))
                .perform(click());
        Thread.sleep(500);
    }


    /**
     * Checks that a valid unit number and street number/name are accepted.
     */
    @Test
    public void c_IsValidUnitNumberAndStreetNameAccepted() throws InterruptedException {

        onView(withId(R.id.cartButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.checkoutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.DeliveryButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextUnit))
                .perform(typeText("1"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextStreetName))
                .perform(typeText("17 Smith St"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.ProceedToPaymentButton))
                .perform(click());
        Thread.sleep(300);
    }


    /**
     * Checks that an invalid street number/name containing symbols is not accepted and displays
     * an error message.
     */
    @Test
    public void d_IsInValidSymbolsStreetNameAccepted() throws InterruptedException {

        onView(withId(R.id.cartButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.checkoutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.DeliveryButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextUnit))
                .perform(typeText("1"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextStreetName))
                .perform(typeText("?!#$"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.ProceedToPaymentButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextStreetName))
                .check(matches(hasErrorText("Street name field cant contain any symbols")));
        Thread.sleep(1000);
    }


    /**
     * Checks that an empty street number/name is not accepted and displays
     * an error message.
     */
    @Test
    public void e_IsEmptyStreetNameAccepted() throws InterruptedException {

        onView(withId(R.id.cartButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.checkoutButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.DeliveryButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextUnit))
                .perform(typeText("1"), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.editTextStreetName))
                .perform(typeText(""), closeSoftKeyboard());
        Thread.sleep(500);

        onView(withId(R.id.ProceedToPaymentButton))
                .perform(click());
        Thread.sleep(500);

        onView(withId(R.id.editTextStreetName))
                .check(matches(hasErrorText("Street name field must contain a street name")));
        Thread.sleep(1000);
    }
}
