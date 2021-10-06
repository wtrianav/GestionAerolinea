
package Model;

import Classes.Flight;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author wtrianav
 */
public class ModelFlight {

    //Declaramos un objeto de tipo 'Database' para acceder a los valores que necesito para conectarme a la bbdd.
    Database database;
    
    public ModelFlight() {
        //Instanciamos ese objeto database
        database = new Database();
    }
    
    //Se está recibiendo el objeto desde el controlador (ctlClient) como parámetro.
    public boolean create(Flight flight, List<Flight> listFlights) {
        //Se agrega el cliente a la lista en el tab lista de clientes.
        listFlights.add(flight);

        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //(?, ?, ?, ?, ?, ?) Campos anónimos que estableceremos más adelante
                String query = "INSERT INTO tbl_flight (code, origin, destination, departure_time, arrival_time, type, nit_airline) VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                //Objeto por medio del cual se prepara la sentencia SQL y se indica la query a inyectar.
                PreparedStatement pstFlight = connection.prepareStatement(query);
                
                //Se establecen los valores para los campos anónimos
                pstFlight.setString(1, flight.getCode() + "");
                pstFlight.setString(2, flight.getOrigin());
                pstFlight.setString(3, flight.getDestination());
                pstFlight.setString(4, flight.getDepartureTime());
                pstFlight.setString(5, flight.getArrivalTime());
                pstFlight.setString(6, flight.getType());
                pstFlight.setString(7, "890.100.577-6");
                
                //executeUpdate(): Inyecta la sentencia SQL hacia el motor de base de datos
                //0: NO fue exitosa la inserción del registro en la tabla 'tbl_person'
                //1: SÍ fue exitosa la inserción del registro en la tabla 'tbl_person'
                int created = pstFlight.executeUpdate();
                
                if (created > 0) {
                    System.out.println("Vuelo creado exitosamente!");
                    return true;
                }    
            }    
        }    
        //catch: si algo sale mal, acá tratamos de controlar la situación para que nuestra app no se rompa
        catch(Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        return false;
        
    }
    
    public Flight read(String code) {
        //Declaramos un atributo de tipo Agente inicializándolo en null, donde se asignará un objeto
        //Solo si el agente es encontrado en la bbdd
        Flight flight = null;

        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //? campo anónimo donde se especificará la identificación a partir del cual se buscará al agente
                //Esta sentencia SQL 'SELECT', obtiene al agente si existe en la base de datos
                //cuyo código es igual al indicado por el usuario desde la capa de la vista
                String query = "SELECT f.origin, f.destination, f.departure_time, f.arrival_time, f.type, f.code\n" +
                                "	FROM tbl_flight f\n" +
                                "       	WHERE f.code = ?;";
                
                PreparedStatement pstFlights = connection.prepareStatement(query);
                //pstFlights.setString(1, code + "");
                pstFlights.setInt(1, Integer.parseInt(code));
                
                //En este ResultSet (Conjunto de resultados) obtenemos el (los) registros encontrados
                //Una vez la consulta SQL fue inyectada al motor de bbdd
                ResultSet result = pstFlights.executeQuery();
                
                //En esta sentencia While, que es un ciclo, recorremos todos y cada uno de los registros encontrados
                while (result.next()) {
                    
                    //Accedemos a cada campo retornado por medio de la línea result.getString (INDICE DE CAMPO);
                    //Este índice lo determina el orden de los campos retornados en la SELECT inyectada a la bbdd
                    String origin = result.getString(1);
                    String destination = result.getString(2);
                    String departure_time = result.getString(3);
                    String arrival_time = result.getString(4);
                    String type = result.getString(5);
                    
                    //A partir de la información recopilada del registro actual, creamos un objeto de tipo Agent
                    //y lo asignamos a la variable 'agent' creada en la línea de código 101.
                    flight = new Flight(code, origin, destination, departure_time, arrival_time, type);
                   
                }
            }
        } catch(Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Retornamos el objeto agent, que estará nulo 'null' si el agent NO fue encontrado
        //o contendrá la información establecida en la línea 141 si fue encontrado
        return flight;
    }
    
    //Método encargado de consultar TODOS los agentes creados en la Base de datos (bbdd).
    public List<Flight> read() {
        List<Flight> listFlights = new ArrayList<>();

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
                String query = "SELECT f.code, f.origin, f.destination, f.departure_time, f.arrival_time, f.type\n" +
                                "	FROM tbl_flight f";
                
                PreparedStatement pstAgents = connection.prepareStatement(query);
                
                //En este ResultSet (Conjunto de resultados) obtenemos el (los) registros encontrados
                //Una vez la consulta SQL fue inyectada al motor de bbdd
                ResultSet result = pstAgents.executeQuery();
                
                //En esta sentencia While, que es un ciclo, recorremos todos y cada uno de los registros encontrados
                while (result.next()) {
                    
                    //Accedemos a cada campo retornado por medio de la línea result.getString (INDICE DE CAMPO);
                    //Este índice lo determina el orden de los campos retornados en la SELECT inyectada a la bbdd
                    String code = result.getString(1);
                    String origin = result.getString(2);
                    String destination = result.getString(3);
                    String departure_time = result.getString(4);
                    String arrival_time = result.getString(5);
                    String type = result.getString(6);
                    
                  
                    Flight flight = new Flight(code, origin, destination, departure_time, arrival_time, type);
                    
                    //Hasta acá, el proceso es igual al realizado en el método anterior, la diferencia es que el agente encontrado
                    //se agrega a la tabla agentes (tableAgent).
                    listFlights.add(flight);
 
                }
            }
        } catch(Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Retornamos el objeto estudiante, que estará nulo 'null' si el estudiante NO fue encontrado
        //o contendrá la información establecida en la línea 158 si fue encontrado
        return listFlights;
    }
    
    
    public boolean update(Flight flightNew) {
        
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            if (connection != null) {
                System.out.println("Conexión exitosa");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //Esta sentencia SQL 'UPDATE', actualiza los campos de la tabla tbl_person
                //para ello, pide 3 campos anónimos con los nuevos valores, y un cuarto campo anónimo en el cual
                //se informará el id de la persona a actualizar, utilizado en la cláusula 'WHERE'
                String query = "UPDATE tbl_flight SET origin = ?, destination = ?, departure_time = ?, arrival_time = ?, type = ?, nit_airline = ?"
                        + "             WHERE code = ?";

                PreparedStatement pstFlight = connection.prepareStatement(query);
                
                //Se realiza el seteo de los valores anónimos en la sentencia SQL
                pstFlight.setString(1, flightNew.getOrigin());
                pstFlight.setString(2, flightNew.getDestination());
                pstFlight.setString(3, flightNew.getDepartureTime());
                pstFlight.setString(4, flightNew.getArrivalTime());
                pstFlight.setString(5, flightNew.getType());
                pstFlight.setString(6, "890.100.577-6");
                pstFlight.setInt(7, Integer.parseInt(flightNew.getCode()));
                
                //Se inyecta la sentencia a la bbdd
                int updatedFlight = pstFlight.executeUpdate();
                
                //Si tanto la actualización de la persona, como la actualización del agente son exitosas, se retorna TRUE
                return updatedFlight > 0;
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Cualquier otro camino en la ejecución del código que haya llegado hasta acá, indica que NO se ha actualizado el agente
        return false;
    }
    
    public boolean delete(String code) {

        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            if (connection != null) {
                System.out.println("Conexión exitosa");
                
                /*
                Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                Esta sentencia SQL 'DELETE', elimina el registro de la tabla tbl_personas
                para ello, pide 1 campo anónimo en el cual se informará el id_persona del estudiante a actualizar, utilizado en la cláusula 'WHERE'
                Para lograr también la eliminación del registro en la tabla tbl_estudiantes de manera automática, se ha cambiado la restricción en la 
                Foreign Key existente en la tabla agents agregando ON DELETE CASCADE ON UPDATE CASCADE;
                */
                String query = "DELETE FROM tbl_flight WHERE code = ?";
                PreparedStatement pstFlight = connection.prepareStatement(query);
                
                //Se realiza el seteo del id de la persona a eliminar
                pstFlight.setString(1, code);
                
                //Se inyecta la sentencia SQL hacia la bbdd
                int deleted = pstFlight.executeUpdate();
                if (deleted > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Cualquier otro camino en la ejecución del código que haya llegado hasta acá, indica que NO se ha eliminado al agente ni a la persona
        return false;
    }
      
}
