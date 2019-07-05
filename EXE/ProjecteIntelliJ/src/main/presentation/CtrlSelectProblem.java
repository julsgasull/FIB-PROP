package main.presentation;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

public class CtrlSelectProblem
{
    private static CtrlSelectProblem singletonObject;
    private CtrlPresentacio controladorPresentacio;
    
    public Button   selectOneProblemButton;
    public Button   selectMoreProblemsButton;
    public Button   goBackButton;
    
    public static CtrlSelectProblem getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlSelectProblem() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/
    public CtrlSelectProblem()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    /**
     * Functions
     **/
    public void selectOneClicked(ActionEvent actionEvent) throws IOException 
    {
        controladorPresentacio.changeStage("main/presentation/SelectOneProblem.fxml", (Stage)selectOneProblemButton.getScene().getWindow());
    }
    public void selectMoreClicked(ActionEvent actionEvent) throws IOException 
    {
        controladorPresentacio.changeStage("main/presentation/SelectMOREProblems.fxml", (Stage)selectMoreProblemsButton.getScene().getWindow());
    }
    public void goBackClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/MainMenu.fxml", (Stage)goBackButton.getScene().getWindow());
    }
}
