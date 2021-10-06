
package Classes;

/**
 *
 * @author wtrianav
 */
public class Person {
    //Atributos propios de la clase
    private String id;
    private String name;
    private String lastName;
    private String phoneNumber;

    //Método constructor
    public Person(String id, String name, String lastName, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }


    //Métodos accesores y mutadores

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

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
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
       
}


