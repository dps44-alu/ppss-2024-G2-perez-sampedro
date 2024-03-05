package ppss;

public class ServicioStub implements IService {
    @Override
    public float consultaPrecio(TipoCoche tipo) {
        return 10;  //Solo se contemplan TURISMO y CARAVANA
    }
}
