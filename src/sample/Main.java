package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {




//The JavaDoc files can be found in the 'JavaDoc Files' Folder in the zip

    @Override
    public void start(Stage primaryStage) throws Exception {            //Main simply opens the Main Menu

        Parent root = FXMLLoader.load(getClass().getResource("Main Menu.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }




}



