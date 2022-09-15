package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DeletePartController {

    private Stage mainStage;
    private Inventory inv;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;



    public void setup(Inventory passedInv, Stage passedStage){      //receives the passed stage and object from main
        inv = passedInv;
        mainStage = passedStage;
    }

    public void okButton() throws Exception{        //deletes selected part
        inv.deletePart(inv.getSelectedPart());
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

    public void cancelButton(){         //closes current window
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
