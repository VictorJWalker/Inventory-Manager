package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, String> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, String> partsInStock;
    @FXML
    private TableColumn<Part, String> pricePerPart;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productID;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, String> productsInStock;
    @FXML
    private TableColumn<Product, String> pricePerUnit;

    @FXML
    private TextField searchPartList;
    @FXML
    private TextField searchProductList;

    @FXML
    private Label partError;
    @FXML
    private Label productError;
    @FXML
    private Label partSelectionError;
    @FXML
    private Label productSelectionError;

    @FXML
    private Button modifyPartButton;
    @FXML
    private Button addPartButton;
    @FXML
    private Button deletePartButton;
    @FXML
    private Button modifyProductButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button deleteProductButton;
    @FXML
    private Button exitButton;

    private Inventory inv = new Inventory();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {                        //runs whenever the main menu is started

        if(inv.getAllParts().isEmpty()) {
            Part startPart = new InHouse(1, "chain", 18.50, 4, 2, 10, 82);
            Part startPart2 = new InHouse(2, "wheel", 20.11, 7, 2, 20, 10);
            Product startProd = new Product(1, "bike plus", 200.31, 6, 1, 20);
            Product startProd2 = new Product(2, "bike minus", 50.12, 2, 1, 5);
            startProd.addAssociatedPart(startPart);
            inv.addPart(startPart);
            inv.addPart(startPart2);
            inv.addProduct(startProd);
            inv.addProduct(startProd2);
        }

        partTable.getItems().setAll(inv.getAllParts());                                         //This section initializes the table to show the starting values
        productTable.getItems().setAll(inv.getAllProducts());
        partsInStock.setCellValueFactory(new PropertyValueFactory<Part, String>("stock"));
        partID.setCellValueFactory(new PropertyValueFactory<Part, String>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        pricePerPart.setCellValueFactory(new PropertyValueFactory<Part, String>("price"));
        productID.setCellValueFactory(new PropertyValueFactory<Product, String>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productsInStock.setCellValueFactory(new PropertyValueFactory<Product, String>("stock"));
        pricePerUnit.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));

    }
    public void backToMenu(Inventory passedInv){                                            //accepts object from other controllers, then re-initializes table
        inv = passedInv;
        partTable.getItems().setAll(inv.getAllParts());
        productTable.getItems().setAll(inv.getAllProducts());

    }

    @FXML
    public void openAddPartMenu() throws Exception{                                            //Called by pressing the Add button on the Main Menu parts table. Opens the add part menu.

        Stage stage = (Stage) addPartButton.getScene().getWindow();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add Part.fxml"));
        Parent root = loader.load();
        AddPartController addPartController = loader.getController();
        addPartController.setup(inv);
        newStage.setScene(new Scene(root));
        stage.close();
        newStage.show();
    }
    @FXML
    public void openAddProductMenu() throws Exception{                                         //Called by pressing the Add button on the Main Menu product table. Opens the add product menu.
        Stage stage = (Stage) addProductButton.getScene().getWindow();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add Product.fxml"));
        Parent root = loader.load();
        AddProductController addProductController = loader.getController();
        addProductController.setup(inv);
        newStage.setScene(new Scene(root));
        stage.close();
        newStage.show();
    }

    @FXML
    public void openModifyPartMenu() throws Exception {                                                //Called by pressing the Modify button on the Main Menu parts table. Opens the modify parts menu.
        if(partTable.getSelectionModel().getSelectedItem() != null) {
            inv.setSelectedPart(partTable.getSelectionModel().getSelectedItem());
            inv.setSelectedIndex(partTable.getSelectionModel().getSelectedIndex());
            Stage stage = (Stage) modifyPartButton.getScene().getWindow();
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Modify Part.fxml"));
            Parent root = loader.load();
            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.setup(inv);
            newStage.setScene(new Scene(root));
            stage.close();
            newStage.show();
        }
        else{
            partSelectionError.setVisible(true);
        }
    }

    @FXML
    public void openModifyProductMenu() throws Exception {                                              //Called by pressing the Modify button on the Main Menu product table

        if(productTable.getSelectionModel().getSelectedItem() != null) {
            inv.setSelectedProduct(productTable.getSelectionModel().getSelectedItem());
            inv.setSelectedIndex(productTable.getSelectionModel().getSelectedIndex());
            Stage stage = (Stage) modifyProductButton.getScene().getWindow();
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Modify Product.fxml"));
            Parent root = loader.load();
            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.setup(inv);
            newStage.setScene(new Scene(root));
            stage.close();
            newStage.show();
        }
        else{
            productSelectionError.setText("No product selected!");
            productSelectionError.setVisible(true);
        }
    }

    @FXML
    public void openDeletePartMenu() throws Exception{                                    //Called by pressing the delete button on the Main Menu parts table.

        if(partTable.getSelectionModel().getSelectedItem() != null) {
            inv.setSelectedPart(partTable.getSelectionModel().getSelectedItem());
            inv.setSelectedIndex(partTable.getSelectionModel().getSelectedIndex());
            Stage newStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete Part Menu.fxml"));
            Parent root = loader.load();
            DeletePartController deletePartController = loader.getController();
            deletePartController.setup(inv, (Stage) deletePartButton.getScene().getWindow());
            newStage.setScene(new Scene(root));
            newStage.show();
        }
        else{
            partSelectionError.setVisible(true);
        }
    }

    public void openDeleteProductMenu() throws Exception{                           //Called by pressing the delete button on the Main menu products table.
        if(productTable.getSelectionModel().getSelectedItem() != null) {
            if(productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                inv.setSelectedProduct(productTable.getSelectionModel().getSelectedItem());
                inv.setSelectedIndex(productTable.getSelectionModel().getSelectedIndex());
                Stage newStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Delete Product Menu.fxml"));
                Parent root = loader.load();
                DeleteProductController deleteProductController = loader.getController();
                deleteProductController.setup(inv, (Stage) deleteProductButton.getScene().getWindow());
                newStage.setScene(new Scene(root));
                newStage.show();
            }
            else{
                productSelectionError.setText("Cannot delete a product with associated parts.");
                productSelectionError.setVisible(true);
            }
        }
        else{
            productSelectionError.setText("No product selected!");
            productSelectionError.setVisible(true);
        }
    }

    @FXML
    public void searchPartList(KeyEvent ke) {                                                              //Called any time something is typed into the Part search field
        if (ke.getCode() == KeyCode.ENTER) {
            String searchText;
            int searchInt;
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            Part tempPart;
            if (searchPartList.getText().isEmpty()) {
                partTable.getItems().setAll(inv.getAllParts());
                partError.setVisible(false);
            } else {
                searchText = searchPartList.getText();
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

    @FXML
    public void searchProductList(KeyEvent ke) {                                                                 //Called any time something is typed into the Product search field
        {
            if (ke.getCode() == KeyCode.ENTER) {
                String searchText;
                int searchInt;
                ObservableList<Product> tempProdList = FXCollections.observableArrayList();
                Product tempProd;
                if (searchProductList.getText().isEmpty()) {
                    productTable.getItems().setAll(inv.getAllProducts());
                    productError.setVisible(false);
                } else {
                    searchText = searchProductList.getText();
                    try {                                                                    //this attempts to parse the text to an int, and if it fails will process it as a string
                        searchInt = Integer.parseInt(searchText);
                        tempProd = inv.lookupProduct(searchInt);
                        if (tempProd != null) {                                             //brings up the relevant product if it exists
                            tempProdList.add(tempProd);
                            productTable.getItems().setAll(tempProdList);
                            productError.setVisible(false);
                        } else {                                                            //outputs error and resets table if it does not
                            productError.setVisible(true);
                            productTable.getItems().setAll(inv.getAllProducts());
                        }

                    } catch (NumberFormatException e) {                                     //triggers if the text is a non-integer, uses the string method for product lookup
                        tempProdList = inv.lookupProduct(searchText);
                        if (tempProdList.isEmpty()) {                                       //Outputs error if there is no matching product
                            productError.setVisible(true);
                            productTable.getItems().setAll(inv.getAllProducts());
                        } else {                                                            //Displays the relevant products in the table
                            productTable.getItems().setAll(tempProdList);
                            productError.setVisible(false);
                        }
                    }
                }
            }
        }
    }

    public void exitProgram(){                  //closes the program
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
