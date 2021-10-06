
package Classes;

/**
 *
 * @author wtrianav
 */
public class Agent extends Person {
    //Atributos propios de la clase
    private int code;
    private String position;
    
    //Método constructor
    public Agent(int code, String position, String id, String name, String lastName, String phoneNumber) {
        super(id, name, lastName, phoneNumber);
        this.code = code;
        this.position = position;
    }
    
    @Override
    public String toString() {
        return "Nombre: " + super.getName() + ", Apellido: " + super.getLastName() + ", Cédula:" + super.getId() + ", Cargo: " + position + ", Código:" + code;
    }     
    
    
    //Métodos accesores y mutadores.

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
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
        
}
