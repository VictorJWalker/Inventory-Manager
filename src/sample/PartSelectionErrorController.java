package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PartSelectionErrorController {

    @FXML
    private Button okButton;

    public void closeWindow(){
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
