package ppss;

public class Factoria {
    private static IOperacionBO operacion = null;

    public static IOperacionBO create() {
        if(operacion != null) {
            return operacion;
        }
        return new Operacion();
    }

    static void setOperacion(IOperacionBO op) {
        operacion = op;
    }
}
