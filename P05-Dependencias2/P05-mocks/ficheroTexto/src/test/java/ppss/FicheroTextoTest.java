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
                .addMockedMethod("getFichero")
                .mock(ctr);

        stub = ctr.createMock(FileReader.class);
    }

    @Test
    public void C1_contarCaracteres_should_return_FicheroException_when_ficheroC1_and_read_IOException() {
        String nombreFichero = "src/test/resources/ficheroC1.txt";

        assertDoesNotThrow(()-> EasyMock.expect(sut.getFichero(nombreFichero))
                                                    .andReturn(stub));

        assertDoesNotThrow(()-> EasyMock.expect(stub.read())
                                        .andReturn(97)  //a
                                        .andReturn(98)  //b
                                        .andThrow(new IOException()));  //c

        ctr.replay();

        String resultadoEsperado = nombreFichero + " (Error al leer el archivo)";
        FicheroException excepcion = assertThrows(FicheroException.class, ()-> sut.contarCaracteres(nombreFichero));

        assertEquals(resultadoEsperado, excepcion.getMessage());
        ctr.verify();
    }

    @Test
    public void C2_contarCaracteres_should_return_FicheroException_when_ficheroC2_and_close_IOException() {
        String nombreFichero = "src/test/resources/ficheroC2.txt";

        assertDoesNotThrow(()-> EasyMock.expect(sut.getFichero(nombreFichero))
                                        .andReturn(stub));

        assertDoesNotThrow(()-> EasyMock.expect(stub.read())
                                        .andReturn(97)  //(int) 'a'
                                        .andReturn(98)  //(int) 'b'
                                        .andReturn(99)  //(int) 'c'
                                        .andReturn(-1));

        assertDoesNotThrow(()-> stub.close());
        EasyMock.expectLastCall()
                .andThrow(new IOException());

        ctr.replay();

        String resultadoEsperado = nombreFichero + " (Error al cerrar el archivo)";
        FicheroException excepcion = assertThrows(FicheroException.class, ()-> sut.contarCaracteres(nombreFichero));

        assertEquals(resultadoEsperado, excepcion.getMessage());
        ctr.verify();
    }
}