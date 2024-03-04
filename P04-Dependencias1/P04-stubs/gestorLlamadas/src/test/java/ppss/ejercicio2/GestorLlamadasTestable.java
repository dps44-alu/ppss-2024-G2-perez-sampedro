package ppss.ejercicio2;

public class GestorLlamadasTestable extends GestorLlamadas {
    private Calendario c;

    public void setCalendario(Calendario c) {
        this.c = c;
    }

    @Override
    public Calendario getCalendario() {
        return c;
    }
}
