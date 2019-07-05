package main.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.domini.model.Problem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
* @author Júlia Gasull
*/


public class CtrlProblemMenu
{
    /**
     * Atributes
     */
    private static CtrlProblemMenu singletonObject = null;
    private CtrlPresentacio controladorPresentacio;

    public Label    fenNotation;
    public Label    problemName;
    public Label    nPlays;
    public Button   exitButton;
    public ListView<String> problemList;
    public Button   goBackButton;
    public Button   playProblemButton;
    public Button   deleteProblemButton;
    public Button   modifyProblemButton;

    private String  difficulty;
    private int     problemID;

    ObservableList observableList;

    public static CtrlProblemMenu getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlProblemMenu() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada
     **/

    public CtrlProblemMenu()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }


    public void startProblemMenu()
    {
        Problem actualProblem   = controladorPresentacio.getActualProblem();
        String  probName        = actualProblem.getName();
        String  numPlays        = Integer.toString(actualProblem.getnPlays());
        String  fen             = actualProblem.getFen();
        this.problemID          = actualProblem.getProblemID();
        this.difficulty         = actualProblem.getDificculty();

        //escribir en text fields
        this.nPlays.setText(numPlays);
        this.fenNotation.setText(fen);
        this.problemName.setText(probName);

        //escribir ranking
        Queue<String>           rankingQueue  = controladorPresentacio.getProblemRanking(this.problemID, this.difficulty);
        List<String>            rankingList   = QueueToList(rankingQueue);
        ObservableList<String>  observableList= FXCollections.observableList(rankingList);
        problemList.setItems(observableList);
    }

    /**
     * Functions
     **/

    private List<String> QueueToList(Queue<String> rankingQueue)
    {
        List<String> auxList = new ArrayList<>();
        if (rankingQueue == null) auxList.add("This ranking is empty, be the first to play that problem!!");
        else
        {
            while (!rankingQueue.isEmpty())
            {
                String newLine = getNecessaryData(rankingQueue.peek());
                auxList.add(newLine);
                rankingQueue.remove();
            }
        }
        return auxList;
    }

    private String getNecessaryData(String peek)
    {
        int spaceCounter = 0;
        int startPosition = 0;
        for (int i = 0; spaceCounter < 2 && i < peek.length(); i++)
        {
            char c = peek.charAt(i);
            if (c == ' ') spaceCounter++;
            startPosition++;
        }
        return peek.substring(startPosition);
    }

    public void deleteProblemClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.deleteProblem(this.problemID, difficulty);
        controladorPresentacio.changeStage("main/presentation/SelectOneProblem.fxml", (Stage)deleteProblemButton.getScene().getWindow());
    }
    public void playProblemClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/ChooseOneMatch.fxml", (Stage)playProblemButton.getScene().getWindow());
    }
    public void modifyProblemClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/ModifyProblem.fxml", (Stage)modifyProblemButton.getScene().getWindow());
    }
    public void goBackClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.deleteActualMatch();
        controladorPresentacio.changeStage("main/presentation/SelectOneProblem.fxml", (Stage)goBackButton.getScene().getWindow());
    }
    public void exitClicked(ActionEvent actionEvent)
    {
        // get a handle to the stage
        Stage stage = (Stage) exitButton.getScene().getWindow();
        // do what you have to do
        stage.close(); //close window
    }

    @FXML
    public void initialize()
    {
        startProblemMenu();
    }

}
