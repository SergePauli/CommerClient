package com.cbs.commerclient;

import com.cbs.commerclient.model.User;
import com.cbs.commerclient.service.RestClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * корень приложения JavaFX
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public  static Stage aStage;
    
    public static RestClient restClient = null;
    public static User loggedUser = null;
    private final double MINIMUM_WINDOW_WIDTH = 780.0;
    private final double MINIMUM_WINDOW_HEIGHT = 640.0;
    

    @Override
    public void start(Stage stage) throws IOException {        
        scene = new Scene(loadFXML("login"),MINIMUM_WINDOW_WIDTH, MINIMUM_WINDOW_HEIGHT);
        aStage = stage;        
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}