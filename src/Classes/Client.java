
package Classes;

/**
 *
 * @author wtrianav
 */
public class Client extends Person {
    //Atributos propios de la clase.
    private int code;
    private String address;
    private String email;
    private String passport;
    private String type;
    //Atributos que indican la relación de agregación con las clases Airline y Flight
    private Airline airline;
    private Flight flight;
    
    
    //Método constructor
    public Client(int code, String address, String email, String passport, String type, String id, String name, String lastName, String phoneNumber) {
        super(id, name, lastName, phoneNumber);
        this.code = code;
        this.address = address;
        this.email = email;
        this.passport = passport;
        this.type = type;
    }
    

    @Override
    public String toString() {
        return "Nombre: " + super.getName() + ", Apellido: " + super.getLastName() + ", Cédula:" + super.getId() +  ", Dirección:" + address + ", Celular: " + super.getPhoneNumber() + ", email:" + email + ", Pasaporte:" + passport + ", Tipo:" + type;
    } 
    
    //Métodos accesores y mutadores

        /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }
    
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * @param passport the passport to set
     */
    public void setPassport(String passport) {
        this.passport = passport;
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

    /**
     * @return the airline
     */
    public Airline getAirline() {
        return airline;
    }

    /**
     * @param airline the airline to set
     */
    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    /**
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

}
