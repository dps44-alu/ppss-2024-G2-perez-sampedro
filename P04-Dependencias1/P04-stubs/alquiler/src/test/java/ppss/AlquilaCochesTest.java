package ppss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AlquilaCochesTest {

    @Test
    public void C1_calculaPrecio_should_return_Ticket_75_when_tipo_TURISMO_and_festivo_false_for_10_days() {
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 5, 18);
        int numDias = 10;

        ServicioStub servicioStub = new ServicioStub();
        CalendarioStub calendarioStub = new CalendarioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable();

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

        ServicioStub servicioStub = new ServicioStub();
        CalendarioStub calendarioStub = new CalendarioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable();

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

        ServicioStub servicioStub = new ServicioStub();
        CalendarioStub calendarioStub = new CalendarioStub();
        AlquilaCochesTestable sut = new AlquilaCochesTestable();

        sut.setServicio(servicioStub);
        sut.setCalendario(calendarioStub);

        MensajeException exception = assertThrows(MensajeException.class, () -> sut.calculaPrecio(tipo, fecha, numDias));

        assertEquals("Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ", exception.getMessage());
    }
}