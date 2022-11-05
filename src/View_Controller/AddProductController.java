/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import static View_Controller.MainScreenController.getModProdIndex;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author chloe
 */
public class AddProductController implements Initializable {
    Stage stage;
    Parent scene;
    private int index = getModProdIndex(); 
    
    private Label label;
    @FXML
    private TableView<Part> addProductTableView;
    @FXML
    private TableColumn<Part, Integer> addPartIdCol;
    @FXML
    private TableColumn<Part, String> addPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addPartInvCol;
    @FXML
    private TableColumn<Part, Double> addPartPriceCol;
    @FXML
    private TableView<Part> deleteProductTableView;
    @FXML
    private TableColumn<Part, Integer> delPartIdCol;
    @FXML
    private TableColumn<Part, String> delPartNameCol;
    @FXML
    private TableColumn<Part, Integer> delPartInvCol;
    @FXML
    private TableColumn<Part, Double> delPartPriceCol;
    @FXML
    private TextField maxText;
    @FXML
    private TextField minText;
    @FXML
    private TextField priceText;
    @FXML
    private TextField invText;
    @FXML
    private TextField productNameText;
    @FXML
    private TextField searchProductText;
    private final int prodId = Inventory.incrementProductId();   
     
    private final Product product = new Product(prodId,"New Product",0.0,0,0,0);
    
    public class noAssocPartsException extends Exception {
        public noAssocPartsException(String message){
        super(message);
        }
    }

   
    public void goToMainScreen(ActionEvent event) throws IOException{
        Parent saveProdMod = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(saveProdMod);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();  
    }
    
    private void updateAllPartsTableView() {
        addProductTableView.setItems(Inventory.getAllParts());
        
        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    private void updateAssocPartsTableView(){        
        deleteProductTableView.setItems(product.getAllAssociatedParts());
        
        delPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        delPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        delPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        delPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Add all parts to add section
        addProductTableView.setItems(Inventory.getAllParts());
        
        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        // TODO
    }    

    @FXML
    private void onActionSearchProducts(ActionEvent event) {
        String searchText = searchProductText.getText();
        
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
        
        
        addProductTableView.setItems(parts);
        searchProductText.clear();
}

    @FXML
    private void onActionAddProduct(ActionEvent event) {
        Part selectedPart;
        selectedPart = addProductTableView.getSelectionModel().getSelectedItem();
        
        product.addAssociatedPart(selectedPart);
        updateAssocPartsTableView(); 
        
    }

    @FXML
    private void onActionDeleteProduct(ActionEvent event) {
      Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are trying to delete an associated part, would you like to proceed?");
      
      Optional<ButtonType> result = alert.showAndWait();
      
      if(result.isPresent() && result.get() == ButtonType.OK){
        Part selectedPart;
        selectedPart = deleteProductTableView.getSelectionModel().getSelectedItem();
        product.deleteAssociatedPart(selectedPart);
        updateAssocPartsTableView();
        
        }
    }

    @FXML
    private void onActionSaveChanges(ActionEvent event) throws noAssocPartsException, IOException {
        
        try{
        if(product.getAllAssociatedParts().isEmpty()){
            throw new noAssocPartsException("All products must have at least one associated part.");
        } else {
            Product newProduct = new Product(
                    prodId,
                    productNameText.getText(),
                    Double.parseDouble(priceText.getText()),
                    Integer.parseInt(invText.getText()),
                    Integer.parseInt(minText.getText()),
                    Integer.parseInt(maxText.getText()));
            
            
            newProduct.getAllAssociatedParts().addAll(product.getAllAssociatedParts());
            
            Inventory.addProduct(newProduct);
            goToMainScreen(event);
            }
        }
        catch(noAssocPartsException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Every product must have at least one associated part.");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes, would you like to proceed?");
      
        Optional<ButtonType> result = alert.showAndWait();
      
         if(result.isPresent() && result.get() == ButtonType.OK){
        
            productNameText.clear();
            invText.clear();
            priceText.clear();
            minText.clear();
            maxText.clear();
         
            goToMainScreen(event);
         }
    }
    
}
