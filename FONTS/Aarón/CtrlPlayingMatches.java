package main.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;


/**
* @author Aarón García
*/


public class CtrlPlayingMatches
{

    private static CtrlPlayingMatches singletonObject = null;
    public ListView resultsList;
    public AnchorPane anchor;
    public Label loading;
    CtrlPresentacio controladorPresentacio;
    public Button playAndShow;
    public Button goBackButton;
    public Button exitButton;


    /**
     *
     * Constructora i singleton
     */

    public static CtrlPlayingMatches getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlPlayingMatches() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada
     **/

    public CtrlPlayingMatches()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    public void playAndShowClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.playAndShowClicked();
        controladorPresentacio.changeStage("main/presentation/Results.fxml", (Stage)goBackButton.getScene().getWindow());
    }

    public void goBackClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/SelectProblem.fxml", (Stage)goBackButton.getScene().getWindow());
    }

    public void exitClicked(ActionEvent actionEvent)
    {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close(); //close window
    }
}
