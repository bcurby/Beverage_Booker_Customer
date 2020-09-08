package com.beveragebooker.customer_app.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FillCartPlaceOrderIntegrationTest {

    @Rule
    public ActivityTestRule<PrimaryMenu> mActivityTestRule = new ActivityTestRule<>(PrimaryMenu.class);

    /**
     * Clicks the Drinks Menu button and then adds a 'Long Black' to Cart with:
     * size = medium
     * sugar = sweetener
     * decaf = checked
     * quantity = 2
     * comment = thanks
     **/

    @Test
    public void isItemAddedToCartandCheckoutSuccessful() {

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.menuLink), withText("Drink"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.addToCart), withText("Add To Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.mediumSize), withText("Medium"),
                        childAtPosition(
                                allOf(withId(R.id.sizeRadioGroup),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatRadioButton2 = onView(
                allOf(withId(R.id.sweetener), withText("Sweetner"),
                        childAtPosition(
                                allOf(withId(R.id.sugarRadioGroup),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatRadioButton2.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.decafCheck), withText("Decaf"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout),
                                        2),
                                1),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.add_btn), withText("+"),
                        childAtPosition(
                                allOf(withId(R.id.layout),
                                        childAtPosition(
                                                withId(R.id.qtyButton),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextComment),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("thanks"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.addToCartButton), withText("Add to Cart"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton6.perform(click());

    /** After adding the item to cart this test then goes through the input process for Checkout.
     * Card Number: 4242 4242 4242 4242
     * Expiry: 12/21
     * CVC: 123
     * Postcode: 2345
     */

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.cartButton), withText("Cart"),
                        childAtPosition(
                                allOf(withId(R.id.menuBarInsert),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.checkoutButton), withText("Checkout"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.PickUpButton), withText("Pick Up"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction cardNumberEditText = onView(
                allOf(withId(R.id.card_number_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.card_number_text_input_layout), withContentDescription("Card number")),
                                        0),
                                0),
                        isDisplayed()));
        cardNumberEditText.perform(replaceText("4242 4242 4242 4242"), closeSoftKeyboard());

        ViewInteraction expiryDateEditText = onView(
                allOf(withId(R.id.expiry_date_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.expiry_date_text_input_layout), withContentDescription("Expiration date")),
                                        0),
                                0),
                        isDisplayed()));
        expiryDateEditText.perform(replaceText("12/21"), closeSoftKeyboard());

        ViewInteraction cvcEditText = onView(
                allOf(withId(R.id.cvc_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.cvc_text_input_layout), withContentDescription("CVC")),
                                        0),
                                0),
                        isDisplayed()));
        cvcEditText.perform(replaceText("123"), closeSoftKeyboard());

        ViewInteraction postalCodeEditText = onView(
                allOf(withId(R.id.postal_code_edit_text),
                        childAtPosition(
                                childAtPosition(
                                        allOf(withId(R.id.postal_code_text_input_layout), withContentDescription("Postal code")),
                                        0),
                                0),
                        isDisplayed()));
        postalCodeEditText.perform(replaceText("2345"), closeSoftKeyboard());
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
