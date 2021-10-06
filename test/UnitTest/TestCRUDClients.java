
package UnitTest;

import Classes.Client;
import Model.ModelClient;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author wtrianav
 */
public class TestCRUDClients {
    
    public TestCRUDClients() {
    }
        
    //Declaramos el puente hacia la capa modelo, ya que allá­ se encuentran los métodos
    //Que vamos a 'testear' en esta clase de pruebas
    private static ModelClient modelClient;
    
    
    //Indica las precondiciones necesarias para la ejecución de los tests
    //BeforeClass: Lo que se necesita ANTES de la ejecución de LOS TESTS
    
    @BeforeClass
    public static void setUpClass() {
        
    //System.out.println("Lo que se necesita ANTES de la ejecución de LOS TESTS\n");
        
    //Para todos los tests, antes de su ejecución, CREAMOS el puente hacia la capa modelo
    modelClient = new ModelClient();
    
    }
    
    //Indica las acciones a realizar una vez finalizados LOS TESTS
    //AfterClass: Lo que se ejecuta DESPUÉS de la ejecución de LOS TESTS
    @AfterClass
    public static void tearDownClass() {
        //System.out.println("Lo que se ejecuta DESPUÉS de la ejecución de LOS TESTS");
    }
    
    //Indica las precondiciones necesarias para la ejecución de CADA TEST
    //Before: Lo que se necesita ANTES de la ejecución de CADA TEST
    @Before
    public void setUp() {
        //System.out.println("Lo que se necesita ANTES de la ejecución de CADA TEST");
    }
    
    //Indica las acciones a realizar una vez finalizado EL TEST
    //After: Lo que se ejecuta DESPUÉS de la ejecución DEL TEST
    @After
    public void tearDown() {
        //System.out.println("Lo que se ejecuta DESPUÉS de la ejecución DEL TEST\n");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testCreateClient() {
        List<Client> listClients = new ArrayList<>();
        Client client = new Client(4070, "Calle 104 # 50-60", "testing@gmail.com", "AM1503265", "Oro", "10535029270", "[TEST] Prueba", "[TEST] Prueba", "3215626352");

        boolean created = modelClient.create(client, listClients);
        //Esta lí­nea le i­ndica a JUnit el valor a partir del cual, determinará si el test
        //fue exitoso o no, en este caso el valor de la variable booleana 'created'
        assertTrue("El cliente NO fue creado", created);
        
        Client finded = modelClient.read("10535029270");
        assertNotNull("El cliente NO fue creado", finded);
         
        finded.setName("[TEST] Actualizado");
        boolean updated = modelClient.update(finded);
        assertTrue("El cliente NO fue actualizado", updated);
        
        boolean deleted = modelClient.delete(finded.getId());
        assertTrue("El cliente NO fue creado", deleted);
        
        finded = modelClient.read("10535029270");
        assertNull("El cliente fue creado", finded);
        
    }
    
    //Este test actualiza el cliente creado en el test anterior testCreateAgent.
    @Test
    public void testUpdateClient() {
        List<Client> listClients = new ArrayList<>();
        int numAgentsBefore = modelClient.read().size();
        
        Client client = new Client(4080, "Calle 104 # 50-60", "testing@gmail.com", "AM1503265", "Oro", "10905029390", "[TEST] Prueba", "[TEST] Prueba", "3215626352");
        
        boolean created = modelClient.create(client, listClients);
        assertTrue("El cliente NO fue creado", created);
        
        int numAgentsAfter = modelClient.read().size();
        assertEquals("", numAgentsBefore + 1, numAgentsAfter);
        
        Client finded = modelClient.read("10905029390");
        assertNotNull("El cliente NO fue creado", finded);
        
        boolean deleted = modelClient.delete(finded.getId());
        assertTrue("El cliente NO fue creado", deleted);
    }
}
