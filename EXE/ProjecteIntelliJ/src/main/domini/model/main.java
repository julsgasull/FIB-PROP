package main.domini.model;

//import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application
{
    /**
     * Main
     */
    public static void main(String[] args) throws Exception
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main/presentation/WelcomeMenu.fxml"));
        primaryStage.setTitle("Cheessy");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}