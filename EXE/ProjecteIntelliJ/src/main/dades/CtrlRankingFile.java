package main.dades;

import java.io.*;
import java.util.*;

public class CtrlRankingFile {

    private static CtrlRankingFile singletonObject;

    public static CtrlRankingFile getInstance(){
        if (singletonObject == null){
            singletonObject = new CtrlRankingFile() {
            };
        }
        return singletonObject;
    }

    private CtrlRankingFile(){
    }

    public Queue<String> showProblemRanking(int problemID, String difficulty){
        /**
         * 1-problemID
         * 2-problemName
         * 3-position
         * 4-playerName
         * 5-numPlays
         * 6-matchTime
         * null if not found;
         */
        Queue<String> q = new LinkedList<>();
        File rankingData;
        FileReader fr = null;
        BufferedReader br;
        try{
            rankingData = new File("ProjecteIntelliJ/Data/rankings" +  problemID + " " + difficulty + ".txt");
            boolean b = rankingData.createNewFile();
            fr = new FileReader(rankingData);
            br = new BufferedReader(fr);

            String line;
            while((line = br.readLine()) != null){
                q.add(line);
            }
            if (q.isEmpty()) q = null;

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return q;
    }


    public void insertRankingLine(int problemID, String problemName, int positon, String playerName, int numPlays, long matchTime){
        String rankingLine = Integer.toString(problemID) + " " + problemName + " " + Integer.toString(positon) + " " + playerName + " " + Integer.toString(numPlays) + " " + Long.toString(matchTime);
        FileWriter ficheroW = null;
        FileReader ficheroR = null;
        PrintWriter pw;
        BufferedReader br;
        try {
            File f = new File("ProjecteIntelliJ/Data/rankings" + problemID  + ".txt");
            boolean b = f.createNewFile();
            ficheroR = new FileReader(f);
            br = new BufferedReader(ficheroR);
            Queue<String> q = new LinkedList<>();
            String line;
            while ((line = br.readLine()) != null) {
                q.add(line);
            }
            ficheroR.close();
            ficheroW = new FileWriter(f);
            pw = new PrintWriter(ficheroW);
            while (!q.isEmpty()){
                pw.println(q.peek());
                pw.flush();
                q.remove();
            }
            pw.println(rankingLine);
            pw.flush();
            ficheroW.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != ficheroW) {
                    ficheroW.close();
                    ficheroR.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public void deleteRankingLinesOfProblem(int problemID, String difficulty) {
        try{
            File f = new File("ProjecteIntelliJ/Data/rankings" + problemID + " " + difficulty + ".txt");
            boolean b = f.createNewFile();
            b = f.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updateRanking(Vector<String> rankingLineVector, int problemID, String difficulty) {
        /**
         * 1-problemID
         * 2-problemName
         * 3-position
         * 4-playerName
         * 5-numPlays
         * 6-matchTime
         * null if not found;
         */

        File f = new File("ProjecteIntelliJ/Data/rankings" + problemID + " " + difficulty + ".txt");
        boolean b = f.delete();
        FileWriter fw = null;
        PrintWriter pw;
        try {
            b = f.createNewFile();
            fw = new FileWriter(f);
            pw = new PrintWriter(fw);
            for (int i = 0; i < rankingLineVector.size(); ++i){
                pw.println(rankingLineVector.elementAt(i));
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (null != fw) {
                    fw.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
