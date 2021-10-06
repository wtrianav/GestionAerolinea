
package Classes;

/**
 *
 * @author wtrianav
 */
public class Flight {
    //Atributos propios de la clase.
    private String code;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private String type;
    
    //Método constructor.
    public Flight(String code, String origin, String destination, String departureTime, String arrivalTime, String type) {
        this.code = code;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.type = type;
    }
    
        @Override
    public String toString() {
        return "Código: " + code + ", Tipo: " + type + ", Destino:" + destination + ", Origen: " + origin + ", Hora de llegada:" + arrivalTime + ", Hora de salida:" + departureTime;
    } 
    
    //Métodos accesores y mutadores.
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return the departureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * @param departureTime the departureTime to set
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * @return the arrivalTime
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * @param arrivalTime the arrivalTime to set
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    
}
