package ppss;

import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;

import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    IMocksControl ctr;
    GestorLlamadas mockGestor;
    Calendario mockCalendario;

    @BeforeEach
    public void setUp() {
        ctr = EasyMock.createStrictControl();

        mockGestor = EasyMock.partialMockBuilder(GestorLlamadas.class)
                                .addMockedMethod("getCalendario")
                                .mock(ctr);

        mockCalendario = EasyMock.strictMock(Calendario.class);
    }

    @Test
    public void C1_calculaConsumo_should_return_457_6_when_minutos_22_and_hora_10() {
        int minutos = 22;
        int hora = 10;

        EasyMock.expect(mockCalendario.getHoraActual())
                .andReturn(hora);

        EasyMock.expect(mockGestor.getCalendario())
                .andReturn(mockCalendario);

        ctr.replay();

        double resultadoEsperado = 457.6;
        double resultadoReal = mockGestor.calculaConsumo(minutos);

        assertEquals(resultadoEsperado, resultadoReal);
        ctr.verify();
    }

    @Test
    public void C2_calculaConsumo_should_return_136_5_when_minutos_13_and_hora_21() {
        int minutos = 13;
        int hora = 21;

        EasyMock.expect(mockCalendario.getHoraActual())
                .andReturn(hora);

        EasyMock.expect(mockGestor.getCalendario())
                .andReturn(mockCalendario);

        ctr.replay();

        double resultadoEsperado = 136.5;
        double resultadoReal = mockGestor.calculaConsumo(minutos);

        assertEquals(resultadoEsperado, resultadoReal);
        ctr.verify();
    }
}