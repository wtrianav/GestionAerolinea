
package Model;

/**
 *
 * @author wtrianav
 */
public class Database {
    
    //Conexión a la base de datos por medio del Driver que me provee la librería 'mysql-connector-java-8.0.26.jar'.
    private final String driver = "com.mysql.jdbc.Driver";
    
    //url dónde se ubica la base de datos.
    private final String url = "jdbc:mysql://localhost:3306/gestion_reservas";

    //Credenciales para conectarme a la base de datos.
    private final String user = "root";
    private final String password = "";

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    
    
}
