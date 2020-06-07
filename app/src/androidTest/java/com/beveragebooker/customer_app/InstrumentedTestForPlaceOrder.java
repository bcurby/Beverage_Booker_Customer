package com.beveragebooker.customer_app;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.beveragebooker.customer_app.activities.CartActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedTestForPlaceOrder {

    @Rule public ActivityScenarioRule<CartActivity> ActivityScenarioRule
            = new ActivityScenarioRule<>(CartActivity.class);

    /**
     * Verifies that when checkoutButton is clicked everything on the next page is displayed showing that
     * the application works as intended.
     */
    @Test
    public void GoToCheckOut() {
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.creditCardNumberLayout))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTextCreditCardNumber1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTextCreditCardNumber2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTextCreditCardNumber3))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTextCreditCardNumber4))
                .check(matches(isDisplayed()));
        onView(withId(R.id.creditCardCVVLayout))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTextCVV))
                .check(matches(isDisplayed()));
        onView(withId(R.id.creditCardExpiryLayout))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTextExpiryMonth))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editTextExpiryYear))
                .check(matches(isDisplayed()));
        onView(withId(R.id.openPlaceOrderButton))
                .check(matches(isDisplayed()));
    }

    /**
     * Verifies that when openPlaceOrderButton is clicked and there is an error at
     * card number where there is a number missing it displays an error instead of continuing.
     */
    @Test
    public void PaymentInformation_ErrorAtCardNumber_TooLittleNumbers() {
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.editTextCreditCardNumber1))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber2))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber3))
                .perform(typeText("123")); // this is where the error is
        onView(withId(R.id.editTextCreditCardNumber4))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCVV))
                .perform(typeText("123"));
        onView(withId(R.id.editTextExpiryMonth))
                .perform(typeText("12"));
        onView(withId(R.id.editTextExpiryYear))
                .perform(typeText("21"));
        onView(withId(R.id.openPlaceOrderButton))
                .perform(click());
        onView(withId(R.id.editTextCreditCardNumber1))
                .check(matches(hasErrorText("Credit card number must be 16 digits")));
    }

    /**
     * Verifies that when openPlaceOrderButton is clicked and there is an error at
     * CVV where there is a number missing it displays an error instead of continuing.
     */
    @Test
    public void PaymentInformation_ErrorAtCVV_TooLittleNumbers() {
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.editTextCreditCardNumber1))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber2))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber3))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber4))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCVV))
                .perform(typeText("12")); // This is where the error is
        onView(withId(R.id.editTextExpiryMonth))
                .perform(typeText("12"));
        onView(withId(R.id.editTextExpiryYear))
                .perform(typeText("21"));
        onView(withId(R.id.openPlaceOrderButton))
                .perform(click());
        onView(withId(R.id.editTextCVV))
                .check(matches(hasErrorText("CVV number must be 3 digits")));
    }

    /**
     * Verifies that when openPlaceOrderButton is clicked and there is an error at
     * Month where an incorrect month is entered it errors instead of continuing.
     */
    @Test
    public void PaymentInformation_ErrorAtMonth_OutOfBounds() {
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.editTextCreditCardNumber1))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber2))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber3))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber4))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCVV))
                .perform(typeText("123"));
        onView(withId(R.id.editTextExpiryMonth))
                .perform(typeText("30")); // This is where the error is
        onView(withId(R.id.editTextExpiryYear))
                .perform(typeText("21"));
        onView(withId(R.id.openPlaceOrderButton))
                .perform(click());
        onView(withId(R.id.editTextExpiryMonth))
                .check(matches(hasErrorText("Expiry month must be a number between 01 and 12")));
    }

    /**
     * Verifies that when openPlaceOrderButton is clicked and there is an error at
     * year where an out of date year is provided it errors instead of continuing.
     */
    public void PaymentInformation_ErrorAtYear_OutOfBounds() {
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.editTextCreditCardNumber1))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber2))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber3))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber4))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCVV))
                .perform(typeText("123"));
        onView(withId(R.id.editTextExpiryMonth))
                .perform(typeText("12"));
        onView(withId(R.id.editTextExpiryYear))
                .perform(typeText("19")); // This is where the error is
        onView(withId(R.id.openPlaceOrderButton))
                .perform(click());
        onView(withId(R.id.editTextExpiryYear))
                .check(matches(hasErrorText("Expiry year must be a number equal to current year or greater")));
    }

    /**
     * This is pretty much simulating an entire order apart from adding the item to cart.
     * The happy day scenario.
     */
    @Test
    public void zPaymentInformation_PlaceOrder_OrderConfirmation_MainMenu() {
        onView(withId(R.id.checkoutButton))
                .perform(click());
        onView(withId(R.id.editTextCreditCardNumber1))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber2))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber3))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCreditCardNumber4))
                .perform(typeText("1234"));
        onView(withId(R.id.editTextCVV))
                .perform(typeText("123"));
        onView(withId(R.id.editTextExpiryMonth))
                .perform(typeText("12"));
        onView(withId(R.id.editTextExpiryYear))
                .perform(typeText("21"));
        onView(withId(R.id.openPlaceOrderButton))
                .perform(click());
        onView(withId(R.id.placeOrderRecyclerView))
                .check(matches(isDisplayed()));
        onView(withId(R.id.orderTotal))
                .check(matches(isDisplayed()));
        onView(withId(R.id.placeOrderButton))
                .check(matches(isDisplayed()));
        onView(withId(R.id.placeOrderButton))
                .perform(click());
        onView(withId(R.id.textViewOrderConfirm))
                .check(matches(isDisplayed()));
        onView(withId(R.id.returnToMainMenu))
                .check(matches(isDisplayed()));
        onView(withId(R.id.returnToMainMenu))
                .perform(click());
        onView(withId(R.id.menuLink))
                .check(matches(isDisplayed()));
        onView(withId(R.id.editText2))
                .check(matches(isDisplayed()));
    }
}
