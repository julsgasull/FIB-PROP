package main.dades;

import java.util.Queue;
import java.util.Vector;

/**
* @author Aarón García
*/


public class CtrlDades
{
    /**
     * Attributes
     */
    private CtrlPlayerFile controladorPlayerDades;
    private CtrlRankingFile controladorRankingDades;
    private CtrlProblemFile controladorProblemDades;

    private static CtrlDades singletonObject;

    /**
     * Constructor
     */

    public static CtrlDades getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlDades() {};
        }
        return singletonObject;
    }


    public CtrlDades()
    {
        initializeCtrlDades();
    }

    public void initializeCtrlDades()
    {
        controladorPlayerDades  = CtrlPlayerFile.getInstance();
        controladorProblemDades = CtrlProblemFile.getInstance();
        controladorRankingDades = CtrlRankingFile.getInstance();
    }

    /**
     * Public Functions
     */

        /**
         * Player Functions
         */
    public boolean signUp(String username, String password)
    {
        return controladorPlayerDades.signUp(username, password);
    }

    public boolean logIn(String username, String password)
    {
        return controladorPlayerDades.logIn(username, password);
    }

    public int getLastPlayerID()
    {
        return controladorPlayerDades.getLastPlayerID();
    }

    /**
     * Ranking Functions
     */
    public Queue<String> showProblemRanking(int problemID, String dif)
    {
        return controladorRankingDades.showProblemRanking(problemID, dif);
    }
    public void updateRanking(Vector<String> rankingLineVector, int problemID, String dif)
    {
        controladorRankingDades.updateRanking(rankingLineVector, problemID, dif);
    }

    /**
     * Problem Functions
     */
    public Queue<String> showProblems(String difficulty)
    {
        return controladorProblemDades.showProblemsByDifficulty(difficulty);
    }
    public int getLastProblemID()
    {
        return controladorProblemDades.getLastProblemID();
    }
    public boolean insertProblemLine(int problemID, String problemName, int nPlays, String fen, String difficulty)
    {
        return controladorProblemDades.insertProblemLine(problemID, problemName, nPlays, fen, difficulty);
    }
    public boolean deleteProblem(int problemID, String dif)
    {
        if (controladorProblemDades.deleteProblem(problemID, dif))
        {
            controladorRankingDades.deleteRankingLinesOfProblem(problemID, dif);
            return true;
        }
        return false;
    }
    public boolean existsProblemInDB(String name, int nPlays, String fen, String dif)
    {
        return controladorProblemDades.existsProblemInDB(name, nPlays, fen, dif);
    }
    public String getProblem(int problemID, String difficulty)
    {
        return controladorProblemDades.getProblem(problemID, difficulty);
    }







}
