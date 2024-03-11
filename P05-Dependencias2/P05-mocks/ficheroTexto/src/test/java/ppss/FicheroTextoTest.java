package ppss;

import java.io.FileReader;
import java.io.IOException;

import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.easymock.EasyMock;
import ppss.excepciones.FicheroException;

import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    IMocksControl ctr;
    FicheroTexto sut;
    FileReader stub;

    @BeforeEach
    public void setUp() {
        ctr = EasyMock.createStrictControl();

        sut = EasyMock.partialMockBuilder(FicheroTexto.class)
                .withConstructor()
                .addMockedMethod("getFichero")
                .mock(ctr);

        stub = EasyMock.niceMock(FileReader.class);
    }

    @Test
    public void C1_contarCaracteres_should_return_FicheroException_when_ficheroC1_and_read_IOException() {
        String nombreFichero = "src/test/resources/ficheroC1.txt";

        assertDoesNotThrow(EasyMock.expect(sut.getFichero(nombreFichero))
                                                .andThrow(stub));

        assertDoesNotThrow(EasyMock.expect(stub.read())
                .andReturn(97)
                .andReturn(98)
                .andThrow(new IOException()));

        EasyMock.replay(sut, stub);

        String resultadoEsperado = "src/test/resources/ficheroC1.txt (Error al leer el archivo)";
        FicheroException excepcion = assertThrows(FicheroException.class, ()-> sut.contarCaracteres("src/test/resources/ficheroC1.txt"));

        assertEquals(resultadoEsperado, excepcion.getMessage());
        EasyMock.verify(ctr);
    }

}