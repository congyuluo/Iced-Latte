package com.zufar.icedlatte.payment.api.event;

import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is responsible for parsing payment event to session object.
 */
@Slf4j
@Service
public class PaymentEventParser {

    public Session parseEventToSession(final Event stripePaymentEvent) {
        log.info("Trying to parse StripePaymentEvent = '{}'", stripePaymentEvent.getType());

        Optional<Session> stripeSession = stripePaymentEvent.getDataObjectDeserializer()
                .getObject()
                .filter(Session.class::isInstance)
                .map(Session.class::cast);

        if (stripeSession.isEmpty()) {
            log.info("Current StripePaymentEvent = '{}' is not related to Stripe session, skipping", stripePaymentEvent.getType());
            return null;
        } else {
            return stripeSession.get();
        }
    }
}
