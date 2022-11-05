/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author chloe
 */
public class Inventory {
    
    //Declare fields
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partId = 0;
    private static int productId = 0;
    
    //Declare methods
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static int incrementPartId(){
        partId++;
        return partId;
    };
    
    public static int incrementProductId(){
        productId++;
        return productId;
    };
    
    public static void addPart(Part newPart){
        allParts.add(newPart);
    };
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    };
    
    public static Part lookupPart(int partId){
        
        for (Part part : allParts) {
            if (partId == part.getId()) {
             return part;
            }
        }
       return null;
    };
        
    public static Product lookupProduct(int productId){
        
        for (Product product : allProducts) {
            if (productId == product.getId()) {
             return product;
            }
        }
       return null;
    };
    
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        
        for(Part part : allParts) {
           if (part.getName().contains(partName)){
               namedParts.add(part);
           }
       }
    return namedParts;
    };
    
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        
        for(Product product : allProducts) {
           if (product.getName().contains(productName)){
               namedProducts.add(product);
           }
       }
    return namedProducts;
    };
    
    public static void updatePart(int index, Part selectedPart){  
        Inventory.getAllParts().set(index, selectedPart);
    };

    public static void updateProduct(int index, Product newProduct){
        Inventory.getAllProducts().set(index, newProduct);
    };
    
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    };
    
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    };
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    };
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    };
}
