package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.ReservaException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    ReservaTestable sut;
    OperacionStub stub;

    @BeforeEach
    public void setUp() {
        sut = new ReservaTestable();
        stub = new OperacionStub();
    }

    @Test
    public void C1_realizaReserva_should_return_ReservaException_when_login_and_pasword_xxxx() {
        String login = "xxxx";
        String password = "xxxx";
        String socio = "Luis";
        String[] isbns = {"11111"};

        ReservaException exception = assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        assertEquals("ERROR de permisos; ", exception.getMessage());
    }

    @Test
    public void C2_realizaReserva_should_return_void_when_login_and_password_ppss_and_socio_Luis_and_isbns_11111_22222() {
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = {"11111", "22222"};

        ArrayList<String> sociosValidos = new ArrayList<>();
        sociosValidos.add(socio);

        ArrayList<String> isbnsValidos = new ArrayList<>(Arrays.asList("11111", "22222"));

        stub.setSociosValidos(sociosValidos);
        stub.setIsbnsValidos(isbnsValidos);

        Factoria.setOperacion(stub);

        assertDoesNotThrow(() -> sut.realizaReserva(login, password, socio, isbns));
    }

    @Test
    public void C3_realizaReserva_should_return_ReservaException_when_isbns_33333_44444() {
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = {"11111", "33333", "44444"};

        ArrayList<String> sociosValidos = new ArrayList<>();
        sociosValidos.add(socio);

        ArrayList<String> isbnsValidos = new ArrayList<>();
        isbnsValidos.add("11111");

        stub.setSociosValidos(sociosValidos);
        stub.setIsbnsValidos(isbnsValidos);

        Factoria.setOperacion(stub);

        ReservaException exception = assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        assertEquals("ISBN invalido:33333; ISBN invalido:44444; ", exception.getMessage());
    }

    @Test
    public void C4_realizaReserva_should_return_ReservaException_when_socio_Pepe() {
        String login = "ppss";
        String password = "ppss";
        String socio = "Pepe";
        String[] isbns = {"11111"};

        ArrayList<String> isbnsValidos = new ArrayList<>();
        isbnsValidos.add("11111");

        stub.setIsbnsValidos(isbnsValidos);

        Factoria.setOperacion(stub);

        ReservaException exception = assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        assertEquals("SOCIO invalido; ", exception.getMessage());
    }

    @Test
    public void C5_realizaReserva_should_return_ReservaException_when_not_valid_connection() {
        String login = "ppss";
        String password = "ppss";
        String socio = "Luis";
        String[] isbns = {"11111", "22222"};

        ArrayList<String> sociosValidos = new ArrayList<>();
        sociosValidos.add(socio);

        ArrayList<String> isbnsValidos = new ArrayList<>(Arrays.asList("11111", "22222"));

        ArrayList<String> fallosConexion = new ArrayList<>();
        fallosConexion.add("22222");

        stub.setSociosValidos(sociosValidos);
        stub.setIsbnsValidos(isbnsValidos);
        stub.setFalloConexion(fallosConexion);

        Factoria.setOperacion(stub);

        ReservaException exception = assertThrows(ReservaException.class, () -> sut.realizaReserva(login, password, socio, isbns));

        assertEquals("CONEXION invalida; ", exception.getMessage());
    }
}