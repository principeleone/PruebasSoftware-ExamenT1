package pe.edu.cibertec.exament1.subscription;

public interface PaymentGatewayClient {
    boolean isCardValid(Long id);
    void chargeMonthlyFee(Long id);
}
