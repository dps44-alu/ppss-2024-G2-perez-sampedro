package ppss;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.junit.jupiter.api.Assertions.*;

class ReservaStubTest {
    Reserva sut;
    FactoriaBOs stubFactoria;
    IOperacionBO stubOperacion;

    @BeforeEach
    public void setUp() {
        sut = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .mock();

        stubFactoria = EasyMock.niceMock(FactoriaBOs.class);

        stubOperacion = EasyMock.niceMock(IOperacionBO.class);
    }

    @Test
    public void C1_realizaReserva_should_return_ReservaException1_when_login_and_password_xxxx() {
        String login, password = login = "xxxx";
        String socio = "Pepe";
        String[] isbns = new String[] {"22222"};


        EasyMock.expect(sut.compruebaPermisos(anyString(), anyString(), anyObject()))
                .andStubReturn(false);

        EasyMock.replay(sut);

        String resultadoEsperado = "ERROR de permisos; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
    }

    @Test
    public void C2_realizaReserva_should_return_void_when_login_and_password_ppss_socio_Pepe_and_isbns_22222_33333() {
        String login, password = login = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"22222", "33333"};

        EasyMock.expect(sut.compruebaPermisos(anyString(), anyString(), anyObject()))
                .andStubReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                .andStubReturn(stubOperacion);

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(anyString(), anyString()));     //isbns[0]
        EasyMock.expectLastCall()
                .asStub();  //void

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(anyString(), anyString()));     //isbns[1]
        EasyMock.expectLastCall()
                .asStub();  //void

        EasyMock.replay(sut, stubFactoria, stubOperacion);

        assertDoesNotThrow(()-> sut.realizaReserva(login, password, socio, isbns));
    }

    @Test
    public void C3_realizaReserva_should_return_ReservaException2_when_login_and_password_ppss_socio_Pepe_and_isbn_11111_55555() {
        String login, password = login = "ppss";
        String socio = "Pepe";
        String[] isbns = new String[] {"11111", "22222", "55555"};

        EasyMock.expect(sut.compruebaPermisos(anyString(), anyString(), anyObject()))
                .andStubReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                .andStubReturn(stubOperacion);

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(anyString(), anyString()));  //isbns[0]
        EasyMock.expectLastCall()
                .andThrow(new IsbnInvalidoException());

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(anyString(), anyString())); //isbns[1]
        EasyMock.expectLastCall()
                .asStub();  //void

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(anyString(), anyString()));  //isbns[2]
        EasyMock.expectLastCall()
                .andThrow(new IsbnInvalidoException());

        EasyMock.replay(sut, stubFactoria, stubOperacion);

        String resultadoEsperado = "ISBN invalido:11111; ISBN invalido:55555; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
    }

    @Test
    public void C4_realizaReserva_should_return_ReservaException3_when_login_and_password_ppss_socio_Luis_and_isbns_22222() {
        String login, password = login = "ppss";
        String socio = "Luis";
        String[] isbns = new String[] {"22222"};

        EasyMock.expect(sut.compruebaPermisos(anyString(), anyString(), anyObject()))
                .andStubReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                .andStubReturn(stubOperacion);

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(anyString(), anyString()));  //isbns[0]
        EasyMock.expectLastCall()
                .andThrow(new SocioInvalidoException());

        EasyMock.replay(sut, stubFactoria, stubOperacion);

        String resultadoEsperado = "SOCIO invalido; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
    }

    @Test
    public void C5_realizaReserva_should_return_ReservaException4_when_login_and_password_ppss_socio_Pepe_and_isbns_11111_33333_and_JDBCException() {
        String login, password = login = "ppss";
        String socio = "Luis";
        String[] isbns = new String[] {"11111", "22222", "33333"};

        EasyMock.expect(sut.compruebaPermisos(anyString(), anyString(), anyObject()))
                .andStubReturn(true);

        sut.setFactoria(stubFactoria);

        EasyMock.expect(stubFactoria.getOperacionBO())
                .andStubReturn(stubOperacion);

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(anyString(), anyString()));  //isbns[0]
        EasyMock.expectLastCall()
                .andThrow(new IsbnInvalidoException());

        assertDoesNotThrow(() -> stubOperacion.operacionReserva(anyString(), anyString())); //isbns[1]
        EasyMock.expectLastCall()
                .asStub();  //void

        assertDoesNotThrow(()-> stubOperacion.operacionReserva(anyString(), anyString()));  //isbns[2]
        EasyMock.expectLastCall()
                .andThrow(new JDBCException());

        EasyMock.replay(sut, stubFactoria, stubOperacion);

        String resultadoEsperado = "ISBN invalido:11111; CONEXION invalida; ";
        ReservaException exception = assertThrows(ReservaException.class, ()-> sut.realizaReserva(login, password, socio, isbns));

        assertEquals(resultadoEsperado, exception.getMessage());
    }
}