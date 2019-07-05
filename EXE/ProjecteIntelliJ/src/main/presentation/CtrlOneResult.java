package main.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class CtrlOneResult {
    private static CtrlOneResult singletonObject = null;
    public Button goBackButton;
    public Button exitButton;
    public Label whoWins;
    CtrlPresentacio controladorPresentacio;


    public static CtrlOneResult getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlOneResult() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada
     **/

    public CtrlOneResult()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    @FXML
    public void initialize() {
        String matchInfo = controladorPresentacio.getInfoMatch();
        whoWins.setText(matchInfo);
    }

    public void showResults(MouseEvent mouseEvent) {
    }


    public void goBackClicked(ActionEvent actionEvent) throws IOException {
        controladorPresentacio.changeStage("main/presentation/ChooseOneMatch.fxml", (Stage)goBackButton.getScene().getWindow());
    }

    public void exitClicked(ActionEvent actionEvent) {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close(); //close window
    }
}
