package ppss;

public class AlquilaCochesTestable extends AlquilaCoches {
    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    IService servicio;

    @Override
    public IService getServicio() {
        return servicio;
    }

    public void setServicio(IService servicio) {
        this.servicio = servicio;
    }
}
