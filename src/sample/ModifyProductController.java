package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class ModifyProductController {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @FXML
    private TextField searchBox;
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
    private Label partError;
    @FXML
    private Label errorMsg;

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, String> partsInStock;
    @FXML
    private TableColumn<Part, String> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, String> pricePerPart;

    @FXML
    private TableView<Part> assPartTable;
    @FXML
    private TableColumn<Part, String> assPartsInStock;
    @FXML
    private TableColumn<Part, String> assPartID;
    @FXML
    private TableColumn<Part, String> assPartName;
    @FXML
    private TableColumn<Part, String> assPricePerPart;

    private Inventory inv;
    private String name;
    private int ID;
    private double price;
    private int max;
    private int min;
    private int stock;
    private Product newProd;

    public void setup(Inventory passedInv){         //called before this menu is shown. Receives the Inv object from the main and initializes the tables and text boxes.

        inv = passedInv;
        newProd = new Product(inv.getSelectedProduct().getId(),"Default",1,1,1,1);
        for(Part part:inv.getSelectedProduct().getAllAssociatedParts()){
            newProd.addAssociatedPart(part);
        }
        partTable.getItems().setAll(inv.getAllParts());
        assPartTable.getItems().setAll(newProd.getAllAssociatedParts());
        partsInStock.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partID.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        pricePerPart.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));
        assPartsInStock.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        assPartID.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        assPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        assPricePerPart.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));

        idBox.setText(String.valueOf(inv.getSelectedProduct().getId()));
        nameBox.setText(String.valueOf(inv.getSelectedProduct().getName()));
        invBox.setText(String.valueOf(inv.getSelectedProduct().getStock()));
        priceBox.setText(String.valueOf(inv.getSelectedProduct().getPrice()));
        maxBox.setText(String.valueOf(inv.getSelectedProduct().getMax()));
        minBox.setText(String.valueOf(inv.getSelectedProduct().getMin()));


    }

    @FXML
    public void searchPartList(KeyEvent ke) {                                                              //Called any time something is typed into the Part search field
        if (ke.getCode() == KeyCode.ENTER) {
            String searchText;
            int searchInt;
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            Part tempPart;
            if (searchBox.getText().isEmpty()) {
                partTable.getItems().setAll(inv.getAllParts());
                partError.setVisible(false);
            } else {
                searchText = searchBox.getText();
                try {                                                                    //this attempts to parse the text to an int, and if it fails will process it as a string
                    searchInt = Integer.parseInt(searchText);
                    tempPart = inv.lookupPart(searchInt);
                    if (tempPart != null) {                                                //brings up the relevant product if it exists
                        tempPartList.add(tempPart);
                        partTable.getItems().setAll(tempPartList);
                        partError.setVisible(false);
                    } else {                                                                //outputs error and resets table if it does not
                        partError.setVisible(true);
                        partTable.getItems().setAll(inv.getAllParts());
                    }

                } catch (NumberFormatException e) {                                         //triggers if the text is a non-integer, uses the string method for part lookup
                    tempPartList = inv.lookupPart(searchText);
                    if (tempPartList.isEmpty()) {                                           //Outputs error if there is no matching product
                        partError.setVisible(true);
                        partTable.getItems().setAll(inv.getAllParts());
                    } else {                                                                //Displays the relevant parts in the table
                        partTable.getItems().setAll(tempPartList);
                        partError.setVisible(false);
                    }
                }
            }
        }
    }

    public void associatePart(){
        newProd.addAssociatedPart(partTable.getSelectionModel().getSelectedItem());
        assPartTable.getItems().setAll(newProd.getAllAssociatedParts());
    }

    public void removeAssPart(){
        newProd.deleteAssociatedPart(assPartTable.getSelectionModel().getSelectedItem());
        assPartTable.getItems().setAll(newProd.getAllAssociatedParts());
    }

    public void saveButton() throws Exception{              //triggered when Save button is pressed. Parses the string inputs from each text field to its respective value, tests for legitimacy, and returns errors if a problem occurs.
        errorMsg.setVisible(false);
        if(nameBox.getText().isBlank() || invBox.getText().isBlank() || priceBox.getText().isBlank() || maxBox.getText().isBlank() || minBox.getText().isBlank()){
            errorMsg.setText("Please fill out all fields before saving!");
            errorMsg.setVisible(true);
        }
        else{
            newProd.setName(nameBox.getText());
            try{
                min = Integer.parseInt(minBox.getText());
                if(min<0){
                    errorMsg.setText("The Min field must contain a positive integer.");
                    errorMsg.setVisible(true);
                    return;
                }
                newProd.setMin(min);
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
                newProd.setMax(max);

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
                newProd.setStock(stock);
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
                newProd.setPrice(Double.parseDouble(new DecimalFormat("0.00").format(price)));
            }
            catch (NumberFormatException e){
                errorMsg.setText("The Price field must contain a valid price.");
                errorMsg.setVisible(true);
                return;
            }
            inv.updateProduct(inv.getSelectedIndex(), newProd);
            inv.setProdIDNumber(ID+1);                                                                      //this section passes the newly changed inv object back to the main menu, then closes this window and opens the main menu.
            Stage stage = (Stage) saveButton.getScene().getWindow();
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

    public void cancelButton() throws Exception{
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
