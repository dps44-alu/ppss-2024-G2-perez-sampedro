package ppss.ejercicio1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    GestorLlamadasStub gestor;

    @BeforeEach
    public void setUp() {
        gestor = new GestorLlamadasStub();
    }

    @Test
    public void C1_calculaConsumo_should_return_208_when_minutos_10_and_hora_15() {
        gestor.setHora(15);
        double resultadoEsperado = 208;
        double resultadoReal = gestor.calculaConsumo(10);
        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void C2_calculaConsumo_should_return_105_when_minutos_10_and_hora_22() {
        gestor.setHora(22);
        double resultadoEsperado = 105;
        double resultadoReal = gestor.calculaConsumo(10);
        assertEquals(resultadoEsperado, resultadoReal);
    }
}