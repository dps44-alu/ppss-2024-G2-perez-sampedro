package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

import javax.swing.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ReservaMockTest {
    IMocksControl ctr;
    Reserva sut;
    FactoriaBOs stubFactoria;
    IOperacionBO stubOperacion;

    @BeforeEach
    public void setUp() {
        ctr = EasyMock.createStrictControl();

        sut = EasyMock.partialMockBuilder(Reserva.class)
                        .addMockedMethod("compruebaPermisos")
                        .mock(ctr);

        stubFactoria = ctr.createMock(FactoriaBOs.class);

        stubOperacion = ctr.createMock(IOperacionBO.class);
    }

    @Test
    public void C1_realizaReserva_should_return_ReservaException1_when_login_and_password_xxxx() {
        String login, password = login = "xxxx";
        String socio = "Pepe";
        String[] isbns = {"33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        EasyMock.expect(sut.compruebaPermisos(login, password, usuario))
                .andReturn(false);

        ctr.replay();

        String resultadoEsperado = "ERROR de permisos; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
        ctr.verify();
    }

    @Test
    public void C2_realizaReserva_should_return_void_when_login_and_password_ppss_socio_Pepe_and_isbns_22222_33333() {
        String login, password = login = "ppss";
        String socio = "Pepe";
        String[] isbns = {"22222", "33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        EasyMock.expect(sut.compruebaPermisos(login, password, usuario))
                .andReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                        .andReturn(stubOperacion);

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[0]));
        EasyMock.expectLastCall()
                .asStub();  //void

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[1]));
        EasyMock.expectLastCall()
                .asStub();  //void

        ctr.replay();

        assertDoesNotThrow(()-> sut.realizaReserva(login, password, socio, isbns));

        ctr.verify();
    }

    @Test
    public void C3_realizaReserva_should_return_ReservaException2_when_login_and_password_ppss_socio_Pepe_and_isbn_11111_55555() {
        String login, password = login = "ppss";
        String socio = "Pepe";
        String[] isbns = {"11111", "22222", "55555"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        EasyMock.expect(sut.compruebaPermisos(login, password, usuario))
                .andReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                .andReturn(stubOperacion);

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(socio, isbns[0]));
        EasyMock.expectLastCall()
                .andThrow(new IsbnInvalidoException());

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[1]));
        EasyMock.expectLastCall()
                .asStub();  //void

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(socio, isbns[2]));
        EasyMock.expectLastCall()
                .andThrow(new IsbnInvalidoException());

        ctr.replay();

        String resultadoEsperado = "ISBN invalido:11111; ISBN invalido:55555; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
        ctr.verify();
    }

    @Test
    public void C4_realizaReserva_should_return_ReservaException3_when_login_and_password_ppss_socio_Luis_and_isbns_22222() {
        String login, password = login = "ppss";
        String socio = "Luis";
        String[] isbns = {"22222"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        EasyMock.expect(sut.compruebaPermisos(login, password, usuario))
                .andReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                .andReturn(stubOperacion);

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(socio, isbns[0]));
        EasyMock.expectLastCall()
                .andThrow(new SocioInvalidoException());

        ctr.replay();

        String resultadoEsperado = "SOCIO invalido; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
        ctr.verify();
    }

    @Test
    public void C5_realizaReserva_should_return_ReservaException4_when_login_and_password_ppss_socio_Pepe_and_isbns_11111_33333_and_JDBCException() {
        String login, password = login = "ppss";
        String socio = "Luis";
        String[] isbns = {"11111", "22222", "33333"};
        Usuario usuario = Usuario.BIBLIOTECARIO;

        EasyMock.expect(sut.compruebaPermisos(login, password, usuario))
                .andReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                .andReturn(stubOperacion);

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(socio, isbns[0]));
        EasyMock.expectLastCall()
                .andThrow(new IsbnInvalidoException());

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(socio, isbns[1]));
        EasyMock.expectLastCall()
                .asStub();  //void

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(socio, isbns[2]));
        EasyMock.expectLastCall()
                .andThrow(new JDBCException());

        ctr.replay();

        String resultadoEsperado = "ISBN invalido:11111; CONEXION invalida; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
        ctr.verify();
    }
}