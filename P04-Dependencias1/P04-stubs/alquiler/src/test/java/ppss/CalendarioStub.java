package ppss;

import java.time.LocalDate;

public class CalendarioStub extends Calendario {
    @Override
    public boolean es_festivo(LocalDate dia) throws CalendarioException {
        String fecha = dia.toString();
        if(fecha.equals("2024-06-20") || fecha.equals("2024-06-24")) {
            return true;
        } else if(fecha.equals("2024-04-18") || fecha.equals("2024-04-21") || fecha.equals("2024-04-22")) {
            throw new CalendarioException("Error en dia: " + fecha);
        }
        return false;
    }
}
