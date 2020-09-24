package com.beveragebooker.customer_app.activities;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedEmptyCartTest {
    @Rule
    public ActivityScenarioRule<BrowseMenu> mActivityScenarioRule
            = new ActivityScenarioRule<>(BrowseMenu.class);

    /**
     * Adds a 'Long Black' to cart, then moves to the cart screen and clicks the 'Empty Cart' button.
     * Then verifies that the menu item is no longer displayed in the cart.
     */
    @Test
    public void isCartEmptiedSuccessfully() {

        onView(withId(R.id.homeButton))
                .perform(click());

        //onView(withId(R.id.menuLink))
              //  .perform(click());

        onView(AllOf.allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Long Black"))))
                .perform(click());

        ViewInteraction appCompatEditText4 = onView(
                Matchers.allOf(withId(R.id.editTextComment),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("thanks"), closeSoftKeyboard());

        /*ViewInteraction appCompatCheckBox = onView(
                Matchers.allOf(withId(R.id.decafCheck), withText("Decaf"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.linearLayout),
                                        2),
                                1),
                        isDisplayed()));
        appCompatCheckBox.perform(click());*/

        ViewInteraction appCompatRadioButton = onView(
                Matchers.allOf(withId(R.id.mediumSize), withText("Medium"),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.sizeRadioGroup),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        ViewInteraction appCompatButton5 = onView(
                Matchers.allOf(withId(R.id.add_btn), withText("+"),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.layout),
                                        childAtPosition(
                                                withId(R.id.qtyButton),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton5.perform(click());

        onView(withId(R.id.addToCartButton))
                .perform(click());

        onView(withId(R.id.cartButton))
                .perform(click());

        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("Long Black"))))
                .check(matches(isDisplayed()));

        onView(withId(R.id.emptyCartButton))
                .perform(click());

        onView(allOf(withId(R.id.cartItemName), hasSibling(withText("Long Black"))))
                .check(doesNotExist());
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