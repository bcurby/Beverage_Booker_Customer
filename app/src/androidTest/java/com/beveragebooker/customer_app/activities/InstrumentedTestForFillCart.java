package com.beveragebooker.customer_app.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.beveragebooker.customer_app.R;

import org.hamcrest.BaseMatcher;
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
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedTestForFillCart {

    @Rule
    public ActivityScenarioRule<PrimaryMenu> mActivityTestRule = new ActivityScenarioRule<>(PrimaryMenu.class);

    /**
     * Instrumented test that clicks the Drinks Menu button and then adds a 'Long Black' to Cart with:
     * size = medium
     * sugar = default (yes)
     * decaf = checked
     * quantity = 1
     * comment = thanks
     **/

    @Test
    public void a_IsItemAddedToCart() throws InterruptedException {

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

    /**
     * Instrumented Test that tries to add a Blueberry Muffin to cart, but the item is sold out.
     * After clicking Add to Cart button it checks that the next activity is not opened,
     * confirming the button is disabled
     */
    @Test
    public void b_IsSoldOutItemAddedToCart() throws InterruptedException {

        Thread.sleep(800);
        onView(withId(R.id.homeButton))
                .perform(click());

        Thread.sleep(800);
        onView(withId(R.id.foodMenuButton))
                .perform(click());

        Thread.sleep(1000);
        onView(AllOf.allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Blueberry Muffin"))))
                .perform(click());

        Thread.sleep(800);
        onView(withId(R.id.addToCartButton))
                .check(doesNotExist());
    }

    /**
     * Instrumented Test that checks that the Blueberry Muffin is displayed, along with the "SOLD OUT"
     * text.
     */
    @Test
    public void c_IsSoldOutTextDisplayed() throws InterruptedException {

        Thread.sleep(800);
        onView(withId(R.id.homeButton))
                .perform(click());

        Thread.sleep(800);
        onView(withId(R.id.foodMenuButton))
                .perform(click());

        Thread.sleep(1000);
        onView(AllOf.allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Blueberry Muffin"))))
                .check(matches(isDisplayed()));

        Thread.sleep(1000);
        onView(AllOf.allOf(ViewMatchers.withId(R.id.addToCart), hasSibling(withText("Blueberry Muffin"))))
                .perform(click());

        Thread.sleep(1000);
        onView(AllOf.allOf(ViewMatchers.withId(R.id.soldOutStatus), hasSibling(withText("Blueberry Muffin"))))
                .check(isVisible());
    }

    //Checks if a view is visible
    public static ViewAssertion isVisible() {
        return new ViewAssertion() {
            public void check(View view, NoMatchingViewException noView) {
                assertThat(view, new VisibilityMatcher(View.VISIBLE));
            }
        };
    }

    //Checks if a view is gone
    public static ViewAssertion isGone() {
        return new ViewAssertion() {
            public void check(View view, NoMatchingViewException noView) {
                assertThat(view, new VisibilityMatcher(View.GONE));
            }
        };
    }

    //Checks if a view is invisible
    public static ViewAssertion isInvisible() {
        return new ViewAssertion() {
            public void check(View view, NoMatchingViewException noView) {
                assertThat(view, new VisibilityMatcher(View.INVISIBLE));
            }
        };
    }

    private static class VisibilityMatcher extends BaseMatcher<View> {

        private int visibility;

        public VisibilityMatcher(int visibility) {
            this.visibility = visibility;
        }

        @Override
        public void describeTo(Description description) {
            String visibilityName;
            if (visibility == View.GONE) visibilityName = "GONE";
            else if (visibility == View.VISIBLE) visibilityName = "VISIBLE";
            else visibilityName = "INVISIBLE";
            description.appendText("View visibility must has equals " + visibilityName);
        }

        @Override
        public boolean matches(Object o) {

            if (o == null) {
                if (visibility == View.GONE || visibility == View.INVISIBLE) return true;
                else if (visibility == View.VISIBLE) return false;
            }

            if (!(o instanceof View))
                throw new IllegalArgumentException("Object must be instance of View. Object is instance of " + o);
            return ((View) o).getVisibility() == visibility;
        }
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
