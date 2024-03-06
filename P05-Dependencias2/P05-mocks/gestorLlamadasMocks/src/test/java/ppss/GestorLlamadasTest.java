package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;

import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {
    GestorLlamadas mockGestor;
    Calendario mockCalendario;

    @BeforeEach
    public void setUp() {
        mockGestor = EasyMock.partialMockBuilder(GestorLlamadas.class)
                                .addMockedMethod("getCalendario")
                                .mock();

        mockCalendario = EasyMock.niceMock(Calendario.class);
    }

    @Test
    public void C1_calculaConsumo_should_return_457_6_when_minutos_22_and_hora_10() {
        int minutos = 22;
        int hora = 10;

        EasyMock.expect(mockCalendario.getHoraActual())
                .andStubReturn(hora);
        EasyMock.replay(mockCalendario);    //Activación

        EasyMock.expect(mockGestor.getCalendario())
                .andReturn(mockCalendario);
        EasyMock.replay(mockGestor);    //Activación

        double resultadoEsperado = 457.6;
        double resultadoReal = mockGestor.calculaConsumo(minutos);

        assertEquals(resultadoEsperado, resultadoReal);
        EasyMock.verify(mockGestor, mockCalendario);    //Verificación de uso
    }

    @Test
    public void C2_calculaConsumo_should_return_136_5_when_minutos_13_and_hora_21() {
        int minutos = 13;
        int hora = 21;

        EasyMock.expect(mockCalendario.getHoraActual())
                .andStubReturn(hora);
        EasyMock.replay(mockCalendario);    //Activación

        EasyMock.expect(mockGestor.getCalendario())
                .andReturn(mockCalendario);
        EasyMock.replay(mockGestor);    //Activación

        double resultadoEsperado = 136.5;
        double resultadoReal = mockGestor.calculaConsumo(minutos);

        assertEquals(resultadoEsperado, resultadoReal);
        EasyMock.verify(mockGestor, mockCalendario);    //Verificación de uso
    }
}