
package Model;

import Classes.Client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author wtrianav
 */
public class ModelClient {
    
    //Declaramos un objeto de tipo 'Database' para acceder a los valores que necesito para conectarme a la bbdd.
    Database database;
    
    public ModelClient() {
        //Instanciamos ese objeto database
        database = new Database();
    }
    
    //Se está recibiendo el objeto desde el controlador (ctlClient) como parámetro.
    public boolean create(Client client, List<Client> listClients) {
        //Se agrega el cliente la tabla tableClient.
        listClients.add(client);
        
        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //(?, ?, ?) Campos anónimos que estableceremos más adelante
                String query = "INSERT INTO tbl_person (id, name, last_name, phone_number) VALUES (?, ?, ?, ?)";
                
                //Objeto por medio del cual se prepara la sentencia SQL y se indica la query a inyectar.
                PreparedStatement pstPerson = connection.prepareStatement(query);
                
                //Se establecen los valores para los campos anónimos
                pstPerson.setString(1, client.getId());
                pstPerson.setString(2, client.getName());
                pstPerson.setString(3, client.getLastName());
                pstPerson.setString(4, client.getPhoneNumber());
                
                                
                //executeUpdate(): Inyecta la sentencia SQL hacia el motor de base de datos
                //0: NO fue exitosa la inserción del registro en la tabla 'tbl_person'
                //1: SÍ fue exitosa la inserción del registro en la tabla 'tbl_person'
                int created = pstPerson.executeUpdate();
                
                if (created > 0) {
                    System.out.println("Persona creada exitosamente!");

                    //Sobre escribimos el valor de la variable que contiene la sentencia SQL a inyectar a la bbdd
                    //(?, ?, ?, ?, ?, ?, ?) Campos anónimos que estableceremos más adelante
                    query = "INSERT INTO tbl_client (code, address, email, passport, type, id_person, nit_airline) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstClient = connection.prepareStatement(query);
                    pstClient.setInt(1, client.getCode());
                    pstClient.setString(2, client.getAddress());
                    pstClient.setString(3, client.getEmail());
                    pstClient.setString(4, client.getPassport());
                    pstClient.setString(5, client.getType());
                    pstClient.setString(6, client.getId());
                    pstClient.setString(7, "890.100.577-6");

                    created = pstClient.executeUpdate();
                    if (created > 0) {
                        System.out.println("El cliente ha sido creado exitosamente");

                        //Solo en este apartado de código, sabemos con toda seguridad que el cliente fue creado correctamente
                        //Por eso retornamos TRUE
                        return true;
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Registro NO creado");
                }
            }
        } 
        //catch: si algo sale mal, acá tratamos de controlar la situación para que nuestra app no se rompa
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexión: " + e.getMessage());
        }
        
        //Para cualquier escenario en que la ejecución del código alcanzara esta línea, 
        //Sabemos con toda seguridad que el cliente NO fue creado
        return false;
    }   
    
    public Client read(String id) {
        //Declaramos un atributo de tipo Cliente inicializándolo en null, donde se asignará un objeto
        //Solo si el cliente es encontrado en la bbdd
        Client client = null;

        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //? campo anónimo donde se especificará la identificación a partir del cual se buscará al cliente
                //Esta sentencia SQL 'SELECT', obtiene al cliente si existe en la base de datos
                //cuyo id es igual al indicado por el usuario desde la capa de la vista
                String query = "SELECT p.name, p.last_name, p.phone_number, c.code, c.address, c.email, c.passport, c.type, p.id\n" +
                                "	FROM tbl_person p, tbl_client c\n" +
                                "       	WHERE p.id = c.id_person AND p.id = ?;";
                
                PreparedStatement pstAgents = connection.prepareStatement(query);
                pstAgents.setString(1, id +"");
                
                //En este ResultSet (Conjunto de resultados) obtenemos el (los) registros encontrados
                //Una vez la consulta SQL fue inyectada al motor de bbdd
                ResultSet result = pstAgents.executeQuery();
                
                //En esta sentencia While, que es un ciclo, recorremos todos y cada uno de los registros encontrados
                while (result.next()) {
                    
                    //Accedemos a cada campo retornado por medio de la línea result.getString (INDICE DE CAMPO);
                    //Este índice lo determina el orden de los campos retornados en la SELECT inyectada a la bbdd
                    String name = result.getString(1);
                    String lastName = result.getString(2);
                    String phoneNumber = result.getString(3);
                    int code = result.getInt(4);
                    String address = result.getString(5);
                    String email = result.getString(6);
                    String passport = result.getString(7);
                    String type = result.getString(8);

                    //A partir de la información recopilada del registro actual, creamos un objeto de tipo Client
                    //y lo asignamos a la variable 'client'creada en la línea de código 102.
                    client = new Client(code, address, email, passport, type, id, name, lastName, phoneNumber);
                   
                }
            }
        } catch(Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Retornamos el objeto client, que estará nulo 'null' si el client NO fue encontrado
        //o contendrá la información establecida en la línea 145 si fue encontrado
        return client;
    }
    
    //Método encargado de consultar TODOS los clientes creados en la Base de datos (bbdd).
    public List<Client> read() {
        List<Client> listClients = new ArrayList<>();

        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //? campo anónimo donde se especificará el código a partir del cual se buscará al estudiante
                //Esta sentencia SQL 'SELECT', obtiene al estudiante si existe en la base de datos
                //cuyo código es igual al indicado por el usuario desde la capa de la vista
                String query = "SELECT p.id, p.name, p.last_name, p.phone_number, c.code, c.address, c.email, c.passport, c.type \n" +
                                "	FROM tbl_person p, tbl_client c\n" +
                                "       	WHERE p.id = c.id_person;";
                
                PreparedStatement pstClients = connection.prepareStatement(query);
                
                //En este ResultSet (Conjunto de resultados) obtenemos el (los) registros encontrados
                //Una vez la consulta SQL fue inyectada al motor de bbdd
                ResultSet result = pstClients.executeQuery();
                
                //En esta sentencia While, que es un ciclo, recorremos todos y cada uno de los registros encontrados
                while (result.next()) {
                    
                    //Accedemos a cada campo retornado por medio de la línea result.getString (INDICE DE CAMPO);
                    //Este índice lo determina el orden de los campos retornados en la SELECT inyectada a la bbdd
                    String id = result.getString(1);
                    String name = result.getString(2);
                    String lastName = result.getString(3);
                    String phoneNumber = result.getString(4);
                    int code = result.getInt(5);
                    String address = result.getString(6);
                    String email = result.getString(7);
                    String passport = result.getString(8);
                    String type = result.getString(9);
                    
                  
                    Client client = new Client(code, address, email, passport, type, id, name, lastName, phoneNumber);
                    
                    //Hasta acá, el proceso es igual al realizado en el método anterior, la diferencia es que el cliente (client) encontrado
                    //se agrega a la tabla de clientes (tableClients).
                    listClients.add(client);
 
                }
            }
        } catch(Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Retornamos el objeto estudiante, que estará nulo 'null' si el estudiante NO fue encontrado
        //o contendrá la información establecida en la línea 158 si fue encontrado
        return listClients;
    }
    
    public boolean update(Client clientNew) {
        
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            if (connection != null) {
                System.out.println("Conexión exitosa");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //Esta sentencia SQL 'UPDATE', actualiza los campos de la tabla tbl_personas
                //para ello, pide 3 campos anónimos con los nuevos valores, y un cuarto campo anónimo en el cual
                //se informará el id de la persona a actualizar, utilizado en la cláusula 'WHERE'
                String query = "UPDATE tbl_person SET name = ?, last_name = ?, phone_number = ?"
                        + "             WHERE id = ?";
                PreparedStatement pstPerson = connection.prepareStatement(query);
                
                //Se realiza el seteo de los valores anónimos en la sentencia SQL
                pstPerson.setString(1, clientNew.getName());
                pstPerson.setString(2, clientNew.getLastName());
                pstPerson.setString(3, clientNew.getPhoneNumber());
                pstPerson.setString(4, clientNew.getId());
                
                //Se inyecta la sentencia a la bbdd
                int updatedPerson = pstPerson.executeUpdate();
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //Esta sentencia SQL 'UPDATE', actualiza los campos de la tabla tbl_client
                //para ello, pide 6 campos anónimos con los nuevos valores, y un septimo campo anónimo en el cual
                //se informará el id_person del client a actualizar, utilizado en la cláusula 'WHERE'
                query = "UPDATE tbl_client SET code = ?, address = ?, email = ?, passport = ?, type = ?, nit_airline = ?"
                        + "             WHERE id_person = ?";
                
                PreparedStatement pstClient = connection.prepareStatement(query);
                pstClient.setInt(1, clientNew.getCode());
                pstClient.setString(2, clientNew.getAddress());
                pstClient.setString(3, clientNew.getEmail());
                pstClient.setString(4, clientNew.getPassport());
                pstClient.setString(5, clientNew.getType());
                pstClient.setString(6, "890.100.577-6");
                pstClient.setString(7, clientNew.getId());
                        
                //Se inyecta la sentencia a la bbdd
                int updatedClientNew = pstClient.executeUpdate();
                
                //Si tanto la actualización de la persona, como la actualización del cliente son exitosas, se retorna TRUE
                return updatedPerson > 0 && updatedClientNew > 0;
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Cualquier otro camino en la ejecución del código que haya llegado hasta acá, indica que NO se ha actualizado el cliente
        return false;
    }
    
    
    public boolean delete(String id) {

        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            if (connection != null) {
                System.out.println("Conexión exitosa");
                
                /*
                Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                Esta sentencia SQL 'DELETE', elimina el registro de la tabla tbl_persona
                para ello, pide 1 campo anónimo en el cual se informará la identificación (id_person) del cliente (client) a actualizar, utilizado en la cláusula 'WHERE'
                Para lograr también la eliminación del registro en la tabla tbl_client de manera automática, se ha cambiado la restricción en la 
                Foreign Key existente en la tabla clients agregando ON DELETE CASCADE ON UPDATE CASCADE;
                */
                String query = "DELETE FROM tbl_person WHERE id = ?";
                PreparedStatement pstAgent = connection.prepareStatement(query);
                
                //Se realiza el seteo del id de la persona a eliminar
                pstAgent.setString(1, id);
                
                //Se inyecta la sentencia SQL hacia la bbdd
                int deleted = pstAgent.executeUpdate();
                if (deleted > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Cualquier otro camino en la ejecución del código que haya llegado hasta acá, indica que NO se ha eliminado al cliente ni a la persona
        return false;
    }
    
}
