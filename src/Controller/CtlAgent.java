
package Controller;

import Classes.Agent;
import Model.ModelAgent;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author wtrianav
 */
public class CtlAgent {
        
    //Este atributo representa la conexión desde la capa controladora hacia la capa modelo (ModelAgent).
    ModelAgent modelAgent;
    
    public CtlAgent() {
        //Se instancia el objeto por medio del cual se tiene acceso oficialmente a la capa modelo
        modelAgent = new ModelAgent();
    }
    
    /*
    * Se indican TODOS los métodos del CRUD, donde se procesa la información que llega
    * como parámetros desde la capa View, se crean los objetos (se procesa la información)
    * y posteriormente se invoca a la capa modelo.
    */
    
    public boolean create(int code, String position, String id, String name, String lastName, 
                          String phoneNumber, List<Agent> listAgents) {
   
        Agent agent = new Agent(code, position, id, name, lastName, phoneNumber);
        return modelAgent.create(agent, listAgents);
    }
    
    public Agent read(String id) {
        Agent agent = modelAgent.read(id);
        return agent;
    }
    
    public List<Agent> read() {
        List<Agent> listAgents = modelAgent.read();
        return listAgents;
    }
    
    public boolean update(int code, String position, String id, String name, String lastName, 
                          String phoneNumber) {
        
        Agent agent = new Agent(code, position, id, name, lastName, phoneNumber);
        return modelAgent.update(agent);
    }
    
    public boolean delete(String id) {
        //Como eliminar la información es una operación delicada, es importante
        //Confirmar nuevamente con el usuario si está seguro de la acción que pretende llevar a cabo
        //0: Si
        //1: No
        //2: Cancelar
        int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar al agente con la cédula " + id + "?");
        
        if (respuesta == 0) {
            return modelAgent.delete(id);
        }
        
        return false;
    }  
    
}
