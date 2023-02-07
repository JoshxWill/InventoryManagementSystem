package wgu.joshwill.managementsystemproject;

/**
 *
 * @author Joshua Williams
 */
public class InHouse extends Part {

    /**
     * Machine ID for Part
     */
    private int machineId;


    /**
     * Constructor for In-House.
     *
     * @param id ID
     * @param name Name
     * @param price Price
     * @param stock Inventory
     * @param min Min
     * @param max Max
     * @param machineId Outsourced MachineID
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * Setter for machineId
     *
     * @param machineId for Part
     */

    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Getter for machineId
     *
     * @return machineId from Part
     *
     */
    public int getMachineId(){
        return machineId;
    }

}
