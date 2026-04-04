package com.example.seniorfinal.Utilities;

import com.stripe.Stripe;

public class StripeConfig
{
        public static void init() {
            // Use your test secret key
            Stripe.apiKey = "sk_test_51THVBJA4cQbN5Nzb2iOiD4ucHWdzNCPGeRX6tujUtNSUfZuL1WDxk0ciZuJUOCy2pcidNlSo3YiuijncX3KRFMq000kni6g5ZQ";
        }
}
