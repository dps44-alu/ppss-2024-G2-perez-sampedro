package ppss.ejercicio1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class MultipathExampleTest {

    MultipathExample path = new MultipathExample();

    @Test
    public void multipath1_lineas() {
        int a = 6;
        int b = 6;
        int c = 1;

        int resultadoEsperado = 13;

        int resultadoReal = path.multiPath1(a, b, c);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void multipath1_condiciones() {
        int a = 6;
        int b = 5;
        int c = 1;

        int resultadoEsperado = 7;
        int resultadoReal = path.multiPath1(a, b, c);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void multipath_all_7_return_7() {
        int a = 3;
        int b = 6;
        int c = 2;

        int resultadoEsperado = 8;
        int resultadoReal = path.multiPath1(a, b, c);

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @ParameterizedTest
    @MethodSource("ejercicio1_D")
    public void multipath2(int a, int b, int c, int resultadoEsperado) {
        int resultadoReal = path.multiPath2(a, b, c);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @ParameterizedTest
    @MethodSource("ejercicio1_E")
    public void multipath3(int a, int b, int c, int resultadoEsperado) {
        int resultadoReal = path.multiPath3(a, b, c);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    private static Stream<Arguments> ejercicio1_D() {
        return Stream.of(
                Arguments.of(6, 6, 6, 12),  //true, false, true
                Arguments.of(4, 4, 4, 4),   //false, true, false
                Arguments.of(6, 4, 6, 16)   //true, true, true
        );
    }

    private static Stream<Arguments> ejercicio1_E() {
        return Stream.of(
            Arguments.of(6, 4, 6, 16),  //true, true, true
            Arguments.of(4, 6, 4, 4)    //false, false, false
        );
    }
}
