
package Model;

import Classes.ClassReports;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author wtrianav
 */
public class ModelReport {
    
    //Declaramos un objeto de tipo 'Database' para acceder a los valores que necesito para conectarme a la bbdd.
    Database database;
    
    public ModelReport() {
        //Instanciamos ese objeto database
        database = new Database();
    }
    
    public List<ClassReports> ListByPersonType(){
        List<ClassReports> ListTypePerson = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                String query =  "SELECT \"Agentes\", COUNT(*)\n" +
                                "    FROM tbl_agent\n" +
                                "UNION\n" +
                                "SELECT \"Clientes\", COUNT(*)\n" +
                                "    FROM tbl_client";
                
                PreparedStatement pstReport = connection.prepareStatement(query);
                
                ResultSet result = pstReport.executeQuery();
                while (result.next()) {
                    String type = result.getString(1);
                    int amount = result.getInt(2);
                    
                    ClassReports report = new ClassReports(type, amount);
                    ListTypePerson.add(report);
                }
                
            }
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return ListTypePerson;    
    }
    
    
    public List<ClassReports> ListByClientType(){
        List<ClassReports> ListTypeClient = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                String query =  "SELECT c.type, COUNT(*)\n" +
                                "	FROM tbl_client c\n" +
                                "    	GROUP BY c.type;";
                
                PreparedStatement pstReport = connection.prepareStatement(query);
                
                ResultSet result = pstReport.executeQuery();
                while (result.next()) {
                    String type = result.getString(1);
                    int amount = result.getInt(2);
                    
                    ClassReports report = new ClassReports(type, amount);
                    ListTypeClient.add(report);
                }
                
            }
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return ListTypeClient;    
    }
    
    public List<ClassReports> ListByFlightType(){
        List<ClassReports> ListTypeFlight = new ArrayList<>();
        
        try (Connection connection = DriverManager.getConnection
                                        (database.getUrl(), database.getUser(), database.getPassword())){
            
            //Preguntamos si la conexión tiene algo, con lo que determinamos si se estableció o no la conexión
            if (connection != null) {
                String query =  "SELECT f.type, COUNT(*)\n" +
                                "	FROM tbl_flight f\n" +
                                "    	GROUP BY f.type;";
                
                PreparedStatement pstReport = connection.prepareStatement(query);
                
                ResultSet result = pstReport.executeQuery();
                while (result.next()) {
                    String type = result.getString(1);
                    int amount = result.getInt(2);
                    
                    ClassReports report = new ClassReports(type, amount);
                    ListTypeFlight.add(report);
                }
                
            }
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return ListTypeFlight;    
    }
    
}
