
package Model;

import Classes.Agent;
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
public class ModelAgent {
    
    //Declaramos un objeto de tipo 'Database' para acceder a los valores que necesito para conectarme a la bbdd.
    Database database;
    
    public ModelAgent() {
        //Instanciamos ese objeto database
        database = new Database();
    }
    
    
    //Se está recibiendo el objeto desde el controlador (ctlAgent) como parámetro.
    public boolean create(Agent agent, List<Agent> listAgents) {
        //Se agrega el agente a la tabla tableAgent.
        listAgents.add(agent);
        
        //Establecemos la conexión hacia la bbdd
        //En esta línea de código, se realiza la petición al motor de base de datos para 
        //que nos asigne una conexión por medio de la cual podamos inyectar sentencias SQL
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                System.out.println("[TEST]Conexión exitosa a la base de datos");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //(?, ?, ?, ?) Campos anónimos que estableceremos más adelante
                String query = "INSERT INTO tbl_person (id, name, last_name, phone_number) VALUES (?, ?, ?, ?)";
                
                //Objeto por medio del cual se prepara la sentencia SQL y se indica la query a inyectar.
                PreparedStatement pstPerson = connection.prepareStatement(query);
                
                //Se establecen los valores para los campos anónimos
                pstPerson.setString(1, agent.getId());
                pstPerson.setString(2, agent.getName());
                pstPerson.setString(3, agent.getLastName());
                pstPerson.setString(4, agent.getPhoneNumber());
                
                                
                //executeUpdate(): Inyecta la sentencia SQL hacia el motor de base de datos
                //0: NO fue exitosa la inserción del registro en la tabla 'tbl_person'
                //1: SÍ fue exitosa la inserción del registro en la tabla 'tbl_person'
                int created = pstPerson.executeUpdate();
                
                if (created > 0) {
                    System.out.println("Persona creada exitosamente!");

                    //Sobre escribimos el valor de la variable que contiene la sentencia SQL a inyectar a la bbdd
                    //(?, ?, ?, ?) Campos anónimos que estableceremos más adelante
                    query = "INSERT INTO tbl_agent (code, position, id_person, nit_airline) VALUES (?, ?, ?, ?)";
                    PreparedStatement pstAgent = connection.prepareStatement(query);
                    pstAgent.setInt(1, agent.getCode());
                    pstAgent.setString(2, agent.getPosition());
                    pstAgent.setString(3, agent.getId());
                    pstAgent.setString(4, "890.100.577-6");

                    created = pstAgent.executeUpdate();
                    if (created > 0) {
                        System.out.println("Agente ha sido creado exitosamente");

                        //Solo en este apartado de código, sabemos con toda seguridad que el agente fue creado correctamente
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
        //Sabemos con toda seguridad que el agente NO fue creado
        return false;
    }
    
    public Agent read(String id) {
        //Declaramos un atributo de tipo Agente inicializándolo en null, donde se asignará un objeto
        //Solo si el agente es encontrado en la bbdd
        Agent agent = null;

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
                String query = "SELECT p.name, p.last_name, p.phone_number, a.code, a.position, p.id\n" +
                                "	FROM tbl_person p, tbl_agent a\n" +
                                "       	WHERE p.id = a.id_person AND p.id = ?;";
                
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
                    String position = result.getString(5);

                    //A partir de la información recopilada del registro actual, creamos un objeto de tipo Agent
                    //y lo asignamos a la variable 'agent' creada en la línea de código 101.
                    agent = new Agent(code, position, id, name, lastName, phoneNumber);
                   
                }
            }
        } catch(Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Retornamos el objeto agent, que estará nulo 'null' si el agent NO fue encontrado
        //o contendrá la información establecida en la línea 141 si fue encontrado
        return agent;
    }
    
    //Método encargado de consultar TODOS los agentes creados en la Base de datos (bbdd).
    public List<Agent> read() {
        List<Agent> listAgents = new ArrayList<>();

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
                String query = "SELECT p.id, p.name, p.last_name, p.phone_number, a.code, a.position \n" +
                                "	FROM tbl_person p, tbl_agent a\n" +
                                "       	WHERE p.id = a.id_person;";
                
                PreparedStatement pstAgents = connection.prepareStatement(query);
                
                //En este ResultSet (Conjunto de resultados) obtenemos el (los) registros encontrados
                //Una vez la consulta SQL fue inyectada al motor de bbdd
                ResultSet result = pstAgents.executeQuery();
                
                //En esta sentencia While, que es un ciclo, recorremos todos y cada uno de los registros encontrados
                while (result.next()) {
                    
                    //Accedemos a cada campo retornado por medio de la línea result.getString (INDICE DE CAMPO);
                    //Este índice lo determina el orden de los campos retornados en la SELECT inyectada a la bbdd
                    String id = result.getString(1);
                    String name = result.getString(2);
                    String lastName = result.getString(3);
                    String phoneNumber = result.getString(4);
                    int code = result.getInt(5);
                    String position = result.getString(6);
                    
                  
                    Agent agent = new Agent(code, position, id, name, lastName, phoneNumber);
                    
                    //Hasta acá, el proceso es igual al realizado en el método anterior, la diferencia es que el agente encontrado
                    //se agrega a la tabla agentes (tableAgent).
                    listAgents.add(agent);
 
                }
            }
        } catch(Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Retornamos el objeto estudiante, que estará nulo 'null' si el estudiante NO fue encontrado
        //o contendrá la información establecida en la línea 158 si fue encontrado
        return listAgents;
    }
    
    public boolean update(Agent agentNew) {
        
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            if (connection != null) {
                System.out.println("Conexión exitosa");
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //Esta sentencia SQL 'UPDATE', actualiza los campos de la tabla tbl_person
                //para ello, pide 3 campos anónimos con los nuevos valores, y un cuarto campo anónimo en el cual
                //se informará el id de la persona a actualizar, utilizado en la cláusula 'WHERE'
                String query = "UPDATE tbl_person SET name = ?, last_name = ?, phone_number = ?"
                        + "             WHERE id = ?";
                PreparedStatement pstPerson = connection.prepareStatement(query);
                
                //Se realiza el seteo de los valores anónimos en la sentencia SQL
                pstPerson.setString(1, agentNew.getName());
                pstPerson.setString(2, agentNew.getLastName());
                pstPerson.setString(3, agentNew.getPhoneNumber());
                pstPerson.setString(4, agentNew.getId());
                
                //Se inyecta la sentencia a la bbdd
                int updatedPerson = pstPerson.executeUpdate();
                
                //Variable que contiene la sentencia SQL a inyectar a la bbdd una vez la conexión fue exitosa
                //Esta sentencia SQL 'UPDATE', actualiza los campos de la tabla tbl_agent
                //para ello, pide 2 campos anónimos con los nuevos valores, y un tercer campo anónimo en el cual
                //se informará el id_person del agent a actualizar, utilizado en la cláusula 'WHERE'
                query = "UPDATE tbl_agent SET code = ?, position = ?, nit_airline = ?"
                        + "             WHERE id_person = ?";
                
                PreparedStatement pstAgent = connection.prepareStatement(query);
                pstAgent.setInt(1, agentNew.getCode());
                pstAgent.setString(2, agentNew.getPosition());
                pstAgent.setString(3, "890.100.577-6");
                pstAgent.setString(4, agentNew.getId());
                        
                //Se inyecta la sentencia a la bbdd
                int updatedAgentNew = pstAgent.executeUpdate();
                
                //Si tanto la actualización de la persona, como la actualización del agente son exitosas, se retorna TRUE
                return updatedPerson > 0 && updatedAgentNew > 0;
            }
        } catch (Exception e) {
            System.out.println("Error en la conexión: " + e.getMessage());
        }
        
        //Cualquier otro camino en la ejecución del código que haya llegado hasta acá, indica que NO se ha actualizado el agente
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
                Esta sentencia SQL 'DELETE', elimina el registro de la tabla tbl_personas
                para ello, pide 1 campo anónimo en el cual se informará el id_persona del estudiante a actualizar, utilizado en la cláusula 'WHERE'
                Para lograr también la eliminación del registro en la tabla tbl_estudiantes de manera automática, se ha cambiado la restricción en la 
                Foreign Key existente en la tabla agents agregando ON DELETE CASCADE ON UPDATE CASCADE;
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
        
        //Cualquier otro camino en la ejecución del código que haya llegado hasta acá, indica que NO se ha eliminado al agente ni a la persona
        return false;
    }
    
}
