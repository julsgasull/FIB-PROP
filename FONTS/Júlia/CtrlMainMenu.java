package main.presentation;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CtrlMainMenu
{
    private static CtrlMainMenu singletonObject = null;
    private CtrlPresentacio controladorPresentacio;

    public Button createProblemButton;
    public Button selectProblemButton;
    public Button logOutButton;
    public Button exitButton;


    public static CtrlMainMenu getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlMainMenu() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/

    public CtrlMainMenu()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    public void selectProblem(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/SelectProblem.fxml", (Stage)selectProblemButton.getScene().getWindow());
    }

    public void createProblem(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/CreateProblem.fxml", (Stage)createProblemButton.getScene().getWindow());
    }

    public void logOutClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.doLogOut((Stage)logOutButton.getScene().getWindow());
    }

    public void exitClicked(ActionEvent actionEvent)
    {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close(); //close window
    }
}
