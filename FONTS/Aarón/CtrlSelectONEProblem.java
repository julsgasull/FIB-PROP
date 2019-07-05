package main.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
* @author Aarón García
*/

public class CtrlSelectONEProblem
{
    private static CtrlSelectONEProblem singletonObject;

    public ListView<String>     problemList;
    public Button               veryEasy;
    public Button               easy;
    public Button               normal;
    public Button               hard;
    public Button               veryHard;

    private CtrlPresentacio     controladorPresentacio;

    public Button       cancelButton;
    public Button       okButton;
    public TextField    problemIDTextField;
    public Label        errorLabel;

    public String difficulty = null;


    public static CtrlSelectONEProblem getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlSelectONEProblem() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/
    public CtrlSelectONEProblem()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    /**
     * Functions
     **/
    public void cancelClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/SelectProblem.fxml", (Stage) cancelButton.getScene().getWindow());
    }

    public void okClicked(ActionEvent actionEvent) throws IOException
    {
        String input = problemIDTextField.getText();
        int problemID = Integer.parseInt(input);
        controladorPresentacio.setActualProblem(problemID, difficulty);
        controladorPresentacio.changeStage("main/presentation/ProblemMenu.fxml", (Stage) okButton.getScene().getWindow());
    }

    public void getVeryEasyProblems(ActionEvent actionEvent)
    {
        Queue<String> q = controladorPresentacio.getVeryEasyProblems();
        showList(q);
        this.difficulty = "VeryEasy";
    }
    public void getEasyProblems(ActionEvent actionEvent)
    {
        Queue<String> q = controladorPresentacio.getEasyProblems();
        showList(q);
        this.difficulty = "Easy";
    }
    public void getNormalProblems(ActionEvent actionEvent)
    {
        Queue<String> q = controladorPresentacio.getNormalProblems();
        showList(q);
        this.difficulty = "Normal";
    }
    public void getVeryHardProblem(ActionEvent actionEvent)
    {
        Queue<String> q = controladorPresentacio.getVeryHardProblems();
        showList(q);
        this.difficulty = "VeryHard";
    }
    public void getHardProblems(ActionEvent actionEvent)
    {
        Queue<String> q = controladorPresentacio.getHardProblems();
        showList(q);
        this.difficulty = "Hard";
    }

    /**
     * Private Functions
     */
    private void showList(Queue<String> q)
    {
        List<String> l = new ArrayList<>();
        if (q == null)
        {
            l.add("There are no problems with that difficulty");
        }
        else
        {
            while (!q.isEmpty())
            {
                l.add(q.peek());
                q.remove();
            }
        }
        ObservableList<String> observableList = FXCollections.observableList(l);
        problemList.setItems(observableList);
    }

    public void selectProblem(MouseEvent mouseEvent) throws IOException
    {
        String a = problemList.getSelectionModel().getSelectedItem();
        if (a.equals("There are no problems with that difficulty")) errorLabel.setText("This is not a problem");
        else
        {
            controladorPresentacio.setActualProblem(getID(a), difficulty);
            controladorPresentacio.changeStage("main/presentation/ProblemMenu.fxml", (Stage) problemList.getScene().getWindow());
        }

    }

    private int getID(String a)
    {
        String ID = "";
        boolean trobat = false;
        for (int i = 0; i < a.length() && !trobat; ++i)
        {
            if (a.charAt(i) != ' ')
            {
                ID += a.charAt(i);
            }
            trobat = true;
        }
        return Integer.parseInt(ID);
    }
}
