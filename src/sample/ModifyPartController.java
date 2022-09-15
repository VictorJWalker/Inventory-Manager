package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.text.DecimalFormat;

public class ModifyPartController{

    @FXML
    private RadioButton outsourcedButton;
    @FXML
    private RadioButton inHouseButton;
    @FXML
    private Label variableLabel;
    @FXML
    private TextField idBox;
    @FXML
    private TextField nameBox;
    @FXML
    private TextField invBox;
    @FXML
    private TextField priceBox;
    @FXML
    private TextField maxBox;
    @FXML
    private TextField minBox;
    @FXML
    private TextField variableBox;
    @FXML
    private Label errorMsg;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Inventory inv;
    private int ID;
    private String name;
    private int stock;
    private double price;
    private int max;
    private int min;
    private int machineID;
    private String companyName;


    public void setup(Inventory passedInv){         //receives the inv object from the main
        inv = passedInv;
        Part passedPart = inv.getSelectedPart();
        idBox.setText(String.valueOf(passedPart.getId()));
        nameBox.setText(String.valueOf(passedPart.getName()));
        invBox.setText(String.valueOf(passedPart.getStock()));
        priceBox.setText(String.valueOf(passedPart.getPrice()));
        maxBox.setText(String.valueOf(passedPart.getMax()));
        minBox.setText(String.valueOf(passedPart.getMin()));

        if(passedPart instanceof InHouse){
            InHouse inHousePart = (InHouse) passedPart;
            inHouseButton.setSelected(true);
            outsourcedButton.setSelected(false);
            variableBox.setText(String.valueOf((inHousePart.getMachineID())));
        }
        else{
            Outsourced outsourcedPart = (Outsourced) passedPart;
            inHouseButton.setSelected(false);
            outsourcedButton.setSelected(true);
            variableBox.setText(outsourcedPart.getCompanyName());
            variableLabel.setText("Company Name");
        }



    }

    public void inHouseButton(){           //triggered when In-House button is pressed
        outsourcedButton.setSelected(false);
        inHouseButton.setSelected(true);
        variableLabel.setText("Machine ID");
        variableBox.clear();
    }

    public void outsourcedButton(){        //triggered when Outsourced button is pressed
        outsourcedButton.setSelected(true);
        inHouseButton.setSelected(false);
        variableLabel.setText("Company Name");
        variableBox.clear();
    }

    public void saveButton() throws Exception{              //triggered when Save button is pressed. Parses the string inputs from each text field to its respective value, tests for legitimacy, and returns errors if a problem occurs.
        errorMsg.setVisible(false);
        if(nameBox.getText().isBlank() || invBox.getText().isBlank() || priceBox.getText().isBlank() || maxBox.getText().isBlank() || minBox.getText().isBlank() || variableBox.getText().isBlank()){
            errorMsg.setText("Please fill out all fields before saving!");
            errorMsg.setVisible(true);
        }
        else{
            ID = inv.getSelectedPart().getId();
            name = nameBox.getText();
            try{
                min = Integer.parseInt(minBox.getText());
                if(min<0){
                    errorMsg.setText("The Min field must contain a positive integer.");
                    errorMsg.setVisible(true);
                    return;
                }
            }
            catch (NumberFormatException e){
                errorMsg.setText("The Min field must contain a positive integer.");
                errorMsg.setVisible(true);
                return;
            }

            try{
                max = Integer.parseInt(maxBox.getText());

                if(max<min){
                    errorMsg.setText("The Max must be at least as high as the Min.");
                    errorMsg.setVisible(true);
                    return;
                }

            }
            catch (NumberFormatException e){
                errorMsg.setText("The Max field must contain a positive integer.");
                errorMsg.setVisible(true);
                return;
            }

            try{
                stock = Integer.parseInt(invBox.getText());
                if( min > stock || max < stock){
                    errorMsg.setText("Inv must be between Min and Max.");
                    errorMsg.setVisible(true);
                    return;
                }
            }
            catch (NumberFormatException e){
                errorMsg.setText("The Inv field must contain a positive integer.");
                errorMsg.setVisible(true);
                return;
            }

            try {
                price = Double.parseDouble(priceBox.getText());
                if(price < 0){
                    errorMsg.setText("The Price field must contain a valid price.");
                    errorMsg.setVisible(true);
                    return;
                }
                price = Double.parseDouble(new DecimalFormat("0.00").format(price));
            }
            catch (NumberFormatException e){
                errorMsg.setText("The Price field must contain a valid price.");
                errorMsg.setVisible(true);
                return;
            }
            if(inHouseButton.isSelected()){
                try{
                    machineID = Integer.parseInt(variableBox.getText());
                }
                catch (NumberFormatException e){
                    errorMsg.setText("The Machine ID field must contain an integer.");
                    errorMsg.setVisible(true);
                    return;
                }
                Part newPart = new InHouse(ID,name,price,stock,min,max,machineID);
                inv.updatePart(inv.getSelectedIndex(),newPart);
            }
            else{
                companyName = variableBox.getText();
                Part newPart = new Outsourced(ID,name,price,stock,min,max,companyName);
                inv.updatePart(inv.getSelectedIndex(),newPart);
            }

            Stage stage = (Stage) saveButton.getScene().getWindow(); //this section passes the newly changed inv object back to the main menu, then closes this window and opens the main menu.
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main Menu.fxml"));
            Parent root = loader.load();
            MainMenuController mainMenuController = loader.getController();
            mainMenuController.backToMenu(inv);
            newStage.setScene(new Scene(root));
            stage.close();
            newStage.show();
        }
    }

    public void cancelButton() throws Exception{             //triggered when cancel button is pressed, closes the window and returns to main menu
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main Menu.fxml"));
        Parent root = loader.load();
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.backToMenu(inv);
        newStage.setScene(new Scene(root));
        stage.close();
        newStage.show();
    }


}
