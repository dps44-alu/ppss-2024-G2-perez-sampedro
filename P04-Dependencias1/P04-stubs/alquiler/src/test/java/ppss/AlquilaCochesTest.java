package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AlquilaCochesTest {

    ServicioStub servicioStub;
    CalendarioStub calendarioStub;
    AlquilaCochesTestable sut;

    @BeforeEach
    public void setUp() {
        servicioStub = new ServicioStub();
        calendarioStub = new CalendarioStub();
        sut = new AlquilaCochesTestable();
    }

    @Test
    public void C1_calculaPrecio_should_return_Ticket_75_when_tipo_TURISMO_and_festivo_false_for_10_days() {
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 5, 18);
        int numDias = 10;

        sut.setServicio(servicioStub);
        sut.setCalendario(calendarioStub);

        Ticket ticket = assertDoesNotThrow(() -> sut.calculaPrecio(tipo, fecha, numDias));

        float resultadoEsperado = 75;
        float resultadoReal = ticket.getPrecio_final();

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void C2_calculaPrecio_should_return_Ticket_62_5_when_tipo_CARAVANA_and_festivo_false_for_5_days_and_true_for_2_days() {
        TipoCoche tipo = TipoCoche.CARAVANA;
        LocalDate fecha = LocalDate.of(2024, 6, 19);
        int numDias = 7;

        ArrayList<LocalDate> festivos = new ArrayList<>();
        festivos.add(LocalDate.of(2024, 6, 20));
        festivos.add(LocalDate.of(2024, 6, 24));

        calendarioStub.setFestivos(festivos);

        sut.setServicio(servicioStub);
        sut.setCalendario(calendarioStub);

        Ticket ticket = assertDoesNotThrow(() -> sut.calculaPrecio(tipo, fecha, numDias));

        float resultadoEsperado = 62.5f;
        float resultadoReal = ticket.getPrecio_final();

        assertEquals(resultadoEsperado, resultadoReal);
    }

    @Test
    public void C3_calculaPrecio_should_return_MensajeException_when_april_18_21_22() {
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 4, 17);
        int numDias = 8;

        ArrayList<LocalDate> excepciones = new ArrayList<>();
        excepciones.add(LocalDate.of(2024, 4, 18));
        excepciones.add(LocalDate.of(2024, 4, 21));
        excepciones.add(LocalDate.of(2024, 4, 22));

        calendarioStub.setExcepciones(excepciones);

        sut.setServicio(servicioStub);
        sut.setCalendario(calendarioStub);

        MensajeException exception = assertThrows(MensajeException.class, () -> sut.calculaPrecio(tipo, fecha, numDias));

        assertEquals("Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ", exception.getMessage());
    }
}