package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeleteProductController {

    private Stage mainStage;
    private Inventory inv;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;



    public void setup(Inventory passedInv, Stage passedStage){      //takes the stage and inventory object from the main menu
        inv = passedInv;
        mainStage = passedStage;
    }

    public void okButton() throws Exception{                        //confirms deletion, returns to main menu
        inv.deleteProduct(inv.getSelectedProduct());
        Stage stage = (Stage) okButton.getScene().getWindow();
        Stage newStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main Menu.fxml"));
        Parent root = loader.load();
        MainMenuController mainMenuController = loader.getController();
        mainMenuController.backToMenu(inv);
        newStage.setScene(new Scene(root));
        mainStage.close();
        stage.close();
        newStage.show();
    }

    public void cancelButton() throws Exception{            //closes current window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
