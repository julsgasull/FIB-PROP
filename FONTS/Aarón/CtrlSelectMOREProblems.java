package main.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
* @author Aarón García
*/

public class CtrlSelectMOREProblems
{
    private static CtrlSelectMOREProblems singletonObject = null;
    public  ListView<String>    problemList;
    public  Button              veryEasy;
    public  Button              easy;
    public  Button              veryHard;
    public  Button              normal;
    public  Button              hard;
    public  Label               errorLabel;
    public  ListView<String>    selectedProblems;

    private CtrlPresentacio     controladorPresentacio;
    private List<String>        selectedProblemsList;

    private Map<Integer, String>    problemes = null;
    private String                  difficulty = null;

    public Button       cancelButton;
    public Button       okButton;
    public TextField    problemIDsTextField;

    private static CtrlSelectMOREProblems getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlSelectMOREProblems() {};
        }
        return singletonObject;
    }

    public CtrlSelectMOREProblems()
    {
        controladorPresentacio = CtrlPresentacio.getInstance();
    }

    public void cancelClicked(ActionEvent actionEvent) throws IOException
    {
        controladorPresentacio.changeStage("main/presentation/SelectProblem.fxml", (Stage)cancelButton.getScene().getWindow());
    }

    public void okClicked(ActionEvent actionEvent) throws IOException
    {
        if (problemes == null)
        {
            errorLabel.setText("Please select a problem");
        }
        else
        {
            if (!controladorPresentacio.setProblems(problemes))
            {
                errorLabel.setText("One or more problems you selected doesn't exist yet");
            }
            else
            {   //controladorPresentacio.getP
                controladorPresentacio.changeStage("main/presentation/ChooseMoreMatches.fxml", (Stage) okButton.getScene().getWindow());
            }
        }
    }
    public void getVeryEasyProblems(ActionEvent actionEvent) throws IOException
    {
        errorLabel.setText(" ");
        if (problemes != null && this.difficulty != null)
        {
            if (!controladorPresentacio.setProblems(problemes))
            {
                errorLabel.setText("One or more problems you selected doesn't exist yet");
            }
        }
        problemes = null;
        Queue<String> q = controladorPresentacio.getVeryEasyProblems();
        showList(q);
        this.difficulty = "VeryEasy";
    }
    public void getEasyProblems(ActionEvent actionEvent)
    {
        errorLabel.setText(" ");
        if (problemes != null && this.difficulty != null)
        {
            if (!controladorPresentacio.setProblems(problemes))
            {
                errorLabel.setText("One or more problems you selected doesn't exist yet");
            }
        }
        problemes = null;
        Queue<String> q = controladorPresentacio.getEasyProblems();
        showList(q);
        this.difficulty = "Easy";
    }
    public void getNormalProblems(ActionEvent actionEvent)
    {
        errorLabel.setText(" ");
        if (problemes != null && this.difficulty != null){
            if (!controladorPresentacio.setProblems(problemes))
            {
                errorLabel.setText("One or more problems you selected doesn't exist yet");
            }
        }
        problemes = null;
        Queue<String> q = controladorPresentacio.getNormalProblems();
        showList(q);
        this.difficulty = "Normal";
    }
    public void getHardProblems(ActionEvent actionEvent)
    {
        errorLabel.setText(" ");
        if (problemes != null && this.difficulty != null)
        {
            if (!controladorPresentacio.setProblems(problemes))
            {
                errorLabel.setText("One or more problems you selected doesn't exist yet");
            }
        }
        problemes = null;
        Queue<String> q = controladorPresentacio.getHardProblems();
        showList(q);
        this.difficulty = "Hard";
    }
    public void getVeryHardProblem(ActionEvent actionEvent)
    {
        errorLabel.setText(" ");
        if (problemes != null && this.difficulty != null)
        {
            if (!controladorPresentacio.setProblems(problemes))
            {
                errorLabel.setText("One or more problems you selected doesn't exist yet");
            }
        }
        problemes = null;
        Queue<String> q = controladorPresentacio.getVeryHardProblems();
        showList(q);
        this.difficulty = "VeryHard";
    }

    /**
     * Private Functions
     */
    private void showList(Queue<String> q)
    {
        List<String> l = new ArrayList<>();
        if (q == null)
        {
            l.add("There are no problems with that difficulty ");
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
        //No se guardan cuando hay dos ids iguales
        String a = problemList.getSelectionModel().getSelectedItem();
        if (!a.equals("There are no problems with that difficulty "))
        {
            if (problemes == null) problemes = new HashMap<>();
            if (selectedProblemsList == null) selectedProblemsList = new ArrayList<>();
            if (!problemes.containsKey(getID(a)))
            {
                selectedProblemsList.add(a);
            }
            problemes.put(getID(a), this.difficulty);
            ObservableList<String> observableList = FXCollections.observableList(selectedProblemsList);
            selectedProblems.setItems(observableList);
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
        return Integer.valueOf(ID);
    }
}
