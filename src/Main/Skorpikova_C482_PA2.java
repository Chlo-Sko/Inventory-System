/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author chloe
 */
public class Skorpikova_C482_PA2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Inventory System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Add Parts for Testing
        InhousePart part1 = new InhousePart(112,"Monitor",159.99,300,100,500,59);
        InhousePart part2 = new InhousePart(221,"Mouse",29.99,500,300,800,512);
        InhousePart part3 = new InhousePart(333,"Keyboard",79.99,167,100,600,29);
        OutsourcedPart part4 = new OutsourcedPart(100,"Speaker",89.99,257,200,500,"Anker");
        OutsourcedPart part5 = new OutsourcedPart(101,"Motherboard",149.99,500,100,700,"Intel");
        OutsourcedPart part6 = new OutsourcedPart(102,"WebCam",49.99,452,200,900,"Logitech");
        
        //Add Test Parts to Observable List
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        
        //Add Products for Testing
        Product product1 = new Product(999,"Audiophile Package",159.99,85,60,200);
        Product product2 = new Product(998,"Conference Package",209.99,58,25,100);
        Product product3 = new Product(997,"Basic Package",99.99,783,600,1000);
        
        //Add Products to Observable List
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        
        //Add Associated Parts to Products
        product1.addAssociatedPart(part6);
        product2.addAssociatedPart(part5);
        product3.addAssociatedPart(part2);
        
        
        launch(args);
    }
    
}
