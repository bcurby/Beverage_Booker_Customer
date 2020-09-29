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
    public ActivityScenarioRule<PrimaryMenu> mActivityScenarioRule = new ActivityScenarioRule<>(PrimaryMenu.class);

    /**
     * Adds a 'Long Black' to cart, then moves to the cart screen and clicks the 'Empty Cart' button.
     * Then verifies that the menu item is no longer displayed in the cart.
     */
    @Test
    public void isCartEmptiedSuccessfully() throws InterruptedException {

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

        Thread.sleep(500);
        onView(withId(R.id.emptyCartButton))
                .perform(click());

        Thread.sleep(1500);
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