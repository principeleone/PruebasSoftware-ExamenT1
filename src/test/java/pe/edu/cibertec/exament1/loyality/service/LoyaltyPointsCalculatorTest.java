package pe.edu.cibertec.exament1.loyality.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class LoyaltyPointsCalculatorTest {
    @Test
    @DisplayName("Lanzar Excepcion cuando el monto de compra es nulo")
    void test1(){
        LoyaltyPointsCalculator lpc=new LoyaltyPointsCalculator();
        assertThrows(
                IllegalArgumentException.class,
                ()->lpc.calculateAccumulatedPoints(null,"REGULAR")
        );
    }

    @Test
    @DisplayName("Lanzar Excepcion cuando el monto de compra es igual a cero")
    void test2() {
        LoyaltyPointsCalculator lpc = new LoyaltyPointsCalculator();

        assertThrows(
                IllegalArgumentException.class,
                () -> lpc.calculateAccumulatedPoints(BigDecimal.ZERO, "REGULAR")
        );
    }

    @Test
    @DisplayName("Lanzar Excepcion cuando el monto de compra es negativo")
    void test3() {
        LoyaltyPointsCalculator lpc = new LoyaltyPointsCalculator();

        assertThrows(
                IllegalArgumentException.class,
                () -> lpc.calculateAccumulatedPoints(new BigDecimal("-10"), "REGULAR")
        );
    }

    @Test
    @DisplayName("Calcular puntos correctamente para cliente regular")
    void test4() {
        LoyaltyPointsCalculator lpc = new LoyaltyPointsCalculator();

        int resultado = lpc.calculateAccumulatedPoints(
                new BigDecimal("150.75"),
                "REGULAR"
        );

        assertEquals(150, resultado);
    }

    @Test
    @DisplayName("Calcular puntos dobles correctamente para cliente VIP")
    void test5() {
        LoyaltyPointsCalculator lpc = new LoyaltyPointsCalculator();

        int resultado = lpc.calculateAccumulatedPoints(
                new BigDecimal("150.75"),
                "VIP"
        );

        assertEquals(301, resultado);
    }
}
