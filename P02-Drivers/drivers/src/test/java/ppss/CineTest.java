package ppss;

import org.junit.jupiter.api.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

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
        boolean[] asientos = new boolean[]{};    //Array que se modifica en la función
        int solicitados = 0;

        boolean[] asientosEsperados = Arrays.copyOf(asientos, asientos.length);   //Array modificado esperado
        boolean resultadoEsperado = false;  //Return esperado de la función

        boolean resultadoReal = assertDoesNotThrow(()-> cine.reservaButacas(asientos, solicitados));

        assertAll("C2_Asientos no debe cambiar",
                () -> assertArrayEquals(asientosEsperados, asientos),
                () -> assertEquals(resultadoEsperado, resultadoReal)
        );
    }

    @Test
    public void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2() {
        boolean[] asientos = {false, false, false, true, true};     //Array que se modifica en la función
        int solicitados = 2;

        boolean[] asientosEsperados = {true, true, false, true, true};  //Array modificado esperado
        boolean resultadoEsperado = true;   //Return esperado de la función

        boolean resultadoReal = assertDoesNotThrow(()-> cine.reservaButacas(asientos, solicitados));


        assertAll("C3_Asientos debe cambiar",
                () -> assertArrayEquals(asientosEsperados, asientos),
                () -> assertEquals(resultadoEsperado, resultadoReal)
        );
    }

    @Test
    public void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1() {
        boolean[] asientos = {true, true, true};    //Array que se modifica en la función
        int solicitados = 1;

        boolean[] asientosEsperados = {true, true, true};  //Array modificado esperado
        boolean resultadoEsperado = false;   //Return esperado de la función

        boolean resultadoReal = assertDoesNotThrow(()-> cine.reservaButacas(asientos, solicitados));

        assertAll("C3_Asientos debe cambiar",
                () -> assertArrayEquals(asientosEsperados, asientos),
                () -> assertEquals(resultadoEsperado, resultadoReal)
        );
    }

    @ParameterizedTest(name = "[{index}] should be {0} when we want {1} and {2}")
    @MethodSource("testArguments")
    @DisplayName("reservaButacas_")
    @Tag("parametrizado")
    public void reservaButacasC5(boolean resultadoEsperado, int solicitados, String mensaje, boolean[] asientos) {
        boolean resultadoReal = assertDoesNotThrow(()-> cine.reservaButacas(asientos, solicitados));

        assertEquals(resultadoEsperado, resultadoReal, mensaje);
    }

    private static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(false, 0, "fila has no seats", new boolean[]{}),
                Arguments.of(true, 2, "there are 2 free seats", new boolean[]{false, false, false, true, true}),
                Arguments.of(false, 1, "all seats are already reserved", new boolean[]{true, true, true})
        );
    }
}