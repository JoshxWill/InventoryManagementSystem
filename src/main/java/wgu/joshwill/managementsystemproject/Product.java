package wgu.joshwill.managementsystemproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Joshua Williams
 */
public class Product {

    /**
     * ID for Product
     */

    private int id;

    /**
     * Name for Product
     */

    private String name;

    /**
     * Price for Product
     */

    private double price;

    /**
     * Inventory Level for Product
     */

    private int stock;

    /**
     * Min for Product
     */

    private int min;

    /**
     * Max for Product
     */

    private int max;

    /**
     * Associated Parts for Product
     */

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *Constructor for Product
     *
     * @param id Product ID
     * @param name Product Name
     * @param price Product Price
     * @param stock Product Stock
     * @param min Product Min
     * @param max Product Max
     */

    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**
     * Setter for id
     *
     * @param id for Product
     */

    public void setId(int id){
        this.id = id;
    }

    /**
     * Getter for id
     *
     * @return id from Product
     */

    public int getId(){
        return id;
    }

    /**
     * Setter for name
     *
     * @param name for Product
     */

    public void setName(String name){
        this.name = name;
    }

    /**
     * Getter for name
     *
     * @return name from Product
     */

    public String getName() {
        return name;
    }

    /**
     * Setter for price
     *
     * @param price for Product
     */

    public void setPrice(double price){
        this.price = price;
    }

    /**
     * Getter for price
     *
     * @return price from Product
     */

    public double getPrice(){
        return price;
    }

    /**
     * Setter for stock
     *
     * @param stock for Product
     */

    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * Getter for stock
     *
     * @return stock from Product
     */

    public int getStock(){
        return stock;
    }

    /**
     * Setter for min
     *
     * @param min for Product
     */

    public void setMin(int min){
        this.min = min;
    }

    /**
     * Getter for min
     *
     * @return min from Product
     */

    public int getMin(){
        return min;
    }

    /**
     * Setter for max
     *
     * @param max for Product
     */

    public void setMax(int max){
        this.max = max;
    }

    /**
     * Getter for max
     *
     * @return max for Product
     */

    public int getMax(){
        return max;
    }

    /**
     * Adds associated parts list for Product
     *
     * @param part add
     */

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Deletes associated parts list for Product
     *
     * @param selectedAssociatedPart delete
     *
     * @return true or false status from delete
     */

    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        if (associatedParts.contains(selectedAssociatedPart)){
            associatedParts.remove(selectedAssociatedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * Getter entire associated parts for Product
     *
     * @return associated parts lists
     */

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}


