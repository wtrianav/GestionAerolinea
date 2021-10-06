
package Controller;

import Classes.Flight;
import Model.ModelFlight;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author wtrianav
 */
public class CtlFlight {
    
        //Este atributo representa la conexión desde la capa controladora hacia la capa modelo (ModelFlight).
    ModelFlight modelFlight;
    
    public CtlFlight() {
        //Se instancia el objeto por medio del cual se tiene acceso oficialmente a la capa modelo
        modelFlight = new ModelFlight();
    }
    
    /*
    * Se indican TODOS los métodos del CRUD, donde se procesa la información que llega
    * como parámetros desde la capa View (vista), se crean los objetos (se procesa la información)
    * y posteriormente se invoca a la capa modelo.
    */
    
    public boolean create(String code, String origin, String destination, String departureTime, 
                          String arrivalTime, String type, List<Flight> listFlights) {
   
        Flight flight = new Flight(code, origin, destination, departureTime, arrivalTime, type);
        return modelFlight.create(flight, listFlights);
    }
    
    public Flight read(String code) {
        Flight flight = modelFlight.read(code);
        return flight;
    }
    
    public List<Flight> read() {
        List<Flight> listFlights = modelFlight.read();
        return listFlights;
    }
    
    public boolean update(String code, String origin, String destination, String departureTime, 
                          String arrivalTime, String type) {
        
        Flight flight = new Flight(code, origin, destination, departureTime, arrivalTime, type);
        return modelFlight.update(flight);
    }
    
    public boolean delete(String code) {
        //Como eliminar la información es una operación delicada, es importante
        //Confirmar nuevamente con el usuario si está seguro de la acción que pretende llevar a cabo
        //0: Si
        //1: No
        //2: Cancelar
        int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar el vuelo " + code + "?");
        
        if (respuesta == 0) {
            return modelFlight.delete(code);
        }
        
        return false;
    }  
    
}
