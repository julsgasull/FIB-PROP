package main.presentation;

import main.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.domini.controladorsDeDomini.*;
import main.domini.model.Human;
import main.domini.model.Match;
import main.domini.model.Problem;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

public class CtrlPresentacio
{
    /**
     * Attributes
     */
    private CtrlChooseMoreMatches   controladorChooseMoreMatches;
    private CtrlChooseOneMatch      controladorChooseOneMatch;
    private CtrlCreateProblem       controladorCreateProblem;
    private CtrlMainMenu            controladorMainMenu;
    private CtrlProblemMenu         controladorProblemMenu;
    private CtrlSelectProblem       controladorSelectProblem;
    private CtrlWelcomeMenu         controladorWelcomeMenu;
    private CtrlLogIn               controladorLogIn;
    private CtrlSignUp              controladorSignUp;
    private CtrlModifyProblem       controladorModifyProblem;

    private CtrlDomain controladorDomini;

    private static CtrlPresentacio singletonObject = null;

    /**
     * Constructor
     */
    public static CtrlPresentacio getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlPresentacio() {};
        }
        return singletonObject;
    }
    public CtrlPresentacio()
    {
//        controladorChooseMoreMatches    = CtrlChooseMoreMatches.getInstance();
//        controladorChooseOneMatch       = CtrlChooseOneMatch.getInstance();
//        controladorCreateProblem        = CtrlCreateProblem.getInstance();
//        controladorMainMenu             = CtrlMainMenu.getInstance();
//        controladorProblemMenu          = CtrlProblemMenu.getInstance();
//        controladorSelectProblem        = CtrlSelectProblem.getInstance();
//        controladorWelcomeMenu          = CtrlWelcomeMenu.getInstance();
//        controladorLogIn                = CtrlLogIn.getInstance();
//        controladorSignUp               = CtrlSignUp.getInstance();
          controladorDomini               = CtrlDomain.getInstance();
    }

    /**
     * Funcions SignUp
     */
    public boolean doSignUp(String username, String password)
    {
        return controladorDomini.signUp(username, password);
    }
    public boolean doLogIn(String username, String password)
    {
        return controladorDomini.logIn(username, password);
    }

    /**
     * Functions ProblemMenu
     */
    public Queue<String> getProblemRanking(int problemID, String dif)
    {
        return controladorDomini.showProblemRanking(problemID, dif);
    }

    public void deleteProblem(int problemID, String dif)
    {
        controladorDomini.deleteProblem(problemID, dif);
    }

    /**
     * Functions Play
     */
    /*

    */
    public Match getMatch(){
        return controladorDomini.getMatch();
    }

    //public int getNplays(){ return controladorDomini.getNplays();}

    //public void setNplays(int n){


    /**
     * Functions
     */

    public void changeStage(String path, Stage actualStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path));
        Stage next = new Stage();
        next.setTitle("Cheessy");
        next.setResizable(false);
        next.setScene(new Scene(root));
        next.show();
        //if (path.equals("main/presentation/ProblemMenu.fxml")) CtrlProblemMenu.getInstance().startProblemMenu();
        // do what you have to do
        actualStage.close(); //close window
    }

    public void doLogOut(Stage actualWindow) throws IOException
    {
        controladorDomini.logOut();
        changeStage("main/presentation/WelcomeMenu.fxml", actualWindow);
    }
    public void setActualProblem(int problemID, String difficulty)
    {
        controladorDomini.setActualProblem(problemID, difficulty);
    }


    public boolean createProblem(String probName, int nPlays, String fen)
    {
        return controladorDomini.createProblem(probName, nPlays, fen);

    }
    public void crearMatch(String matchType)
    {
        controladorDomini.crearMatch(matchType);
    }

    public void playMatch(){
        controladorDomini.playMatch();
    }

    public void crearMatches(String matchType)
    {
        controladorDomini.crearMatch(matchType);
    }

    public Queue<String> getVeryEasyProblems()
    {
        return controladorDomini.getVeryEasyProblems();
    }

    public Queue<String> getEasyProblems()
    {
        return controladorDomini.getEasyProblems();
    }

    public Queue<String> getNormalProblems()
    {
        return controladorDomini.getNormalProblems();
    }

    public Queue<String> getHardProblems()
    {
        return controladorDomini.getHardProblems();
    }

    public Queue<String> getVeryHardProblems()
    {
        return controladorDomini.getVeryHardProblems();
    }

    public boolean setProblems(Map<Integer, String> problemes) {
        return controladorDomini.setProblems(problemes);
    }

    public boolean modifyActualProblem(String probName, int nPlays, String fen) {
        return controladorDomini.modifyActualProblem(probName, nPlays, fen);
    }

    public Problem getActualProblem() {
        return controladorDomini.getActualProblem();
    }

    public Human getActualPlayer() { return controladorDomini.getActualPlayer();
    }

    public void updateRanking(Vector<String> s, int problemid) {
        controladorDomini.updateRanking(s,problemid,controladorDomini.getMatch().getProblem().getDificculty());
    }

    public void playAndShowClicked() {
        controladorDomini.playAndShowClicked();
    }

    public Vector<String> getResults() {
        return controladorDomini.getResults();
    }

    public void setMatchType(String matchType) {
        controladorDomini.setMatchType(matchType);
    }

    public String getInfoMatch() {
        return controladorDomini.getInfoMatch();
    }

    public void deleteActualMatch() {
        controladorDomini.deleteActualMatch();
    }

    public void deleteMoreMatches() {
        controladorDomini.deleteMoreMatches();
    }
}

