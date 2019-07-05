package main.presentation;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
* @author Júlia Gasull
*/


public class CtrlWelcomeMenu
{
    public CtrlPresentacio controladorPresentacio;

    public static CtrlWelcomeMenu singletonObject = null;

    public Button signUpButton;
    public Button logInButton;
    public Button exitButton;

    public static CtrlWelcomeMenu getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlWelcomeMenu() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada
     **/
    public CtrlWelcomeMenu()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
        /**
         * No hacen falta, el usuario se crea en log in y sign up, y el problema en ...
         * controladorPresentacio.setActualUser(null);
         * controladorPresentacio.setActualProblem(-1);
         * controladorPresentacio.setProblemQueue(null);
         */
    }

    /**
     * Functions
     **/
    public void SignUpClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/SignUp.fxml", (Stage) exitButton.getScene().getWindow());
    }
    public void LogInClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/LogIn.fxml", (Stage) exitButton.getScene().getWindow());
    }
    public void exitClicked(ActionEvent actionEvent)
    {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close(); //close window
    }
}
