/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author chloe
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private Label label;
    @FXML
    private RadioButton partInhouseRButton;
    @FXML
    private ToggleGroup addPartTG;
    @FXML
    private RadioButton partOutsourcedRButton;
    @FXML
    private TextField partMaxText;
    @FXML
    private TextField partMinText;
    @FXML
    private Label companyNameLbl;
    @FXML
    private TextField partPriceText;
    @FXML
    private TextField partNameText;
    @FXML
    private TextField partInvText;
    @FXML
    private TextField partIDText;
    @FXML
    private TextField partCompanyNameText;
    
    public void goToMainScreen (ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionPartInhouse(ActionEvent event) {
        boolean isInhouse;
        isInhouse = partInhouseRButton.isSelected();
         companyNameLbl.setText("Machine ID");
         partCompanyNameText.setPromptText("Mach ID");
    }

    @FXML
    private void onActionPartOutsourced(ActionEvent event) {
        boolean isOutsourced;
        isOutsourced = partOutsourcedRButton.isSelected();
        companyNameLbl.setText("Company Name");
        partCompanyNameText.setPromptText("Company Nm");
        
    }

    @FXML
    private void onActionAddPart(ActionEvent event) throws IOException {
        boolean isInhouse;      
        isInhouse = partInhouseRButton.isSelected();
        
        if(isInhouse){   
            Inventory.addPart(new InhousePart(
            Inventory.incrementPartId(),
            partNameText.getText(),
            Double.parseDouble(partPriceText.getText()),
            Integer.parseInt(partInvText.getText()),
            Integer.parseInt(partMinText.getText()),
            Integer.parseInt(partMaxText.getText()),
            Integer.parseInt(partCompanyNameText.getText())));
        }
        else{
            Inventory.addPart(new OutsourcedPart(
            Inventory.incrementPartId(),
            partNameText.getText(),
            Double.parseDouble(partPriceText.getText()),
            Integer.parseInt(partInvText.getText()),
            Integer.parseInt(partMinText.getText()),
            Integer.parseInt(partMaxText.getText()),
            partCompanyNameText.getText()));                   
        }
        
        goToMainScreen(event);
       
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes, would you like to proceed?");
      
        Optional<ButtonType> result = alert.showAndWait();
      
         if(result.isPresent() && result.get() == ButtonType.OK){
            partNameText.clear();
            partPriceText.clear();
            partInvText.clear();
            partMinText.clear();
            partMaxText.clear();
            partCompanyNameText.getText();
            partInhouseRButton.setSelected(false);
            partOutsourcedRButton.setSelected(false);
        
        goToMainScreen(event);
        }
    }
}
