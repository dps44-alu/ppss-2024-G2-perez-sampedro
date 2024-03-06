package ppss;

import ppss.excepciones.*;

public class OperacionStub implements IOperacionBO {
    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, SocioInvalidoException {
        if(!socio.equals("Luis")) {
            throw new SocioInvalidoException();
        }
        if(!isbn.equals("11111") && !isbn.equals("22222") ) {
            throw new IsbnInvalidoException();
        }
    }
}
