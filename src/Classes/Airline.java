
package Classes;

/**
 *
 * @author wtrianav
 */
public class Airline {
    //Atributos propios de la clase
    private String name;
    private String city;
    private int nit;
    //Atributos que indican la relación de composición con las clases Agent y Flight
    private Agent agent;
    private Flight flight;
    
    //Método constructor
    public Airline(String name, String city, int nit, Agent agent, Flight flight) {
        this.name = name;
        this.city = city;
        this.nit = nit;
        this.agent = agent;
        this.flight = flight;
    }
    
    //Métodos accesores y mutadores.

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the nit
     */
    public int getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(int nit) {
        this.nit = nit;
    }

    /**
     * @return the agent
     */
    public Agent getAgent() {
        return agent;
    }

    /**
     * @param agent the agent to set
     */
    public void setAgent(Agent agent) {
        this.agent = agent;
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
