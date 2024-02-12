package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CineTest {
    Cine cine;

    @BeforeEach
    public void setup() {
        cine = new Cine();
    }

    @Test
    public void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3() {
        asientos = [];
        solicitados = 3;
        resultadoEsperado = "No se puede procesar la solicitud";
        resultadoReal = cine.reservarButacas(asientos, solicitados);
        assertEquals(resultadoEsperado, resultadoReal);
    }
}