package pe.edu.cibertec.exament1.loyality.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoyaltyPointsCalculatorTest {
    @Test
    @DisplayName("Lanzar Excepcion cuando el monto de compra es nulo")
    void test1(){
        LoyaltyPointsCalculator lpc=new LoyaltyPointsCalculator();
        assertThrows(IllegalArgumentException.class,()->lpc.calculateAccumulatedPoints(null,"REGULAR")
        );
    }
}
