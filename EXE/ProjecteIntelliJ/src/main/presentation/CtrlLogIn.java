package main.presentation;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.domini.controladorsDeDomini.CtrlDomain;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class CtrlLogIn {
    /**
     * Atributes
     */
    private CtrlPresentacio controladorPresentacio;
    private static CtrlLogIn singletonObject = null;

    public Label            errorLabel;
    public Button           okButton;
    public Button           cancelButton;
    public TextField        username;
    public PasswordField    password;

    public static CtrlLogIn getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlLogIn(){};
        }
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/

    public CtrlLogIn()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    /**
     * Functions
     */
    public void okClicked(javafx.event.ActionEvent actionEvent) throws IOException
    {
        if (username.getText().length() != 0 && password.getText().length() != 0)
        {
            if (controladorPresentacio.doLogIn(username.getText(), password.getText()))
            {
                controladorPresentacio.changeStage("main/presentation/MainMenu.fxml", (Stage)okButton.getScene().getWindow());
            }
            else writeError("Username or password incorrect");
        }
        else writeError("Incomplete fields");
    }
    public void cancelClicked(javafx.event.ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/WelcomeMenu.fxml", (Stage) cancelButton.getScene().getWindow());
    }
    public void writeError(String error)
    {
        errorLabel.setText(error);
    }

}
