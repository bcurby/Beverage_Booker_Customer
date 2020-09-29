package com.beveragebooker.customer_app.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FillCartPlaceOrderIntegrationTest {

    @Rule
    public ActivityScenarioRule<PrimaryMenu> mActivityTestRule = new ActivityScenarioRule<>(PrimaryMenu.class);

    /**
     * Clicks the Drinks Menu button and then adds a 'Long Black' to Cart with:
     * size = medium
     * sugar = default
     * decaf = checked
     * quantity = 1
     * comment = thanks
     **/

    @Test
    public void a_IsFillCartSuccessful() throws InterruptedException {

        onView(withId(R.id.drinkMenuButton))
                .perform(click());
        Thread.sleep(500);

        //Select 'Long Black' from drink menu
        Thread.sleep(1000);
        onView(AllOf.allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Long Black"))))
                .perform(click());

        //Select medium size drink
        Thread.sleep(500);
        onView(withId(R.id.mediumSize))
                .perform(click());
        Thread.sleep(500);

        //Select decaf
        Thread.sleep(500);
        onView(withId(R.id.decafCheck))
                .perform(click());
        Thread.sleep(500);

        //Add comment
        onView(withId(R.id.editTextComment))
                .perform(typeText("thanks"), closeSoftKeyboard());
        Thread.sleep(500);

        //Add to cart
        onView(withId(R.id.addToCartButton))
                .perform(click());
        Thread.sleep(500);

        //Go to cart
        Thread.sleep(1000);
        onView(withId(R.id.cartButton))
                .perform(click());
        Thread.sleep(500);

        //Check item is in cart
        Thread.sleep(2000);
        onView(AllOf.allOf(withId(R.id.cartItemName), hasSibling(withText("Long Black"))))
                .check(matches(isDisplayed()));
    }

    /** After adding the item to cart this test then goes through the input process for Checkout.
     * Card Number: 4242 4242 4242 4242
     * Expiry: 12/21
     * CVC: 123
     * Postcode: 2345
     */
    @Test
    public void b_IsPlaceOrderSuccessful() throws InterruptedException {

        //Go to cart
        Thread.sleep(1000);
        onView(withId(R.id.cartButton))
                .perform(click());
        Thread.sleep(500);

        //Go to checkout
        Thread.sleep(1000);
        onView(withId(R.id.checkoutButton))
                .perform(click());
        Thread.sleep(500);

        //Select Pick Up
        Thread.sleep(1000);
        onView(withId(R.id.PickUpButton))
                .perform(click());
        Thread.sleep(500);

        Thread.sleep(500);
        ViewInteraction cardNumberEditText = onView(
                allOf(withId(R.id.card_number_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.card_number_text_input_layout), withContentDescription("Card number")),
                                        0),
                                0),
                        isDisplayed()));
        cardNumberEditText.perform(replaceText("4242424242424242") ,closeSoftKeyboard());

        Thread.sleep(500);
        ViewInteraction expiryDateEditText = onView(
                allOf(withId(R.id.expiry_date_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.expiry_date_text_input_layout), withContentDescription("Expiration date")),
                                        0),
                                0),
                        isDisplayed()));
        expiryDateEditText.perform(replaceText("12/21"), closeSoftKeyboard());

        Thread.sleep(500);
        ViewInteraction cvcEditText = onView(
                allOf(withId(R.id.cvc_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.cvc_text_input_layout), withContentDescription("CVC")),
                                        0),
                                0),
                        isDisplayed()));
        cvcEditText.perform(replaceText("123"), closeSoftKeyboard());

        Thread.sleep(500);
        ViewInteraction postalCodeEditText = onView(
                allOf(withId(R.id.postal_code_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.postal_code_text_input_layout), withContentDescription("Postal code")),
                                        0),
                                0),
                        isDisplayed()));
        postalCodeEditText.perform(replaceText("2345"), closeSoftKeyboard());

        //Press Place Order button
        Thread.sleep(1000);
        onView(withId(R.id.placeOrderButton))
                .perform(click());

        //Order Confirmation
        Thread.sleep(5000);
        onView(withId(R.id.textViewOrderConfirm))
                .check(matches(isDisplayed()));
        
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
