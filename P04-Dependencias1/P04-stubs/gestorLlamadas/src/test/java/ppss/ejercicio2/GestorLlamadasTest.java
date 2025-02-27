package ppss.ejercicio2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    GestorLlamadasTestable sut;

    @BeforeEach
    public void setUp() {
        sut = new GestorLlamadasTestable();
    }

    @Test
    public void C1_calculaConsumo_should_return_208_when_minutos_10_and_hora_15() {
        CalendarioStub c = new CalendarioStub(15);
        sut.setCalendario(c);
        double resultadoEsperado = 208;
        double resultadoReal = sut.calculaConsumo(10);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void C2_calculaConsumo_should_return_105_when_minutos_10_and_hora_22() {
        CalendarioStub c = new CalendarioStub(22);
        sut.setCalendario(c);
        double resultadoEsperado = 105;
        double resultadoReal = sut.calculaConsumo(10);
        assertEquals(resultadoEsperado, resultadoReal);
    }
}