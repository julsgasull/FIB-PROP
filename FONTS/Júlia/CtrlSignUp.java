package main.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
* @author Júlia Gasull
*/


public class CtrlSignUp
{
    public Button           okButton;
    public Button           cancelButton;
    public PasswordField    password;
    public TextField        username;
    public PasswordField    repeatPassword;
    public Label            errorLabel;
    /**
     * Atributes
     */

    public CtrlPresentacio controladorPresentacio;

    public static CtrlSignUp singletonObject;

    public static CtrlSignUp getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlSignUp() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/

    public CtrlSignUp()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    /**
     * Funciones Publicas
     */


    private void doSignUp() throws IOException
    {
        if (controladorPresentacio.doSignUp(username.getText(), password.getText()))
        {
            controladorPresentacio.changeStage("main/presentation/WelcomeMenu.fxml", (Stage) okButton.getScene().getWindow());
        }
        else writeError("This username is already used");
    }

    public void cancelClicked(javafx.event.ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/WelcomeMenu.fxml", (Stage) cancelButton.getScene().getWindow());
    }

    public void okClicked (ActionEvent actionEvent) throws IOException
    {
        if (username.getText().length() != 0 && password.getText().length() != 0 && repeatPassword.getText().length() != 0)
        {
            if (repeatPassword.getText().equals(password.getText()))
            {
                doSignUp();
            }
            else writeError("The passwords doesn't match");
        }
        else writeError("Incomplete Fields");
    }

    public void writeError(String error)
    {
        errorLabel.setText(error);
    }



}
