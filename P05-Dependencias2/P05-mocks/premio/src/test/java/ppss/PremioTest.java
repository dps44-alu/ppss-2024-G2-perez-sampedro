package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.ClienteWebServiceException;

import static org.junit.jupiter.api.Assertions.*;

class PremioTest {
    IMocksControl ctr;
    Premio sut;
    ClienteWebService stub;

    @BeforeEach
    public void setUp() {
        ctr = EasyMock.createStrictControl();

        sut = EasyMock.partialMockBuilder(Premio.class)
                        .addMockedMethod("generaNumero")
                        .mock(ctr);

        stub = ctr.createMock(ClienteWebService.class);
    }

    @Test
    public void C1_compruebaPremio_should_return_String_when_generador_0_07_and_obtenerPremio_String() {
        float generador = 0.07f;

        EasyMock.expect(sut.generaNumero())
                .andReturn(generador);

        assertDoesNotThrow(()-> EasyMock.expect(stub.obtenerPremio())
                                        .andStubReturn("entrada final Champions"));

        ctr.replay();

        sut.cliente = stub;

        String resultadoEsperado = "Premiado con entrada final Champions";
        String resultadoReal = sut.compruebaPremio();

        assertEquals(resultadoEsperado, resultadoReal);
        ctr.verify();
    }

    @Test
    public void C2_compruebaPremio_should_return_String_when_generador_0_03_and_obtenerPremio_ClienteWebServiceException() {
        float generador = 0.03f;

        EasyMock.expect(sut.generaNumero())
                .andReturn(generador);

        assertDoesNotThrow(()-> EasyMock.expect(stub.obtenerPremio())
                                        .andStubThrow(new ClienteWebServiceException()));

        ctr.replay();

        sut.cliente = stub;

        String resultadoEsperado = "No se ha podido obtener el premio";
        String resultadoReal = sut.compruebaPremio();

        assertEquals(resultadoEsperado, resultadoReal);
        ctr.verify();
    }

    @Test
    public void C3_compruebaPremio_should_return_String_when_generador_0_3() {
        float generador = 0.3f;

        EasyMock.expect(sut.generaNumero())
                .andReturn(generador);

        ctr.replay();

        String resultadoEsperado = "Sin premio";
        String resultadoReal = sut.compruebaPremio();

        assertEquals(resultadoEsperado, resultadoReal);
        ctr.verify();
    }
}