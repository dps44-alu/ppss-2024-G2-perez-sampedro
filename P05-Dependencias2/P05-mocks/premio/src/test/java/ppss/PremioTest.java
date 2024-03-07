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
                        .withConstructor()
                        .addMockedMethod("generaNumero")
                        .mock(ctr);

        stub = EasyMock.niceMock(ClienteWebService.class);
    }

    @Test
    public void C1_compruebaPremio_should_return_String_when_generador_0_07_and_obtenerPremio_String() {
        float generador = 0.07f;

        assertDoesNotThrow(()-> EasyMock.expect(stub.obtenerPremio())
                                        .andStubReturn("entrada final Champions"));
        EasyMock.replay(stub);

        EasyMock.expect(sut.generaNumero())
                .andReturn(generador);
        EasyMock.replay(sut);

        sut.setCliente(stub);

        String resultadoEsperado = "Premiado con entrada final Champions";
        String resultadoReal = sut.compruebaPremio();

        assertEquals(resultadoEsperado, resultadoReal);
        EasyMock.verify(sut, stub);
    }

    @Test
    public void C2_compruebaPremio_should_return_String_when_generador_0_03_and_obtenerPremio_ClienteWebServiceException() {
        float generador = 0.03f;

        assertDoesNotThrow(()-> EasyMock.expect(stub.obtenerPremio())
                .andStubThrow(new ClienteWebServiceException()));
        EasyMock.replay(stub);

        EasyMock.expect(sut.generaNumero())
                .andReturn(generador);
        EasyMock.replay(sut);

        sut.setCliente(stub);

        String resultadoEsperado = "No se ha podido obtener el premio";
        String resultadoReal = sut.compruebaPremio();

        assertEquals(resultadoEsperado, resultadoReal);
        EasyMock.verify(sut, stub);
    }

    @Test
    public void C3_compruebaPremio_should_return_String_when_generador_0_3() {
        float generador = 0.3f;

        EasyMock.expect(sut.generaNumero())
                .andReturn(generador);
        EasyMock.replay(sut);

        String resultadoEsperado = "Sin premio";
        String resultadoReal = sut.compruebaPremio();

        assertEquals(resultadoEsperado, resultadoReal);
        EasyMock.verify(sut);
    }
}