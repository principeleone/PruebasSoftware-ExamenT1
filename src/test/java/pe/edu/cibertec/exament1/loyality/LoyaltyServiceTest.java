package pe.edu.cibertec.exament1.loyality;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoyaltyServiceTest {

    @Mock
    private LoyaltyRepository loyaltyRepository;

    @InjectMocks
    private LoyaltyServiceImpl loyaltyService;

    @Test
    @DisplayName("Lanzar Excepcion si el monto es menor a 0")
    void test1(){
        BigDecimal montoAPagar = new BigDecimal("-20");
        Long userId = 1L;

        assertThrows(
                IllegalArgumentException.class,
                () -> loyaltyService.calculateAndAssignPoints(
                        userId,
                        montoAPagar
                )
        );

        verify(loyaltyRepository, never())
                .addPoints(anyLong(), anyInt());
    }

    @Test
    @DisplayName("Asignar puntos dobles cuando el cliente es VIP")
    void test2(){
        BigDecimal montoAPagar = new BigDecimal("100");
        Long userId = 1L;

        when(loyaltyRepository.isVipUser(userId))
                .thenReturn(true);

        LoyaltyResponse response = loyaltyService.calculateAndAssignPoints(
                userId,montoAPagar
        );

        assertEquals(200,response.pointsEarned());

        verify(loyaltyRepository).addPoints(userId,200);

    }

}
