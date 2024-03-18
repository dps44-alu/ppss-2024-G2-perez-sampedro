package ppss;

import ppss.excepciones.*;

import java.util.ArrayList;

public class OperacionStub implements IOperacionBO {
    ArrayList<String> falloConexion = new ArrayList<>();
    ArrayList<String> sociosValidos = new ArrayList<>();
    ArrayList<String> isbnsValidos = new ArrayList<>();

    public void setFalloConexion(ArrayList<String> falloConexion) {
        this.falloConexion = falloConexion;
    }

    public void setSociosValidos(ArrayList<String> sociosValidos) {
        this.sociosValidos = sociosValidos;
    }

    public void setIsbnsValidos(ArrayList<String> isbnsValidos) {
        this.isbnsValidos = isbnsValidos;
    }

    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, SocioInvalidoException, JDBCException {
        if (!sociosValidos.contains(socio)) {
            throw new SocioInvalidoException();
        } else if (!isbnsValidos.contains(isbn)) {
            throw new IsbnInvalidoException();
        } else if (falloConexion.contains(isbn)) {
            throw new JDBCException();
        }
    }
}
