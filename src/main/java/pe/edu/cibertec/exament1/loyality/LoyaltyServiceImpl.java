package pe.edu.cibertec.exament1.loyality;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoyaltyServiceImpl {
    private final LoyaltyRepository loyaltyRepository;

    public LoyaltyServiceImpl(LoyaltyRepository loyaltyRepository) {
        this.loyaltyRepository = loyaltyRepository;
    }

    public LoyaltyResponse calculateAndAssignPoints(Long userId, BigDecimal purchaseAmount) {
        if (purchaseAmount == null || purchaseAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto de compra debe ser mayor a cero");
        }

        boolean isVip = loyaltyRepository.isVipUser(userId);

        // Regla básica: 1 punto por cada unidad entera. Si es VIP, se duplica.
        int basePoints = purchaseAmount.setScale(0, RoundingMode.DOWN).intValue();
        int finalPoints = isVip ? basePoints * 2 : basePoints;

        loyaltyRepository.addPoints(userId, finalPoints);

        return new LoyaltyResponse(userId, finalPoints, isVip);
    }

}
