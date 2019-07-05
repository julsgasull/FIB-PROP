package main.dades;

//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.io.*;


/**
 * Implementación de la classe gestionadora de datos para la clase "Asignatura".
 **/
public class CtrlPlayerFile {

    /**
     * Implementación del patrón de diseño "Singleton", con el objetivo de que
     * haya una única instancia de esta clase en el sistema. En este caso, esta
     * propiedad es deseada ya que esta classe no tiene estado (astributos no
     * estáticos). Para lograrlo, declaramos la constructora como privada y
     * añadimos una operación estática que retorne siempre la misma instancia.
     * Para acceder a esta instancia lo haremos mediante la llamada
     * CtrlAsignaturaFicher.getInstance();
     **/

    private static CtrlPlayerFile singletonObject;

    public static CtrlPlayerFile getInstance() {
        if (singletonObject == null)
            singletonObject = new CtrlPlayerFile() {
            };
        return singletonObject;
    }

    /**
     * Constructora privada.
     **/

    private CtrlPlayerFile() {
    }

    public boolean signUp(String username, String password) {
        boolean signupCompleted = false;
        if (!logIn(username, password)) { //comprobar que no exista el usuario.
            FileWriter ficheroW = null;
            FileReader ficheroR = null;
            PrintWriter pw;
            BufferedReader br;
            try {
                ficheroR = new FileReader("ProjecteIntelliJ/Data/players.txt");
                br = new BufferedReader(ficheroR);
                Queue<String> q = new LinkedList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    q.add(line);
                }
                ficheroR.close();
                ficheroW = new FileWriter("ProjecteIntelliJ/Data/players.txt");
                pw = new PrintWriter(ficheroW);
                while (!q.isEmpty()){
                    pw.println(q.peek());
                    pw.flush();
                    q.remove();
                }
                pw.println(username + " " + password);
                pw.flush();
                signupCompleted = true;
                ficheroW.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != ficheroW && null != ficheroR) {
                        ficheroW.close();
                        ficheroR.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            return false;
        }
        return signupCompleted;

    }

    public boolean logIn(String username, String password) {
        boolean loginCompleted = false;
        File playerData = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            //playerData = new File("../../../Data/players.txt");//poner ruta del archivo);
            fr = new FileReader("ProjecteIntelliJ/Data/players.txt");
            br = new BufferedReader(fr);

            String line;
            String user = "";
            String pass = "";
            boolean tocaPass = false;
            while ((line = br.readLine()) != null & !loginCompleted)
            {
                for (int Iterator = 0; Iterator < line.length(); Iterator++)
                {
                    char c = line.charAt(Iterator);
                    if (c != ' ' && !tocaPass) {
                        user = user + c;
                    } else if (c == ' ') tocaPass = true;
                    else { //if (c != ' ' && tocaPass)
                        pass = pass + c;

                    }
                }
                if (pass.equals(password) && user.equals(username)) {
                    loginCompleted = true;
                }
                pass = "";
                user = "";
                tocaPass = false;

            }
            System.out.println(pass + " " + user);

            /*
            else if (pass != password) //excepcion wrong pass;
            else //excepcion wrong username;
            */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return loginCompleted;
    }

    public int getLastPlayerID(){
        int problemID = 2;
        File playerData = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            playerData = new File("ProjecteIntelliJ/Data/players.txt");
            fr = new FileReader(playerData);
            br = new BufferedReader(fr);

            while ((br.readLine()) != null) {
                problemID++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return ++problemID;
    }
}