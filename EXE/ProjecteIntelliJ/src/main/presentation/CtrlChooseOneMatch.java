package main.presentation;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class CtrlChooseOneMatch
{
    private CtrlPresentacio controladorPresentacio;
    private static CtrlChooseOneMatch singletonObject = null;

    public RadioButton  h1h2;
    public RadioButton  h1m1;
    public RadioButton  h1m2;
    public RadioButton  m1m1;
    public RadioButton  m1m2;
    public RadioButton  m2m1;
    public RadioButton  m2m2;
    public Button       okButton;
    public Button       cancelButton;
    public Label        errorLabel;

    private String matchType = null;

    public static CtrlChooseOneMatch getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlChooseOneMatch() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/

    public CtrlChooseOneMatch()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    public void seth1h2(ActionEvent actionEvent) // you vs friend
    {
        matchType = "h1h2";
    }
    public void seth1m1(ActionEvent actionEvent) // you vs machine1
    {
        matchType = "h1m1";
    }
    public void seth1m2(ActionEvent actionEvent) // you vs machine 2
    {
        matchType = "h1m2";
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
        controladorPresentacio.changeStage("main/presentation/ProblemMenu.fxml", (Stage)cancelButton.getScene().getWindow());
    }
    public void okClicked(ActionEvent actionEvent) throws IOException
    {
        if (matchType == null) writeError("No selected type.");
        else
        {
            controladorPresentacio.crearMatch(matchType);
            if (matchType.equals("h1h2") || matchType.equals("h1m1") || matchType.equals("h1m2")) {
                chessBoardGUIJavafx c = new chessBoardGUIJavafx();
            }
            else {
                controladorPresentacio.changeStage("main/presentation/OneResult.fxml", (Stage)cancelButton.getScene().getWindow());
            }
        }
    }
    public void writeError(String error)
    {
        errorLabel.setText(error);
    }
}
