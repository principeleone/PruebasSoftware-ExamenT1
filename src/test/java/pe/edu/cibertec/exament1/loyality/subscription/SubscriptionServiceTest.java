package pe.edu.cibertec.exament1.loyality.subscription;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.cibertec.exament1.subscription.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private PaymentGatewayClient paymentGatewayClient;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;


    @Test
    @DisplayName("Suspender la cuenta y lanzar excepcion cuando la tarjeta esta vencida")
    void test1() {

        Subscription subscription =
                new Subscription(
                        1L,
                        "ACTIVE",
                        LocalDate.now().minusDays(1));

        when(subscriptionRepository.findById(1L))
                .thenReturn(subscription);

        when(paymentGatewayClient.isCardValid(1L))
                .thenReturn(false);

        assertThrows(
                ExpiredCardException.class,
                () -> subscriptionService.renewSubscription(1L)
        );

        verify(subscriptionRepository)
                .updateStatus(1L, "SUSPENDED");

        verify(paymentGatewayClient, never())
                .chargeMonthlyFee(1L);
    }

    @Test
    @DisplayName("Renovar exitosamente la suscripcion por un mes adicional")
    void test2() {

        LocalDate fechaActual = LocalDate.now();

        Subscription subscription =
                new Subscription(
                        1L,
                        "ACTIVE",
                        fechaActual);

        when(subscriptionRepository.findById(1L))
                .thenReturn(subscription);

        when(paymentGatewayClient.isCardValid(1L))
                .thenReturn(true);

        Subscription resultado =
                subscriptionService.renewSubscription(1L);

        LocalDate nuevaFecha = fechaActual.plusMonths(1);

        assertEquals(nuevaFecha, resultado.nextBillingDate());

        verify(paymentGatewayClient)
                .chargeMonthlyFee(1L);

        verify(subscriptionRepository)
                .updateBillingDate(1L, nuevaFecha);
    }
}
