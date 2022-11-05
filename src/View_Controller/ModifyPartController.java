/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import static Model.Inventory.getAllParts;
import Model.OutsourcedPart;
import Model.Part;
import static View_Controller.MainScreenController.getModPartIndex;
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
public class ModifyPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private boolean isInhouse;
    private final int index = getModPartIndex();
    private int partId;
    
    
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Part part = Inventory.getAllParts().get(index);
        partId = Inventory.getAllParts().get(index).getId();
        partIDText.setText("Auto-Gen: " + Integer.toString(partId));
        partNameText.setText(part.getName());
        partInvText.setText(Integer.toString(part.getStock()));
        partPriceText.setText(Double.toString(part.getPrice()));
        partMinText.setText(Integer.toString(part.getMin()));
        partMaxText.setText(Integer.toString(part.getMax()));
        
        if (part instanceof InhousePart) {
            companyNameLbl.setText("Machine ID");
            partCompanyNameText.setText(Integer.toString(((InhousePart) getAllParts().get(index)).getMachineId()));
            partInhouseRButton.setSelected(true);
        }
        else {
            companyNameLbl.setText("Company Name");
            partCompanyNameText.setText(((OutsourcedPart) getAllParts().get(index)).getCompanyName());
            partOutsourcedRButton.setSelected(true);
        }
        
    }    

    @FXML
    private void onActionPartInhouse(ActionEvent event) {
        isInhouse = true;
        companyNameLbl.setText("Machine ID");
        partCompanyNameText.setText("");
        partCompanyNameText.setPromptText("Mach ID");
    }

    @FXML
    private void onActionPartOutsourced(ActionEvent event) {
        isInhouse = false;
        companyNameLbl.setText("Company Name");
        partCompanyNameText.setText("");
        partCompanyNameText.setPromptText("Company Nm");
    }

    @FXML
    private void onActionAddPart(ActionEvent event) throws IOException {
        boolean isInhouse;      
        isInhouse = partInhouseRButton.isSelected();
        
        if(isInhouse){   
            Part inPart = new InhousePart(
            partId,
            partNameText.getText(),
            Double.parseDouble(partPriceText.getText()),
            Integer.parseInt(partInvText.getText()),
            Integer.parseInt(partMinText.getText()),
            Integer.parseInt(partMaxText.getText()),
            Integer.parseInt(partCompanyNameText.getText()));
            
            Inventory.updatePart(index, inPart);
        }
        else{
            Part outPart = new OutsourcedPart (
            partId,
            partNameText.getText(),
            Double.parseDouble(partPriceText.getText()),
            Integer.parseInt(partInvText.getText()),
            Integer.parseInt(partMinText.getText()),
            Integer.parseInt(partMaxText.getText()),
            partCompanyNameText.getText());  
            
            Inventory.updatePart(index, outPart);
        }
    
        goToMainScreen(event);
    }
    
      public void goToMainScreen (ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will cancel any changes, would you like to proceed?");
      
        Optional<ButtonType> result = alert.showAndWait();
      
         if(result.isPresent() && result.get() == ButtonType.OK){      
            partIDText.clear();
            partNameText.clear();
            partInvText.clear();
            partPriceText.clear();
            partMinText.clear();
            partMaxText.clear();
            partCompanyNameText.clear();
            partInhouseRButton.setSelected(false);
            partOutsourcedRButton.setSelected(false);
        
        
            goToMainScreen(event);
        }
    } 
}
