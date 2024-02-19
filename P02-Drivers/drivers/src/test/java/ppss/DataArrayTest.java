package ppss;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DataArrayTest {

    DataArray dataArray;

    @Test
    public void C1_delete_should_delete_5_when_delete_5_and_there_is_one_5() {
        int[] datos = {1, 3, 5, 7};
        int[] datosEsperado = {1, 3, 7};    //Array modificado esperado
        int elementoBorrar = 5;     //Elemento a borrar

        dataArray  = new DataArray(datos);
        assertDoesNotThrow(()-> dataArray.delete(elementoBorrar));
        int[] datosReal = dataArray.getColeccion();     //Array modificado en la función

        int sizeEsperado = 3;    //Número de elementos esperado
        int sizeReal = dataArray.size();     //Número de elementos real

        assertAll("C1_DataArray debe cambiar",
                ()-> assertArrayEquals(datosEsperado, datosReal),
                ()-> assertEquals(sizeEsperado, sizeReal)
        );
    }

    @Test
    public void C2_delete_should_delete_one_3_when_delete_3_and_there_are_two_3() {
        int[] datos = {1, 3, 3, 5, 7};
        int[] datosEsperado = {1, 3, 5, 7};     //Array modificado esperado
        int elementoBorrar = 3;     //Elemento a borrar

        dataArray  = new DataArray(datos);
        assertDoesNotThrow(()-> dataArray.delete(elementoBorrar));
        int[] datosReal = dataArray.getColeccion();     //Array modificado en la función

        int sizeEsperado = 4;    //Número de elementos esperado
        int sizeReal = dataArray.size();     //Número de elementos real

        assertAll("C2_DataArray debe cambiar",
                ()-> assertArrayEquals(datosEsperado, datosReal),
                ()-> assertEquals(sizeEsperado, sizeReal)
        );
    }

    @Test
    public void C3_delete_should_delete_4_when_delete_4_and_there_is_one_4() {
        int[] datos = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] datosEsperado = {1, 2, 3, 5, 6, 7, 8, 9, 10};     //Array modificado esperado
        int elementoBorrar = 4;     //Elemento a borrar

        dataArray  = new DataArray(datos);
        assertDoesNotThrow(()-> dataArray.delete(elementoBorrar));
        int[] datosReal = dataArray.getColeccion();     //Array modificado en la función

        int sizeEsperado = 9;    //Número de elementos esperado
        int sizeReal = dataArray.size();     //Número de elementos real

        assertAll("C3_DataArray debe cambiar",
                ()-> assertArrayEquals(datosEsperado, datosReal),
                ()-> assertEquals(sizeEsperado, sizeReal)
        );
    }

    @Test
    public void C4_delete_should_return_Exception_when_delete_8_and_datos_empty() {
        int[] datos = {};
        int elementoBorrar = 4;     //Elemento a borrar

        dataArray = new DataArray(datos);
        DataException exception = assertThrows(DataException.class, ()-> dataArray.delete(elementoBorrar));

        assertEquals("No hay elementos en la colección", exception.getMessage());
    }

    @Test
    public void C5_delete_should_return_Exception_when_delete_minus_5_and_datos_not_empty() {
        int[] datos = {1, 3, 5, 7};
        int elementoBorrar = -5;    //Elemento a borrar

        dataArray = new DataArray(datos);
        DataException exception = assertThrows(DataException.class, ()-> dataArray.delete(elementoBorrar));

        assertEquals("El valor a borrar debe ser > 0", exception.getMessage());
    }

    @Test
    public void C6_delete_should_return_Exception_when_delete_0_and_datos_empty() {
        int[] datos = {};
        int elementoBorrar = 0;    //Elemento a borrar

        dataArray = new DataArray(datos);
        DataException exception = assertThrows(DataException.class, ()-> dataArray.delete(elementoBorrar));

        assertEquals("Colección vacía. Y el valor a borrar debe ser > 0", exception.getMessage());
    }

    @Test
    public void C7_delete_should_return_Exception_when_delete_8_and_there_is_not_8() {
        int[] datos = {1, 3, 5, 7};
        int elementoBorrar = 8;    //Elemento a borrar

        dataArray = new DataArray(datos);
        DataException exception = assertThrows(DataException.class, ()-> dataArray.delete(elementoBorrar));

        assertEquals("Elemento no encontrado", exception.getMessage());
    }

    @ParameterizedTest(name = "[{index}] Mesage exception should be {0} when we want delete {1}")
    @MethodSource("testArgumentsC8")
    @DisplayName("delete_With_Exceptions_")
    @Tag("parametrizado")
    @Tag("conExcepciones")
    public void C8_deleteWithExceptions(String mensaje, int elementoBorrar, int[] datos) {
        dataArray = new DataArray(datos);
        DataException exception = assertThrows(DataException.class, ()-> dataArray.delete(elementoBorrar));

        assertEquals(mensaje, "\"" + exception.getMessage() + "\"");
    }

    @ParameterizedTest(name = "[{index}] should be {0} when we want delete {1}")
    @MethodSource("testArgumentsC9")
    @DisplayName("delete_Without_Exceptions_")
    @Tag("parametrizado")
    public void C9_deleteWithoutExceptions(int[] datosEsperado, int elementoBorrar, int[] datos) {
        dataArray  = new DataArray(datos);
        assertDoesNotThrow(()-> dataArray.delete(elementoBorrar));
        int[] datosReal = dataArray.getColeccion();     //Array modificado en la función

        int sizeEsperado = datosEsperado.length;    //Número de elementos esperado
        int sizeReal = dataArray.size();     //Número de elementos real

        assertAll("C1_DataArray debe cambiar",
                ()-> assertArrayEquals(datosEsperado, datosReal),
                ()-> assertEquals(sizeEsperado, sizeReal)
        );
    }

    private static Stream<Arguments> testArgumentsC8() {
        return Stream.of(
                Arguments.of("\"No hay elementos en la colección\"", 8, new int[]{}),
                Arguments.of("\"El valor a borrar debe ser > 0\"", -5, new int[]{1, 3, 5, 7}),
                Arguments.of("\"Colección vacía. Y el valor a borrar debe ser > 0\"", 0, new int[]{}),
                Arguments.of("\"Elemento no encontrado\"", 8, new int[]{1, 3, 5, 7})
        );
    }

    private static Stream<Arguments> testArgumentsC9() {
        return Stream.of(
                Arguments.of(new int[]{1, 3, 7}, 5, new int[]{1, 3, 5, 7}),
                Arguments.of(new int[]{1, 3, 5, 7}, 3, new int[]{1, 3, 3, 5, 7}),
                Arguments.of(new int[]{1, 2, 3, 5, 6, 7, 8, 9, 10}, 4, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
        );
    }
}