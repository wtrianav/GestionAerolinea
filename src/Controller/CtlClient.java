
package Controller;

import Classes.Client;
import Model.ModelClient;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author wtrianav
 */
public class CtlClient {
    
    //Este atributo representa la conexión desde la capa controladora hacia la capa modelo (ModelClient).
    ModelClient modelClient;
    
    public CtlClient() {
        //Se instancia el objeto por medio del cual se tiene acceso oficialmente a la capa modelo
        modelClient = new ModelClient();
    }
    
    /*
    * Se indican TODOS los métodos del CRUD, donde se procesa la información que llega
    * como parámetros desde la capa View, se crean los objetos (se procesa la información)
    * y posteriormente se invoca a la capa modelo.
    */
    
    public boolean create(int code, String address, String email, String passport, String type, String id, 
                          String name, String lastName, String phoneNumber, List<Client> listClients) {
   
        Client client = new Client(code, address, email, passport, type, id, name, lastName, phoneNumber);
        return modelClient.create(client, listClients);
    }
    
    public Client read(String id) {
        Client client = modelClient.read(id);
        return client;
    }
    
    public List<Client> read() {
        List<Client> listClients = modelClient.read();
        return listClients;
    }
    
    public boolean update(int code, String address, String email, String passport, String type, String id, 
                          String name, String lastName, String phoneNumber) {
        
        Client client = new Client(code, address, email, passport, type, id, name, lastName, phoneNumber);
        return modelClient.update(client);
    }
    
    public boolean delete(String id) {
        //Como eliminar la información es una operación delicada, es importante
        //Confirmar nuevamente con el usuario si está seguro de la acción que pretende llevar a cabo
        //0: Si
        //1: No
        //2: Cancelar
        int respuesta = JOptionPane.showConfirmDialog(null, "Está seguro que desea eliminar al cliente con la cédula " + id + "?");
        
        if (respuesta == 0) {
            return modelClient.delete(id);
        }
        
        return false;
    }    
    
}
    
