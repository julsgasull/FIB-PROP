package main.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.domini.model.Problem;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class CtrlModifyProblem
{
    private static CtrlModifyProblem singletonObject = null;
    public Label problemName;
    public Label nPlays;
    public Label fenNotation;
    private CtrlPresentacio controladorPresentacio;

    public Button       cancelButton;
    public Button       okButton;
    public Label        errorLabel;
    public TextField    newProblemName;
    public TextField    newNPlays;
    public TextField    newFenNotation;


    public static CtrlModifyProblem getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlModifyProblem() {};
        }
        return singletonObject;
    }

    public void startModifyProblem() {
        Problem actualProblem = controladorPresentacio.getActualProblem();
        String probName = actualProblem.getName();
        String numPlays = Integer.toString(actualProblem.getnPlays());
        String fen = actualProblem.getFen();
        int problemID = actualProblem.getProblemID();
        String difficulty = actualProblem.getDificculty();

        //escribir en text fields
        fenNotation.setText(fen);
        this.nPlays.setText(numPlays);
        this.problemName.setText(probName);
    }

    public CtrlModifyProblem()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    public void cancelClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/ProblemMenu.fxml", (Stage)cancelButton.getScene().getWindow());
    }

    public void okClicked(ActionEvent actionEvent) throws IOException
    {
        if (newProblemName.getText().length() == 0) System.out.println("fen null");
        if (newNPlays == null) System.out.println("plays null");
        if (newProblemName == null) System.out.println("name null");
        if (newProblemName.getText().length() != 0 && newNPlays.getText().length() != 0 && newFenNotation.getText().length() != 0)
        {
            if (controladorPresentacio.modifyActualProblem(newProblemName.getText(), Integer.valueOf(newNPlays.getText()), newFenNotation.getText()))
            {
                controladorPresentacio.changeStage("main/presentation/ProblemMenu.fxml", (Stage) okButton.getScene().getWindow());
            }
            else writeError("This problem cannot be modified");
        }
        else writeError("Incomplete fields");
    }

    public void writeError(String error)
    {
        errorLabel.setText(error);
    }

}
