package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CineTest {
    Cine cine;

    @BeforeEach
    public void setup() {
        cine = new Cine();
    }

    @Test
    public void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3() {
        boolean[] asientos = new boolean[0];
        int solicitados = 3;

        //Lanza excepción
        ButacasException exception = assertThrows(ButacasException.class, ()-> cine.reservaButacas(asientos, solicitados));

        assertEquals("No se puede procesar la solicitud", exception.getMessage());
    }

    @Test
    public void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero() {
        boolean[] asientos = new boolean[0];
        int solicitados = 0;

        boolean[] asientosFuncion = Arrays.copyOf(asientos, asientos.length);   //Array que se modifica en la función
        boolean resultadoEsperado = false;  //Return esperado de la función

        boolean resultadoReal = false;
        try {
            resultadoReal = cine.reservaButacas(asientosFuncion, solicitados);
        } catch (ButacasException e) {
            fail("ButacasException lanzada");
        }
        boolean finalResultadoReal = resultadoReal; //Return real de la función

        assertAll("C2_Asientos no debe cambiar",
                () -> assertArrayEquals(asientos, asientosFuncion),
                () -> assertEquals(resultadoEsperado, finalResultadoReal)
        );
    }

    @Test
    public void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2() {
        boolean[] asientos = {false, false, false, true, true};     //Array que se modifica en la función
        int solicitados = 2;

        boolean[] asientosEsperados = {true, true, false, true, true};  //Array modificado esperado
        boolean resultadoEsperado = true;   //Return esperado de la función

        boolean resultadoReal = false;
        try {
            resultadoReal = cine.reservaButacas(asientos, solicitados);
        } catch (ButacasException e) {
            fail("ButacasException lanzada");
        }
        boolean finalResultadoReal = resultadoReal;     //Return real de la función

        assertAll("C3_Asientos debe cambiar",
                () -> assertArrayEquals(asientosEsperados, asientos),
                () -> assertEquals(resultadoEsperado, finalResultadoReal)
        );
    }

    @Test
    public void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1() {
        boolean[] asientos = {true, true, true};
        int solicitados = 1;
    }
}