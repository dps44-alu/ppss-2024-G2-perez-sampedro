package ppss;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {

    FicheroTexto fichero;

    @BeforeEach
    public void setup() {
        fichero = new FicheroTexto();
    }

    @Test
    public void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist() {
        String nombreFichero = "ficheroC1.txt";

        //Lanza excepción
        FicheroException exception = assertThrows(FicheroException.class, ()-> fichero.contarCaracteres(nombreFichero));

        assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", exception.getMessage());
    }

    @Test
    public void C2_contarCaracteres_should_return_3_when_file_has_3_chars() {
        String nombreFichero = "src/test/resources/ficheroCorrecto.txt";
        int resultadoEsperado = 3;  //Return esperado de la función

        int resultadoReal = assertDoesNotThrow(()-> fichero.contarCaracteres(nombreFichero));

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Tag("excluido")
    @Test
    public void C3_contarCaracteres_should_return_Exception_when_file_cannot_be_read() {
        Assertions.fail();
    }

    @Tag("excluido")
    @Test
    public void C4_contarCaracteres_should_return_Exception_when_file_cannot_be_closed() {
        Assertions.fail();
    }
}