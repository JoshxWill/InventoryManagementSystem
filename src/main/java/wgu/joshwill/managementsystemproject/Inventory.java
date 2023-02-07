package wgu.joshwill.managementsystemproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Feeds data to application
 *
 * @author Joshua Williams
 */
public class Inventory {

    /**
     *
     * PartID from Part
     */

    private static int partId = 0;

    /**
     *
     * ProductID from Part
     */

    private static int productId = 0;

    /**
     *
     * All Parts Inventory List
     */

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     *
     * All Products Inventory List
     */

    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * Add Part to Inventory
     *
     * @param newPart
     */

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     *
     * Add Product to Inventory
     *
     * @param newProduct
     */

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * New Part ID
     *
     * @return new Part ID
     */

    public static int getNewPartID(){
        return ++partId;
    }

    /**
     * New Product ID
     *
     * @return new Product ID
     */

    public static int getNewProductId(){
        return ++productId;
    }

    /**
     *
     * Search Part by ID
     *
     * @param partId
     * @return Part found, null if not found
     */

    public static Part lookupPart(int partId){
        Part foundPart = null;

        for(Part part : allParts){
            if (part.getId() == partId){
                foundPart = part;
            }
        }

        return foundPart;
    }

    /**
     *
     * Search Product by ID
     *
     * @param productId
     * @return Product found, null if not found
     */

    public static Product lookupProduct(int productId){
        Product foundProduct = null;

        for(Product product : allProducts){
            if(product.getId() == productId){
                foundProduct = product;
            }
        }

        return foundProduct;
    }

    /**
     *
     * Search Parts by Name
     *
     * @param partName
     * @return Parts Found
     */

    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        for(Part part : allParts){
            if(part.getName().equals(partName)){
                foundParts.add(part);
            }
        }

        return foundParts;
    }

    /**
     *
     * Search Products by Name
     *
     * @param productName
     * @return Products Found
     */

    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        for (Product product : allProducts){
            if (product.getName().equals(productName)){
                foundProducts.add(product);
            }
        }

        return foundProducts;
    }

    /**
     *
     * Update Parts List
     *
     * @param index being replaced
     * @param selectedPart the replacement
     */

    public static void updatePart (int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     *
     * Update Products List
     *
     * @param index being replaced
     * @param selectedProduct the replacement
     */

    public static void updateProduct (int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }

    /**
     *
     * Remove Part from Parts List
     *
     * @param selectedPart removed
     * @return status of removal
     */

    public static boolean deletePart(Part selectedPart){
        if(allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * Remove Product from Products List
     *
     * @param selectedProduct
     * @return status of removal
     */

    public static boolean deleteProduct(Product selectedProduct){
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     *
     * All Parts in Inventory
     *
     * @return entire Parts
     */

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *
     * All Products in Inventory
     *
     * @return entire Products
     */

    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
