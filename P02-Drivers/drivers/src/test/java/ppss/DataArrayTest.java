package ppss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataArrayTest {

    DataArray dataArray;

    @Test
    public void C1_delete_should_delete_5_when_delete_5_and_there_is_one_5() {
        int[] datos = {1, 3, 5, 7};
        int[] datosEsperado = {1, 3, 7};    //Array modificado esperado

        dataArray = new DataArray(datos);   //Array que modifica la función
        try {
            dataArray.delete(5);
        } catch(DataException e) {
            Assertions.fail("DataException lanzada");   //Control de la excepción
        }
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

        dataArray  = new DataArray(datos);
        try {
            int elementoBorrar = 4;     //Elemento a borrar
            dataArray.delete(elementoBorrar);
        } catch(DataException e) {
            Assertions.fail("DataException lanzada");   //Control de la excepción

        }
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

        dataArray = new DataArray(datos);   //Array que modifica la función
        try {
            int elementoBorrar = 4;     //Elemento a borrar
            dataArray.delete(elementoBorrar);
        } catch(DataException e) {
            Assertions.fail("DataException lanzada");   //Control de la excepción
        }
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

    }
}