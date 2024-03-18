package ppss;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarioStub extends Calendario {
    ArrayList<LocalDate> festivos = new ArrayList<>();
    ArrayList<LocalDate> excepciones = new ArrayList<>();

    public void setFestivos(ArrayList<LocalDate> festivos) {
        this.festivos = festivos;
    }

    public void setExcepciones(ArrayList<LocalDate> excepciones) {
        this.excepciones = excepciones;
    }

    @Override
    public boolean es_festivo(LocalDate dia) throws CalendarioException {
        if(festivos.contains(dia)) {
            return true;
        } else if(excepciones.contains(dia)) {
            throw new CalendarioException("Error en dia: " + dia.toString());
        }
        return false;
    }
}
