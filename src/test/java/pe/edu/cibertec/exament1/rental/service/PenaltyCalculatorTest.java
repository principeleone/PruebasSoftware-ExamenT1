package pe.edu.cibertec.exament1.rental.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PenaltyCalculatorTest {
    @Test
    @DisplayName("Lanzar Excepcion cuando los dias de retraso son negativos")
    void test1() {
        PenaltyCalculator pc = new PenaltyCalculator();

        assertThrows(
                IllegalStateException.class,
                () -> pc.calculateLatePenalty(-1)
        );
    }

    @Test
    @DisplayName("Retornar 0 cuando la devolucion es a tiempo")
    void test2() {
        PenaltyCalculator pc = new PenaltyCalculator();

        int resultado = pc.calculateLatePenalty(0);

        assertEquals(0, resultado);
    }

    @Test
    @DisplayName("Calcular penalidad para retraso menor o igual a 7 dias")
    void test3() {
        PenaltyCalculator pc = new PenaltyCalculator();

        int resultado = pc.calculateLatePenalty(5);

        assertEquals(75, resultado);
        // 5 * 15
    }

    @Test
    @DisplayName("Aplicar penalidad adicional por severidad despues de 7 dias")
    void test4() {
        PenaltyCalculator pc = new PenaltyCalculator();

        int resultado = pc.calculateLatePenalty(10);

        assertEquals(180, resultado);
        // (10 * 15) + ((10 - 7) * 10)
        // 150 + 30 = 180
    }

    @Test
    @DisplayName("Aplicar limite maximo de penalidad")
    void test5() {
        PenaltyCalculator pc = new PenaltyCalculator();

        int resultado = pc.calculateLatePenalty(20);

        assertEquals(300, resultado);
        // (20 * 15) + ((20 - 7) * 10) = 430
    }
}
