 package ppss;

import org.dbunit.Assertion;
import static org.junit.jupiter.api.Assertions.*;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.util.fileloader.FlatXmlDataFileLoader;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

 /* IMPORTANTE:
     Dado que prácticamente todos los métodos de dBUnit lanzan una excepción,
     vamos a usar "throws Esception" en los métodos, para que el código quede más
     legible sin necesidad de usar un try..catch o envolver cada sentencia dbUnit
     con un assertDoesNotThrow()
     Es decir, que vamos a primar la legibilidad de los tests.
     Si la SUT puede lanza una excepción, SIEMPRE usaremos assertDoesNotThrow para
     invocar a la sut cuando no esperemos que se lance dicha excepción (independientemente de que hayamos propagado las excepciones provocadas por dbunit).
 */
public class ClienteDAO_IT {
  
  private ClienteDAO clienteDAO; //SUT
  private IDatabaseTester databaseTester;
  private IDatabaseConnection connection;

  @BeforeEach
  public void setUp() throws Exception {

    String cadena_conexionDB = "jdbc:mysql://localhost:3306/DBUNIT?useSSL=false";
    databaseTester = new JdbcDatabaseTester("com.mysql.cj.jdbc.Driver",
            cadena_conexionDB, "root", "ppss");
    connection = databaseTester.getConnection();

    clienteDAO = new ClienteDAO();
  }

  @Test
  public void D1_insert_should_add_John_to_cliente_when_John_does_not_exist() throws Exception {
    Cliente cliente = new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();
    
    //invocamos a la sut
    Assertions.assertDoesNotThrow(()->clienteDAO.insert(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente"); 

    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);

   }

  @Test
  public void D2_delete_should_remove_John_from_cliente_when_John_is_in_table() throws Exception {
    Cliente cliente =  new Cliente(1,"John", "Smith");
    cliente.setDireccion("1 Main Street");
    cliente.setCiudad("Anycity");

    //inicializamos la BD
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-esperado.xml");
    databaseTester.setDataSet(dataSet);
    databaseTester.onSetup();

    //invocamos a la SUT
    Assertions.assertDoesNotThrow(()->clienteDAO.delete(cliente));

    //recuperamos los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    ITable actualTable = databaseDataSet.getTable("cliente");
    
    //creamos el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-init.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    Assertion.assertEquals(expectedTable, actualTable);
  }

  @Test
  public void D3_insert_should_return_duplicate_entry_client_alredy_exist() throws Exception {
    Cliente cliente = new Cliente(2,"Franklin", "Saint");
    cliente.setDireccion("1 Hide Street");
    cliente.setCiudad("Simcity");

    //Inicializar el dataSet
    IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-d3.xml");
    //Inyectar el dataset
    databaseTester.setDataSet(dataSet);
    //Inicializar la base de datos
    databaseTester.onSetup();
    //Invocar al SUT
    SQLException exception = Assertions.assertThrows(SQLException.class, ()->clienteDAO.insert(cliente));

    //Recuperar los datos de la BD después de invocar al SUT
    IDataSet databaseDataSet = connection.createDataSet();
    //Recuperar los datos de la tabla cliente
    ITable actualTable = databaseDataSet.getTable("cliente");

    //Crear el dataset con el resultado esperado
    IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-d3.xml");
    ITable expectedTable = expectedDataSet.getTable("cliente");

    String exceptionExpected = "Duplicate entry '2' for key 'cliente.PRIMARY'";

    assertAll("D3_insert debe devolver excepción y mantener la db igual",
            ()-> assertEquals(exceptionExpected, exception.getMessage()),
            ()-> Assertion.assertEquals(expectedTable, actualTable)
    );
  }

   @Test
   public void D4_delete_should_return_delete_failed_when_Franklin_is_not_in_table() throws Exception {
     Cliente cliente = new Cliente(3,"Paul", "Atreides");
     cliente.setDireccion("1 Sand Street");
     cliente.setCiudad("Nocity");

     //Inicializar el dataSet
     IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-d4.xml");
     //Inyectar el dataset
     databaseTester.setDataSet(dataSet);
     //Inicializar la base de datos
     databaseTester.onSetup();
     //Invocar al SUT
     SQLException exception = Assertions.assertThrows(SQLException.class, ()->clienteDAO.delete(cliente));

     //Recuperar los datos de la BD después de invocar al SUT
     IDataSet databaseDataSet = connection.createDataSet();
     //Recuperar los datos de la tabla cliente
     ITable actualTable = databaseDataSet.getTable("cliente");

     //Crear el dataset con el resultado esperado
     IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-d4.xml");
     ITable expectedTable = expectedDataSet.getTable("cliente");

     String exceptionExpected = "Delete failed!";

     assertAll("D4_delete debe devolver excepción y mantener la db igual",
             ()-> assertEquals(exceptionExpected, exception.getMessage()),
             ()-> Assertion.assertEquals(expectedTable, actualTable)
     );
   }

   @Test
   public void D5_update_should_change_John_when_are_the_same_ids() throws Exception {
     Cliente cliente = new Cliente(1,"John", "Smith");
     cliente.setDireccion("Other Street");
     cliente.setCiudad("NewCity");

     //Inicializar el dataSet
     IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-d5.xml");
     //Inyectar el dataset
     databaseTester.setDataSet(dataSet);
     //Inicializar la base de datos
     databaseTester.onSetup();
     //Invocar al SUT
     Assertions.assertDoesNotThrow(()->clienteDAO.update(cliente));

     //Recuperar los datos de la BD después de invocar al SUT
     IDataSet databaseDataSet = connection.createDataSet();
     //Recuperar los datos de la tabla cliente
     ITable actualTable = databaseDataSet.getTable("cliente");

     //Crear el dataset con el resultado esperado
     IDataSet expectedDataSet = new FlatXmlDataFileLoader().load("/cliente-esperado-d5.xml");
     ITable expectedTable = expectedDataSet.getTable("cliente");

     Assertion.assertEquals(expectedTable, actualTable);
   }

   @Test
   public void D6_retrieve_should_John_when_id_exists() throws Exception {
     int clienteID = 1;

     //Inicializar el dataSet
     IDataSet dataSet = new FlatXmlDataFileLoader().load("/cliente-init-d6.xml");
     //Inyectar el dataset
     databaseTester.setDataSet(dataSet);
     //Inicializar la base de datos
     databaseTester.onSetup();
     //Invocar al SUT
     Cliente clienteReal = Assertions.assertDoesNotThrow(()->clienteDAO.retrieve(clienteID));

     Cliente clienteEsperado = new Cliente(1, "John", "Smith");
     clienteEsperado.setDireccion("1 Main Street");
     clienteEsperado.setCiudad("Anycity");

     assertAll("D6_retreive debe devolver el cliente de la base de datos",
             ()-> assertEquals(clienteEsperado.getId(), clienteReal.getId()),
             ()-> assertEquals(clienteEsperado.getNombre(), clienteReal.getNombre()),
             ()-> assertEquals(clienteEsperado.getApellido(), clienteReal.getApellido()),
             ()-> assertEquals(clienteEsperado.getDireccion(), clienteReal.getDireccion()),
             ()-> assertEquals(clienteEsperado.getCiudad(), clienteReal.getCiudad())
     );
   }
}
