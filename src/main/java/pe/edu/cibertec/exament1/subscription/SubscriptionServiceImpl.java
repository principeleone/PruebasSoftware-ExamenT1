package pe.edu.cibertec.exament1.subscription;

import java.time.LocalDate;

public class SubscriptionServiceImpl {
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentGatewayClient paymentGatewayClient;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, PaymentGatewayClient paymentGatewayClient) {
        this.subscriptionRepository = subscriptionRepository;
        this.paymentGatewayClient = paymentGatewayClient;
    }

    public Subscription renewSubscription(Long subscriptionId) {
        Subscription sub = subscriptionRepository.findById(subscriptionId);

        if (!paymentGatewayClient.isCardValid(subscriptionId)) {
            subscriptionRepository.updateStatus(subscriptionId, "SUSPENDED");
            throw new ExpiredCardException("La tarjeta asociada ha expirado");
        }

        paymentGatewayClient.chargeMonthlyFee(subscriptionId);
        LocalDate newBillingDate = sub.nextBillingDate().plusMonths(1);
        subscriptionRepository.updateBillingDate(subscriptionId, newBillingDate);

        return new Subscription(subscriptionId, "ACTIVE", newBillingDate);
    }

}
