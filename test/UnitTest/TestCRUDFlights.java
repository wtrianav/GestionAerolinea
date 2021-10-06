
package UnitTest;

import Classes.Flight;
import Model.ModelFlight;
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
public class TestCRUDFlights {
    
    public TestCRUDFlights() {
    }
    
            
    //Declaramos el puente hacia la capa modelo, ya que allá­ se encuentran los métodos
    //Que vamos a 'testear' en esta clase de pruebas
    private static ModelFlight modelFlight;
    
    
    //Indica las precondiciones necesarias para la ejecución de los tests
    //BeforeClass: Lo que se necesita ANTES de la ejecución de LOS TESTS
    
    @BeforeClass
    public static void setUpClass() {
                
    //System.out.println("Lo que se necesita ANTES de la ejecución de LOS TESTS\n");
        
    //Para todos los tests, antes de su ejecución, CREAMOS el puente hacia la capa modelo
    modelFlight = new ModelFlight();
    
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
    public void testCreateFlight() {
        List<Flight> listFlights = new ArrayList<>();
        Flight flight = new Flight("5090", "New York", "Bogotá", "04:00:00", "09:00:00", "[TEST] Prueba");
 
        boolean created = modelFlight.create(flight, listFlights);
        //Esta lí­nea le i­ndica a JUnit el valor a partir del cual, determinará si el test
        //fue exitoso o no, en este caso el valor de la variable booleana 'created'
        assertTrue("El vuelo NO fue creado", created);
        
        Flight finded = modelFlight.read("5090");
        assertNotNull("El vuelo NO fue creado", finded);
         
        finded.setType("[TEST] Actualizado");
        boolean updated = modelFlight.update(finded);
        assertTrue("El vuelo NO fue actualizado", updated);
        
        boolean deleted = modelFlight.delete(finded.getCode());
        assertTrue("El vuelo NO fue creado", deleted);
        
        finded = modelFlight.read("5090");
        assertNull("El vuelo fue creado", finded);
        
    }
    
    //Este test actualiza el vuelo creado en el test anterior testCreateAgent.
    @Test
    public void testUpdateFlight() {
        List<Flight> listFlights = new ArrayList<>();
        int numAgentsBefore = modelFlight.read().size();
        
        Flight flight = new Flight("5027", "New York", "Bogotá", "04:00:00", "09:00:00", "[TEST] Prueba");
        
        boolean created = modelFlight.create(flight, listFlights);
        assertTrue("El vuelo NO fue creado", created);
        
        int numAgentsAfter = modelFlight.read().size();
        assertEquals("", numAgentsBefore + 1, numAgentsAfter);
        
        Flight finded = modelFlight.read("5027");
        assertNotNull("El vuelo NO fue creado", finded);
        
        boolean deleted = modelFlight.delete(finded.getCode());
        assertTrue("El vuelo NO fue creado", deleted);
    }
}
