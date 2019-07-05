package main.dades;

import java.io.*;
import java.util.*;

/**
* @author Aarón García
*/


public class CtrlProblemFile
{

    private static CtrlProblemFile singletonObject;

    public static CtrlProblemFile getInstance()
    {
        if (singletonObject == null)
        {
            singletonObject = new CtrlProblemFile() {};
        }
        return singletonObject;
    }

    /**
     * Constructora
     */

    private CtrlProblemFile()
    {

    }

    /**
     * Public Functions
     */
    public Queue<String> showProblemsByDifficulty(String difficulty)
    {
        Queue<String> q = new LinkedList<>();
        File problemData = null;
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            problemData = new File("ProjecteIntelliJ/Data/problems" + difficulty + ".txt");
            boolean aux = problemData.createNewFile();

            fr = new FileReader(problemData);
            br = new BufferedReader(fr);

            String line;
            boolean noEntra = true;
            while ((line = br.readLine()) != null)
            {
                noEntra = false;
                q.add(line);
            }
            if (noEntra)
            {
                q = null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fr)
                {
                    fr.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return q;
    }

    public int getLastProblemID()
    {
        int problemID = 0;
        File problemIDFile = null;
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            problemIDFile = new File("ProjecteIntelliJ/Data/problemsID.txt");
            boolean aux = problemIDFile.createNewFile();
            fr = new FileReader(problemIDFile);
            br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null)
            {
                problemID = Integer.parseInt(line);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fr)
                {
                    fr.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return ++problemID;
    }

    public boolean insertProblemLine(int problemID, String problemName, int nPlays, String fen, String difficulty)
    {
        String problemLine = Integer.toString(problemID) + " " + problemName + " " + Integer.toString(nPlays) + " " + fen;
        FileWriter fichero = null;
        PrintWriter pw = null;
        /**
         *  primero comrobar que no exista con otra funcion
         */
        boolean creado = false;
        try
        {
            Queue<String> q = showProblemsByDifficulty(difficulty);
            File file = new File("ProjecteIntelliJ/Data/problems" + difficulty + ".txt");
            boolean aux = file.createNewFile();
            if (q != null)
            {
                fichero = new FileWriter(file);
                pw = new PrintWriter(fichero);
                while(!q.isEmpty())
                {
                    System.out.println(q.peek());
                    pw.println(q.peek());
                    pw.flush();
                    q.remove();
                }
                pw.println(problemLine);
                pw.flush();
                creado = true;
            }
            else
            {
                fichero = new FileWriter(file);
                pw = new PrintWriter(fichero);
                pw.println(problemLine);
                pw.flush();
                creado = true;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fichero)
                {
                    fichero.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        incrementProblemID();
        return creado;
    }

    public boolean deleteProblem(int problemID, String difficulty)
    {
        Queue<String> q = showProblemsByDifficulty(difficulty); String line;
        boolean esborrat = false;
        FileWriter fw = null;
        try
        {
            if (q != null)
            {
                // Empty actual db problem.txt
                File file = new File("ProjecteIntelliJ/Data/problems" + difficulty + ".txt");
                boolean aux = file.createNewFile();
                fw = new FileWriter(file);
                PrintWriter pw; pw = new PrintWriter(fw); pw.write(""); pw.flush();

                // Fill without the deleted one
                while ((line = q.peek()) != null)
                {
                    System.out.println(getProblemId(line));
                    if (Integer.parseInt(getProblemId(line)) != problemID)
                    {
                        insertProblemLineInAFile(line, "ProjecteIntelliJ/Data/problems" + difficulty + ".txt", difficulty);
                    }
                    else esborrat = true;
                    q.remove();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fw)
                {
                    fw.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return esborrat;
    }

    public boolean existsProblemInDB(String name, int nPlays, String fen, String difficulty)
    {
        Queue<String> q = showProblemsByDifficulty(difficulty);
        boolean trobat = false;
        String line;
        if (q != null)
        {
            while ((line = q.poll()) != null && !trobat)
            {
                if (getName(line).equals(name) && getnPlays(line) == nPlays && getFen(line).equals(fen))
                {
                    trobat = true;
                }
            }
        }
        return trobat;
    }

    /**
     * Private Functions
     */
    private void incrementProblemID()
    {
        File f = null;
        FileWriter fichero = null;
        PrintWriter pw = null;
        int ID = getLastProblemID();
        try
        {
            f = new File("ProjecteIntelliJ/Data/problemsID.txt");
            boolean aux = f.createNewFile();
            fichero = new FileWriter(f);
            pw = new PrintWriter(fichero);
            pw.println(ID);
            pw.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fichero)
                {
                    fichero.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }

    private String getProblemId(String problemInfo)
    {   // phase 0..1
        String result = ""; int spaceCounter = 0;
        for (int i = 0; spaceCounter < 1 && i < problemInfo.length(); i++)
        {
            char c = problemInfo.charAt(i);
            if      (c == ' ')          spaceCounter++;
            else if (spaceCounter == 0) result += c;
        }
        return result;
    }

    private String getName(String problemInfo)
    {   // phase 1..2
        String result = ""; int spaceCounter = 0;
        for (int i = 0; spaceCounter < 2 && i < problemInfo.length(); i++)
        {
            char c = problemInfo.charAt(i);
            if      (c == ' ')          spaceCounter++;
            else if (spaceCounter == 1) result += c;
        }
        return result;
    }
    private int getnPlays(String problemInfo)
    {   // phase 2..3
        String result = ""; int spaceCounter = 0;
        for (int i = 0; spaceCounter < 3 && i < problemInfo.length(); i++)
        {
            char c = problemInfo.charAt(i);
            if      (c == ' ')          spaceCounter++;
            else if (spaceCounter == 2) result += c;
        }
        return Integer.parseInt(result);
    }
    private String getFen(String problemInfo)
    {   // phase 3..final
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


    private void insertProblemLineInAFile(String Line, String route, String difficulty)
    {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            Queue<String> q = showProblemsByDifficulty(difficulty);
            if (q != null)
            {
                fichero = new FileWriter(route);
                pw = new PrintWriter(fichero);
                for (int i = 0; i < q.size() && !q.isEmpty(); ++i)
                {
                    pw.println(q.peek());
                    pw.flush();
                    q.remove();
                }
                pw.println(Line);
                pw.flush();
            }
            else
            {
                fichero = new FileWriter(route);
                pw = new PrintWriter(fichero);
                pw.println(Line);
                pw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fichero)
                {
                    fichero.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
    }


    public String getProblem(int problemID, String difficulty)
    {
        String problem = null;
        File problemData = null;
        FileReader fr = null;
        BufferedReader br = null;
        try
        {
            problemData = new File("ProjecteIntelliJ/Data/problems" + difficulty + ".txt");
            boolean aux = problemData.createNewFile();
            fr = new FileReader(problemData);
            br = new BufferedReader(fr);

            String line;
            boolean noEntra = true;
            while ((line = br.readLine()) != null)
            {
                if (Integer.toString(problemID).equals(getProblemId(line)))
                {
                    problem = line;
                    noEntra = false;
                }
            }
            if (noEntra)
            {
                problem = null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (null != fr)
                {
                    fr.close();
                }
            }
            catch (Exception e2)
            {
                e2.printStackTrace();
            }
        }
        return problem;
    }
}
