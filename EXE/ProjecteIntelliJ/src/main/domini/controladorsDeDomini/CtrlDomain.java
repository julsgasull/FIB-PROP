package main.domini.controladorsDeDomini;


import java.util.*;
import java.io.*;
import main.dades.CtrlDades;
import main.domini.model.*;
import main.presentation.chessBoardGUIJavafx;


public class CtrlDomain {

    /**
     * Attributes
     */
    private CtrlDades controladorDades;
    private Map<String, Human> jugadorsActuals;
    private Map<Integer, Problem> problemesActuals;
    private Human actualUser;

    public Problem getActualProblem() {
        return actualProblem;
    }

    private Problem actualProblem;

    private static CtrlDomain singletonObject;
    private Match actualmatch;
    private Map<String, Problem> problemes;
    private Vector<String> matchTypes;
    private Vector<String> moreMatchesWins;


    /**
     * Constructor
     */

    public CtrlDomain() {
        initializeCtrlDomain();
    }

    private void initializeCtrlDomain(){
        jugadorsActuals = new HashMap<>();
        problemesActuals = new HashMap<>();
        problemes = new HashMap<String, Problem>();
        controladorDades = CtrlDades.getInstance();
        matchTypes = new Vector<>();
    }

    public static CtrlDomain getInstance() {
        if (singletonObject == null)
            singletonObject = new CtrlDomain() {
            };
        return singletonObject;
    }

    /**
     * Public Functions
     */

    /**
     * Player Functions
     */
    public boolean signUp(String username, String password){
        System.out.println("fem el if");
        if (controladorDades.signUp(username, password)){
            System.out.println("Estic dins del if");
            int ID = controladorDades.getLastPlayerID();
            actualUser = new Human(ID, username, password);
            jugadorsActuals.put(username, actualUser);
            return true;
        }
        System.out.println("no faig el if");
        return false;
    }

    public boolean logIn(String username, String password){
        //mirar que existe, y guardarlo en acutalUser
        if (controladorDades.logIn(username, password)){
            if (jugadorsActuals.containsKey(username)){
                actualUser = jugadorsActuals.get(username);
            }
            else {
                int ID = controladorDades.getLastPlayerID();
                actualUser = new Human(ID, username, password);
                jugadorsActuals.put(username, actualUser);
            }
            return true;
        }
        else return false;
    }

    public int getLastPlayerID(){
        return controladorDades.getLastPlayerID();
    }

    /**
     * Ranking Functions
     */

    public Queue<String> showProblemRanking(int problemID, String dif){
        return controladorDades.showProblemRanking(problemID, dif);
    }

    public void updateRanking(Vector<String> rankingLineVector, int problemID, String dif) {
        controladorDades.updateRanking(rankingLineVector, problemID, dif);
    }

    /**
     * Problem Functions
     */

    /*
    public Queue<String> showProblems(){
        return controladorDades.showProblems();
    }
    */

    public int getLastProblemID(){
        return controladorDades.getLastProblemID();
    }

    public boolean insertProblemLine(int problemID, String problemName, int nPlays, String fen, String difficulty) {
        return controladorDades.insertProblemLine(problemID, problemName, nPlays, fen, difficulty);

    }

    public boolean deleteProblem(int problemID, String dif) { return controladorDades.deleteProblem(problemID, dif); }

    public boolean existsProblemInDB(String name, int nPlays, String fen, String dif){
        return controladorDades.existsProblemInDB(name, nPlays, fen, dif);
    }

    /**
     * Domain Query Functions
     */

    public boolean existsHuman(String username){
        return jugadorsActuals.containsKey(username);
    }

    public boolean existsProblem(int problemID){
        return problemesActuals.containsKey(problemID);
    }

    public Human getHuman(String username){
        return jugadorsActuals.get(username);
    }

    public Problem getProblem(int id){
        return problemesActuals.get(id);
    }

    public void sendHuman(Human newHuman){
        jugadorsActuals.put(newHuman.getUsername(), newHuman);
    }

    public void sendProblem(Problem newProblem){
        problemesActuals.put(newProblem.getProblemID(),newProblem);
    }

    public void logOut() {
        jugadorsActuals.remove(actualUser.getUsername());
        actualUser = null;
    }

    /**
     * Match Functions
     */

    public void crearMatch(String matchType) {
        matchTypes = new Vector<>();
        matchTypes.addElement(matchType);
        actualmatch = new Match(actualProblem, 0,matchType);

    }

    public void playMatch(){
        boolean res = actualmatch.playH1H2(actualUser,actualmatch.getProblem().whoStarts());
        System.out.println(res);
    }

    public Match getMatch () {
        return actualmatch;
    }

    /**
     * Problem Functions
     */

    public Queue<String> getEasyProblems() {
        return controladorDades.showProblems("Easy");
    }

    public Queue<String> getNormalProblems() {
        return controladorDades.showProblems("Normal");
    }

    public Queue<String> getHardProblems() {
        return controladorDades.showProblems("Hard");
    }

    public Queue<String> getVeryHardProblems() {
        return controladorDades.showProblems("VeryHard");
    }

    public Queue<String> getVeryEasyProblems() {
        return controladorDades.showProblems("VeryEasy");
    }

    public boolean setProblems(Map<Integer, String> problemes) {
        for (Map.Entry<Integer, String> entry : problemes.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            String problemInfo = controladorDades.getProblem(key, value);
            if (!controladorDades.existsProblemInDB(getName(problemInfo), getnPlays(problemInfo), getFen(problemInfo), value)) {
                return false;
            }
            Problem aux = new Problem(key, getName(problemInfo), getnPlays(problemInfo), getFen(problemInfo));
            String mapKey = aux.getProblemID() + aux.getDificculty();
            this.problemes.put(mapKey, aux);
        }
        return true;
    }

    public boolean createProblem(String probName, int nPlays, String fen) {
        int ID = controladorDades.getLastProblemID();
        Problem aux = new Problem(ID, probName, nPlays, fen);
        if (aux.validateProblem()){ //validamos el fen
            if (controladorDades.insertProblemLine(ID, probName, nPlays, fen, aux.getDificculty())){
                //lo insertamos en la BD si no existe
                return true;
            }
            else return false;
        }
        else{
            return false;
        }
    }

    public void setActualProblem(int problemID, String difficulty) {
        String dadesProblema = controladorDades.getProblem(problemID, difficulty);
        System.out.println(dadesProblema);
        actualProblem = new Problem(problemID, getName(dadesProblema), getnPlays(dadesProblema), getFen(dadesProblema));
    }

    public Human getActualPlayer() { return actualUser;
    }

    public boolean modifyActualProblem(String probName, int nPlays, String fen) {
        String auxprobName = actualProblem.getName();
        int auxnPlays = actualProblem.getnPlays();
        String auxFen = actualProblem.getFen();
        int ID = actualProblem.getProblemID();
        String difficulty = actualProblem.getDificculty();

        if (actualProblem.update(probName, nPlays, fen)) {
            if (controladorDades.deleteProblem(ID, difficulty)){
                if (controladorDades.insertProblemLine(actualProblem.getProblemID(), probName, nPlays, fen, actualProblem.getDificculty())){
                    return true;
                }
                else return false;
            }
            else return false;
        }
        else return false;
    }

    public void playAndShowClicked() {
        Match nextMatch;
        boolean whoWins;
        int matchNumber = 1;
        moreMatchesWins = new Vector<>();
        for (Map.Entry<String, Problem> entry : problemes.entrySet()) {
            System.out.println(entry.getKey());
            nextMatch = new Match(entry.getValue(), entry.getValue().getProblemID(), matchTypes.elementAt(0));
            if (matchTypes.elementAt(0).equals("m1m1"))
            {
                whoWins = nextMatch.playProblemM1M1(nextMatch.getProblem().whoStarts());
                if (whoWins)
                {
                    String winLine = "Match " + entry.getKey() + ": winner m1 (attacker)";
                    moreMatchesWins.addElement(winLine);
                }
                else{
                    String winLine = "Match " + entry.getKey() + ": winner m1 (defender)";
                    moreMatchesWins.addElement(winLine);
                }
            }
            else if (matchTypes.elementAt(0).equals("m1m2")){
                whoWins = nextMatch.playProblemM1M2(nextMatch.getProblem().whoStarts());
                if (whoWins){
                    String winLine = "Match " + entry.getKey() + ": winner m1 (attacker)";
                    moreMatchesWins.addElement(winLine);
                }
                else{
                    String winLine = "Match " + entry.getKey() + ": winner m2 (defender)";
                    moreMatchesWins.addElement(winLine);
                }
            }
            else if (matchTypes.elementAt(0).equals("m2m1")){
                whoWins = nextMatch.playProblemM2M1(nextMatch.getProblem().whoStarts());
                if (whoWins){
                    String winLine = "Match " + entry.getKey() + ": winner m2 (attacker)";
                    moreMatchesWins.addElement(winLine);
                }
                else{
                    String winLine = "Match " + entry.getKey() + ": winner m1 (defender)";
                    moreMatchesWins.addElement(winLine);
                }
            }
            else {
                whoWins = nextMatch.playProblemM2M2(nextMatch.getProblem().whoStarts());
                if (whoWins){
                    String winLine = "Match " + entry.getKey() + ": winner m2 (attacker)";
                    moreMatchesWins.addElement(winLine);
                }
                else{
                    String winLine = "Match " + entry.getKey() + ": winner m2 (defender)";
                    moreMatchesWins.addElement(winLine);
                }
            }
            matchNumber++;
        }
    }

    public Vector<String> getResults() {
        return moreMatchesWins;
    }



    /**
     * Private Functions
     */

    private String getName(String problemInfo){
        String result = ""; int spaceCounter = 0;
        for (int i = 0; spaceCounter < 2 && i < problemInfo.length(); i++)
        {
            char c = problemInfo.charAt(i);
            if      (c == ' ')          spaceCounter++;
            else if (spaceCounter == 1) result += c;
        }
        return result;
    }

    private String getFen(String problemInfo) {
        String result = "";
        int spaceCounter = 0;
        for (int i = 0; spaceCounter < 3 && i < problemInfo.length(); i++)
        {
            char c = problemInfo.charAt(i);
            if (c == ' ') {
                spaceCounter++;
                if (spaceCounter == 3) {
                    result = problemInfo.substring(i + 1, problemInfo.length() - 1 - 3);
                }
            }
        }
        return result;
    }

    private int getnPlays(String problemInfo) {
        String result = ""; int spaceCounter = 0;
        for (int i = 0; spaceCounter < 3 && i < problemInfo.length(); i++)
        {
            char c = problemInfo.charAt(i);
            if      (c == ' ')          spaceCounter++;
            else if (spaceCounter == 2) result += c;
        }
        return Integer.parseInt(result);
    }

    public void setMatchType(String matchType) {
        matchTypes = new Vector<>();
        matchTypes.addElement(matchType);
    }

    public String getInfoMatch() {
        String info;
        System.out.println(actualmatch.getMatchType());
        switch (actualmatch.getMatchType()){
            case "m1m1":
                if (actualmatch.playProblemM1M1(actualmatch.getProblem().whoStarts())){
                    info = "m1 (attacker) has won!";
                }
                else info = "m1 (defender) has won!";
                break;
            case "m1m2":
                if (actualmatch.playProblemM1M2(actualmatch.getProblem().whoStarts())){
                    info = "m1 (attacker) has won!";
                }
                else info = "m2 (defender) has won!";
                break;
            case "m2m1":
                if (actualmatch.playProblemM2M1(actualmatch.getProblem().whoStarts())){
                    info = "m2 (attacker) has won!";
                }
                else info = "m1 (defender) has won!";
                break;
            case "m2m2":
                if (actualmatch.playProblemM2M2(actualmatch.getProblem().whoStarts())){
                    info = "m2 (attacker) has won!";
                }
                else info = "m2 (defender) has won!";
                break;
            default:
                info = "easterEgg";
        }
        return info;
    }

    public void deleteActualMatch() {
        actualmatch = null;
        matchTypes = new Vector<>();


    }

    public void deleteMoreMatches() {
        actualmatch = null;
        matchTypes = new Vector<>();
        problemes = new HashMap<>();
    }
}
