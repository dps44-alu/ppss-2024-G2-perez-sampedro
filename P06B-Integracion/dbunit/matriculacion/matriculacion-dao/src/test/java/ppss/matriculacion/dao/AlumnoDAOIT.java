package ppss.matriculacion.dao;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;
import ppss.matriculacion.to.AlumnoTO;

import java.time.LocalDate;
import java.time.Month;

@Tag("Integracion-Fase1")
public class AlumnoDAOIT {

    private AlumnoTO alumno;
    private IDatabaseTester databaseTester;
    private IDatabaseConnection connection;

    @BeforeEach
    public void setUp() throws Exception {

        String cadena_conexionDB = "jdbc:mysql://localhost:3306/matriculacion?useSSL=false";
        databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
                cadena_conexionDB, "root", "ppss");
        connection = databaseTester.getConnection();
        alumno = new AlumnoTO();

        //inicializamos la BD
        IDataSet dataSet = new FlatXmlDataFileLoader().load("/tabla2.xml");
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @Test
    public void testA1() throws Exception {
        alumno.setNif("33333333C");
        alumno.setNombre("Elena Alguirre Juarez");
        alumno.setFechaNacimiento(LocalDate.of(1985, Month.FEBRUARY, 22));

        //invocamos a la sut
        Assertions.assertDoesNotThrow(()-> new FactoriaDAO().getAlumnoDAO().addAlumno(alumno));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla3.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testA2() throws Exception {
        alumno.setNif("11111111A");
        alumno.setNombre("Alfonso Ramirez Ruiz");
        alumno.setFechaNacimiento(LocalDate.of(1982, Month.FEBRUARY, 22));

        //invocamos a la sut
        DAOException exception = Assertions.assertThrows(DAOException.class,
                ()-> new FactoriaDAO().getAlumnoDAO().addAlumno(alumno));

        String esperado = "Error al conectar con BD";

        Assertions.assertEquals(esperado, exception.getMessage());
    }

    @Test
    public void testA3() throws Exception {
        alumno.setNif("44444444D");
        alumno.setNombre(null);
        alumno.setFechaNacimiento(LocalDate.of(1982, Month.FEBRUARY, 22));

        //invocamos a la sut
        DAOException exception = Assertions.assertThrows(DAOException.class,
                ()-> new FactoriaDAO().getAlumnoDAO().addAlumno(alumno));

        String esperado = "Error al conectar con BD";

        Assertions.assertEquals(esperado, exception.getMessage());
    }

    @Test
    public void testA4() throws Exception {
        alumno = null;

        //invocamos a la sut
        DAOException exception = Assertions.assertThrows(DAOException.class,
                ()-> new FactoriaDAO().getAlumnoDAO().addAlumno(alumno));

        String esperado = "Alumno nulo";

        Assertions.assertEquals(esperado, exception.getMessage());
    }

    @Test
    public void testA5() throws Exception {
        alumno.setNif(null);
        alumno.setNombre("Pedro Gracia Lopez");
        alumno.setFechaNacimiento(LocalDate.of(1982, Month.FEBRUARY, 22));

        //invocamos a la sut
        DAOException exception = Assertions.assertThrows(DAOException.class,
                ()-> new FactoriaDAO().getAlumnoDAO().addAlumno(alumno));

        String esperado = "Error al conectar con BD";

        Assertions.assertEquals(esperado, exception.getMessage());
    }

    @Test
    public void testB1() throws Exception {
        String nif = "11111111A";

        //invocamos a la sut
        Assertions.assertDoesNotThrow(()-> new FactoriaDAO().getAlumnoDAO().delAlumno(nif));

        //recuperamos los datos de la BD después de invocar al SUT
        IDataSet databaseDataSet = connection.createDataSet();
        ITable actualTable = databaseDataSet.getTable("alumnos");

        //creamos el dataset con el resultado esperado
        IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/tabla4.xml");
        ITable expectedTable = expectedDataSet.getTable("alumnos");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testB2() throws Exception {
        String nif = "33333333C";

        //invocamos a la sut
        DAOException exception = Assertions.assertThrows(DAOException.class,
                ()-> new FactoriaDAO().getAlumnoDAO().delAlumno(nif));

        String esperado = "No se ha borrado ningun alumno";

        Assertions.assertEquals(esperado, exception.getMessage());
    }
}
