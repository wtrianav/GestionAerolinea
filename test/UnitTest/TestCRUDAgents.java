
package UnitTest;

import Classes.Agent;
import Model.ModelAgent;
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
public class TestCRUDAgents {
    
    public TestCRUDAgents() {
    }
        
    //Declaramos el puente hacia la capa modelo, ya que allá­ se encuentran los métodos
    //Que vamos a 'testear' en esta clase de pruebas
    private static ModelAgent modelAgent;
    
    
    //Indica las precondiciones necesarias para la ejecución de los tests
    //BeforeClass: Lo que se necesita ANTES de la ejecución de LOS TESTS
    
    @BeforeClass
    public static void setUpClass() {
        
    //System.out.println("Lo que se necesita ANTES de la ejecución de LOS TESTS\n");
        
    //Para todos los tests, antes de su ejecución, CREAMOS el puente hacia la capa modelo
    modelAgent = new ModelAgent();
    
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
    public void testCreateAgent() {
        List<Agent> listAgents = new ArrayList<>();
        Agent agent = new Agent(2070, "Coordinador", "10545632356", "[TEST] Prueba", "[TEST] Prueba", "3503262363");
 
        boolean created = modelAgent.create(agent, listAgents);
        //Esta lí­nea le i­ndica a JUnit el valor a partir del cual, determinará si el test
        //fue exitoso o no, en este caso el valor de la variable booleana 'created'
        assertTrue("El agente NO fue creado", created);
        
        Agent finded = modelAgent.read("10545632356");
        assertNotNull("El agente NO fue creado", finded);
         
        finded.setName("[TEST] Actualizado");
        boolean updated = modelAgent.update(finded);
        assertTrue("El agente NO fue actualizado", updated);
        
        boolean deleted = modelAgent.delete(finded.getId());
        assertTrue("El agente NO fue creado", deleted);
        
        finded = modelAgent.read("10545632356");
        assertNull("El agente fue creado", finded);
        
    }
    
    //Este test actualiza el agente creado en el test anterior testCreateAgent.
    @Test
    public void testUpdateAgent() {
        List<Agent> listAgents = new ArrayList<>();
        int numAgentsBefore = modelAgent.read().size();
        
        Agent agent = new Agent(2071, "Asesor Administrativo", "10608524152", "[TEST] Prueba", "[TEST] Prueba", "3503262363");
        
        boolean created = modelAgent.create(agent, listAgents);
        assertTrue("El agente NO fue creado", created);
        
        int numAgentsAfter = modelAgent.read().size();
        assertEquals("", numAgentsBefore + 1, numAgentsAfter);
        
        Agent finded = modelAgent.read("10608524152");
        assertNotNull("El agente NO fue creado", finded);
        
        boolean deleted = modelAgent.delete(finded.getId());
        assertTrue("El agente NO fue creado", deleted);
        
        
    }
}
