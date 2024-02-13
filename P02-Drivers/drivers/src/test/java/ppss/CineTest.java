package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.UnexpectedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        String resultadoEsperado = "No se puede procesar la solicitud";
        Boolean resultadoReal = cine.reservaButacas(asientos, solicitados);

        UnexpectedException exception = assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos, solicitados));

        assertEquals(resultadoEsperado, exception.getMessage());
    }
}