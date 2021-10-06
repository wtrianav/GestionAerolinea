
package Classes;

/**
 *
 * @author wtrianav
 */
public class ClassReports {
    private String type;
    private int quantity;

    public ClassReports(String type, int quantity) {
        this.type = type;
        this.quantity = quantity;
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
     * @return the amount
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the amount to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
}
