package main.presentation;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

/**
* @author Sergi Sendrós
*/


public class CtrlChooseMoreMatches
{
    private CtrlPresentacio controladorPresentacio;
    private static CtrlChooseMoreMatches singletonObject = null;
    public RadioButton  m1m1;
    public RadioButton  m1m2;
    public RadioButton  m2m1;
    public RadioButton  m2m2;
    public Button       okButton;
    public Button       cancelButton;
    public Label        errorLabel;

    private String matchType = null;

    public static CtrlChooseMoreMatches getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlChooseMoreMatches() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/

    public CtrlChooseMoreMatches()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    public void setm1m1(ActionEvent actionEvent) // machine1 vs machine1
    {
        matchType = "m1m1";
    }
    public void setm1m2(ActionEvent actionEvent) // machine1 vs machine2
    {
        matchType = "m1m2";
    }
    public void setm2m1(ActionEvent actionEvent) // machine2 vs machine1
    {
        matchType = "m2m1";
    }
    public void setm2m2(ActionEvent actionEvent) // machine2 vs machine2
    {
        matchType = "m2m2";
    }


    public void cancelClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.deleteMoreMatches();
        controladorPresentacio.changeStage("main/presentation/SelectMOREProblems.fxml", (Stage)cancelButton.getScene().getWindow());
    }

    public void okClicked(ActionEvent actionEvent) throws IOException
    {
        if (matchType == null) writeError("No selected type.");
        else
        {
            controladorPresentacio.setMatchType(matchType);
            controladorPresentacio.changeStage("main/presentation/PlayingMatches.fxml", (Stage)okButton.getScene().getWindow());
        }
    }

    public void writeError(String error)
    {
        errorLabel.setText(error);
    }
}
