package ppss;

import ppss.excepciones.*;

public class OperacionStubJDBC implements IOperacionBO {
    @Override
    public void operacionReserva(String socio, String isbn) throws JDBCException {
        if(isbn.equals("22222")) {
            throw new JDBCException();
        }
    }
}
