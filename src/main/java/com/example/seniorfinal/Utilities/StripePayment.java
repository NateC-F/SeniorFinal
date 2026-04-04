package com.example.seniorfinal.Utilities;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class StripePayment {

    public static PaymentIntent createPaymentIntent(long amountInCents, String currency) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)           // e.g., $50 => 5000
                .setCurrency(currency)              // e.g., "usd"
                .addPaymentMethodType("card")       // allow card payments
                .build();

        return PaymentIntent.create(params);
    }
}