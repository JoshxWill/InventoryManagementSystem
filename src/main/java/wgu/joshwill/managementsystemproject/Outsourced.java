package wgu.joshwill.managementsystemproject;

/**
 *
 * @author Joshua Williams
 */
public class Outsourced extends Part {


    /**
     * Company Name for Add Part
     */
    private String companyName;


    /**
     * Constructor for Outsourced.
     *
     * @param id          Outsourced ID
     * @param name        Outsourced Name
     * @param price       Outsourced Price
     * @param stock       Outsourced Stock
     * @param min         Outsourced Min
     * @param max         Outsourced Max
     * @param companyName Outsourced Company Name
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Setter for companyName
     *
     * @param companyName for Add Part
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter for companyName
     *
     * @return companyName from Add Part
     */
    public String getCompanyName() {
        return companyName;
    }

}
