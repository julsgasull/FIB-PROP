package main.presentation;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
* @author Aarón García
*/

public class CtrlCreateProblem
{
    private static CtrlCreateProblem singletonObject = null;
    private CtrlPresentacio controladorPresentacio;

    public Label        errorLabel;
    public Button       okButton;
    public Button       cancelButton;
    public TextField    problemName;
    public TextField    nPlays;
    public TextField    fenNotation;

    public static CtrlCreateProblem getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlCreateProblem() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada
     **/

    public CtrlCreateProblem()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    /**
     * Functions
     **/
    public void cancelClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/MainMenu.fxml", (Stage) cancelButton.getScene().getWindow());
    }
    public void okClicked(ActionEvent actionEvent) throws IOException
    {
        if (problemName.getText().length() != 0 && nPlays.getText().length() != 0 && fenNotation.getText().length() != 0)
        {
            if (controladorPresentacio.createProblem(problemName.getText(), Integer.parseInt(nPlays.getText()), fenNotation.getText()))
            {
                controladorPresentacio.changeStage("main/presentation/MainMenu.fxml", (Stage) okButton.getScene().getWindow());
            }
            else writeError("This problem cannot be created. Maybe it is already created or the fen is incorrect");
        }
        else writeError("Incomplete fields");
    }
    public void writeError(String error)
    {
        errorLabel.setText(error);
    }
}
