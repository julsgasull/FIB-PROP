package main.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

/**
* @author Júlia Gasull
*/


public class CtrlResults
{

    /**
     * Attributes
     */

    private static CtrlResults singletonObject = null;
    public Button   goBackButton;
    public Button   exitButton;
    public ListView resultsList;
    CtrlPresentacio controladorPresentacio;


    public static CtrlResults getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlResults() {};
        }
        return singletonObject;
    }

    /**
     * Constructora privada
     **/

    public CtrlResults()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    public void showResults()
    {
        Vector<String>          results         = controladorPresentacio.getResults();
        List<String>            resultsList     = VectorToList(results);
        ObservableList<String>  observableList  = FXCollections.observableList(resultsList);
        this.resultsList.setItems(observableList);
    }

    private List<String> VectorToList(Vector<String> results)
    {
        List<String> auxList = new ArrayList<>();
        if (results == null) auxList.add("No games had been played");
        else
        {
            for (int i = 0; i < results.size(); ++i)
            {
                auxList.add(results.elementAt(i));
            }
        }
        return auxList;
    }

    public void goBackClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/SelectProblem.fxml", (Stage)goBackButton.getScene().getWindow());
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
        showResults();
    }
}
