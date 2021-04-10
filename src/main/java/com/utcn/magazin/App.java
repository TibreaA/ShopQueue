package com.utcn.magazin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX com.utcn.polynom.App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        stage.setScene(new Scene(root, 1080.0, 810.0));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}