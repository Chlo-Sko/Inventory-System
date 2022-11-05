/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author chloe
 */
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    

    private static int modPartIndex;
    private static int modProductIndex;
    
    

    @FXML
    private TextField partsSearchText;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private TableColumn<Part, Integer> partIdCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part, Integer> partInvCol;
    @FXML
    private TableColumn<Part, Double> partPriceCol;
    @FXML
    private TextField productsSearchText;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private TableColumn<Product, Integer> productIdCol;
    @FXML
    private TableColumn<Product, String> productNameCol;
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    
     public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException i) {
            return false;
        }
    }
     
     private void updateProductsTable() {
        productsTableView.setItems(Inventory.getAllProducts());
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
     }
     
     private void updatePartsTable(){
        partsTableView.setItems(Inventory.getAllParts());
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
     }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        //Set Parts Table Data
        updatePartsTable();
        
        //Set Products Table Data
        updateProductsTable();

    }    
    public static int getModPartIndex() {
        return modPartIndex;
    }

    public static int getModProdIndex() {
        return modProductIndex;
    }
    @FXML
    private void onActionSearchParts(ActionEvent event) {
        String searchText = partsSearchText.getText();
        
        ObservableList<Part> parts = Inventory.lookupPart(searchText);
        
        if (parts.isEmpty()) {
            try {
                int searchId = Integer.parseInt(searchText);
                Part searchPart = Inventory.lookupPart(searchId);
                if (searchPart != null)
                parts.add(searchPart);
            }
            catch (NumberFormatException e) {
            //ignore
            }
       
    
    }
        
        
        partsTableView.setItems(parts);
        partsSearchText.clear();
    }

    @FXML
    private void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionModifyPart(ActionEvent event) throws IOException {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        modPartIndex = Inventory.getAllParts().indexOf(selectedPart);
        
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();
    }

    @FXML
    private void onActionDeletePart(ActionEvent event) throws IOException {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are trying to delete a part, would you like to proceed?");
      
      Optional<ButtonType> result = alert.showAndWait();
      
      if(result.isPresent() && result.get() == ButtonType.OK){
      Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
      Inventory.deletePart(selectedPart);
      updatePartsTable();
      }
    }

    @FXML
    private void onActionSearchProducts(ActionEvent event) {
         String searchText = productsSearchText.getText();
        
        ObservableList<Product> products = Inventory.lookupProduct(searchText);
        
        if (products.isEmpty()) {
            try {
                int searchId = Integer.parseInt(searchText);
                Product searchProduct = Inventory.lookupProduct(searchId);
                if (searchProduct != null)
                products.add(searchProduct);
            }
            catch (NumberFormatException e) {
            //ignore
            }
       
    
    }
        
        
        productsTableView.setItems(products);
        productsSearchText.clear();
    }

    @FXML
    private void onActionAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionModifyProduct(ActionEvent event) throws IOException {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        modProductIndex = Inventory.getAllProducts().indexOf(selectedProduct);
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
        
    }

    @FXML
    private void onActionDeleteProduct(ActionEvent event) throws IOException {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are trying to delete a product, would you like to proceed?");
      
      Optional<ButtonType> result = alert.showAndWait();
      
      if(result.isPresent() && result.get() == ButtonType.OK){
        
      Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
      Inventory.deleteProduct(selectedProduct);
      updateProductsTable();
      }
    }   
    
    
    
}
