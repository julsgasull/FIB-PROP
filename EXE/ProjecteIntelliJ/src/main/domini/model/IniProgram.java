//package main.domini.model;
//
//import java.util.*;
//import main.domini.controladorsDeDomini.CtrlDomain;
//import testing.drivers.*;
//import org.junit.runner.JUnitCore;
//import org.junit.runner.Result;
//import org.junit.runner.notification.Failure;
//import testing.drivers.jUnit.main.domini.MatchTest;
//
//public class IniProgram {
//
//    private CtrlDomain  ctrlDomain;
//    private int         playerID;
//    private int         problemID;
//    private Human       actualHuman;
//    private Problem     actualProblem;
//
//    public IniProgram() {
//        ctrlDomain      = new CtrlDomain();
//        playerID        = ctrlDomain.getLastPlayerID();
//        problemID       = ctrlDomain.getLastProblemID();
//        actualHuman     = null;
//        actualProblem   = null;
//    }
//
//    /**
//     * INITIAL MENU
//     * - Sign Up
//     * - Log In     -> main
//     * - Exit
//     */
//    public void showInitialMenu()
//    {
//        // Initial menu options
//        System.out.println(" ");
//        System.out.println("WELCOME TO CHEESSY!");
//        System.out.println(" ");
//
//        // Go to LogIn, SignUp menu
//        showLogInSignUpOptions();
//    }
//    private void showLogInSignUpOptions()
//    {
//        // LogIn, SignUp menu options
//        System.out.println(" ");
//        System.out.println("Welcome to initial menu!");
//        System.out.println("What do you want to do?");
//        System.out.println("    1. Sign up");
//        System.out.println("    2. Log in");
//        System.out.println("    3. Testing");
//        System.out.println("    4. Exit");
//        System.out.println(" ");
//
//        // Scan stdin
//        Scanner sc = new Scanner(System.in);
//        int option = sc.nextInt();
//
//        // Option chooser
//        if      (option == 1)   signUp();
//        else if (option == 2)   logIn();
//        else if (option == 3)   testing();
//        else if (option == 4)   return;
//        else {
//            System.out.println(" ");
//            System.out.println("You have to write 1..2");
//            showLogInSignUpOptions();   // go back
//        }
//    }
//    private void signUp()
//    {
//        // Request username and scan stdin
//        System.out.println(" ");
//        System.out.println("Enter your NEW Username and press Enter:");
//        Scanner su = new Scanner(System.in);
//        String username = su.nextLine();
//
//        // Request password and scan stdin
//        System.out.println(" ");
//        System.out.println("Enter your NEW Password and press Enter:");
//        Scanner sp = new Scanner(System.in);
//        String password = sp.nextLine();
//
//        // Try to sign up user = create bd line
//        if (ctrlDomain.signUp(username, password))
//        {
//            System.out.println(" ");
//            System.out.println("Welcome " + username + "!");
//            System.out.println(" ");
//            System.out.println("Now, you can login :)");
//        } else {
//            /**
//             * @comment exc?
//             */
//            System.out.println(" ");
//            System.out.println("Ups! Something went wrong :$. Please, try again.");
//        }
//        showLogInSignUpOptions(); // go back anyway
//    }
//    private void logIn()
//    {
//        // Request username and scan stdin
//        System.out.println(" ");
//        System.out.println("Enter your Username and press Enter:");
//        Scanner su = new Scanner(System.in);
//        String username = su.nextLine();
//
//        // Request password and scan stdin
//        System.out.println(" ");
//        System.out.println("Enter your Password and press Enter:");
//        Scanner sp = new Scanner(System.in);
//        String password = sp.nextLine();
//
//        // Try to log in user = create a human instance
//        if (ctrlDomain.logIn(username, password))
//        {
//            if (ctrlDomain.existsHuman(username))
//            {
//                actualHuman = ctrlDomain.getHuman(username);
//            } else {
//                actualHuman = new Human(playerID, username, password);
//                ctrlDomain.sendHuman(actualHuman);
//                playerID++;
//            }
//            showMainMenu();         // go next
//        } else {
//            System.out.println(" ");
//            System.out.println("Ups! Something went wrong :$. Please, try again.");
//            System.out.println(" ");
//            logOut(); // equals to:  actualHuman = null; showInitialMenu();
//        }
//    }
//
//
//    /**
//     * MAIN MENU
//     * - Log Out
//     * - Select problem     -> problem
//     * - Create problem
//     */
//    private void showMainMenu()
//    {
//        // Main menu options
//        System.out.println(" ");
//        System.out.println("Welcome to main menu!");
//        System.out.println("What do you want to do?");
//        System.out.println("    1. Log out");
//        System.out.println("    2. Select problem");
//        System.out.println("    3. Create problem");
//        System.out.println(" ");
//
//        // Scan stdin
//        Scanner sc = new Scanner(System.in);
//        int option = sc.nextInt();
//
//        // Option chooser
//        if      (option == 1)   logOut();
//        else if (option == 2)   selectProblem();
//        else if (option == 3)   createProblem();
//        else {
//            System.out.println(" ");
//            System.out.println("You have to write 1..3");
//            showMainMenu();     // go back
//        }
//    }
//    private void logOut()
//    {
//        actualHuman = null;
//        showInitialMenu();          // go back
//    }
//    private void selectProblem()
//    {
//        // Queue of problems --> element format: String "problemID problemName nPlays fen"
//        Queue<String> problemQueue0 = ctrlDomain.showProblems();
//
//        if (problemQueue0 == null)
//        {
//            System.out.println(" ");
//            System.out.println("Sorry, there are no problems in our dataBase right now.");
//            showMainMenu();         // go back
//        } else {
//            // Duplicate queue --> new queue (1)
//            Queue<String> problemQueue1 = ctrlDomain.showProblems();
//
//            // Print and remove initial queue (0)
//            System.out.println(" ");
//            System.out.println("Row format: problemID problemName nPlays fen");
//            System.out.println(" ");
//            while (!problemQueue0.isEmpty()) System.out.println(problemQueue0.remove());
//
//            // Request problem and scan it
//            System.out.println(" ");
//            System.out.println("Now, write the problemID of the problem you want to work with:");
//            Scanner spid = new Scanner(System.in);
//            String pid = spid.nextLine();
//            System.out.println(" ");
//
//            // Search for problem the user requested
//            boolean found = false; String problemInfo = "";
//            while (!found && !problemQueue1.isEmpty())
//            {
//                if (getProblemId(problemQueue1.peek()).equals(pid))
//                {
//                    problemInfo = problemQueue1.peek();
//                    found = true;
//                }
//                problemQueue1.remove();
//            }
//
//            // Show problem info and go to problem menu
//            if (found)
//            { // User requested an existent problem
//                // Get problem information
//                String id       = pid;
//                if (!ctrlDomain.existsProblem(Integer.parseInt(id)))
//                {   // Create instance problem
//                    String name     = getProblemName    (problemInfo);
//                    String nPlays   = getProblemNPlays  (problemInfo);
//                    String fen      = getProblemFen     (problemInfo);
//                    actualProblem = new Problem(Integer.parseInt(id), name, Integer.parseInt(nPlays), fen);
//                    ctrlDomain.sendProblem(actualProblem);
//                } else {
//                    // Get existent problem
//                    actualProblem = ctrlDomain.getProblem(Integer.parseInt(id));
//                }
//                showProblemMenu();  // go next
//            } else {
//                // User requested a non-existent problem
//                System.out.println(" ");
//                System.out.println("Sorry, the problem you requested is not in our dataBase");
//                System.out.println(" ");
//                actualProblem = null;
//                showMainMenu();         // go back
//            }
//        }
//    }
//    private String getProblemId(String problemInfo)
//    {   // phase 0..1
//        String result = ""; int spaceCounter = 0;
//        for (int i = 0; spaceCounter < 1 && i < problemInfo.length(); i++)
//        {
//            char c = problemInfo.charAt(i);
//            if      (c == ' ')          spaceCounter++;
//            else if (spaceCounter == 0) result += c;
//        }
//        return result;
//    }
//    private String getProblemName(String problemInfo)
//    {   // phase 1..2
//        String result = ""; int spaceCounter = 0;
//        for (int i = 0; spaceCounter < 2 && i < problemInfo.length(); i++)
//        {
//            char c = problemInfo.charAt(i);
//            if      (c == ' ')          spaceCounter++;
//            else if (spaceCounter == 1) result += c;
//        }
//        return result;
//    }
//    private String getProblemNPlays(String problemInfo)
//    {   // phase 2..3
//        String result = ""; int spaceCounter = 0;
//        for (int i = 0; spaceCounter < 3 && i < problemInfo.length(); i++)
//        {
//            char c = problemInfo.charAt(i);
//            if      (c == ' ')          spaceCounter++;
//            else if (spaceCounter == 2) result += c;
//        }
//        return result;
//    }
//    private String getProblemFen(String problemInfo)
//    {   // phase 3..final
//        String result = ""; int spaceCounter = 0;
//        for (int i = 0; i < problemInfo.length(); i++)
//        {
//            char c = problemInfo.charAt(i);
//            if (c == ' ') {
//                spaceCounter++;
//                if (spaceCounter == 3) result = problemInfo.substring(i + 1, problemInfo.length() - 1);
//            }
//        }
//        return result;
//    }
//    private void createProblem()
//    {
//        System.out.println(" ");
//        System.out.println("Welcome to Problem builder!");
//        System.out.println(" ");
//
//        // name: Request problemName and scan it
//        System.out.println(" ");
//        System.out.println("Enter your problemName (without spaces) and press Enter");
//        Scanner sn = new Scanner(System.in);
//        String name = sn.nextLine();
//
//        // nplays: Request number of plays and scan it
//        System.out.println(" ");
//        System.out.println("Enter your number of plays and press Enter");
//        Scanner snp = new Scanner(System.in);
//        String nPlays = snp.nextLine();
//
//        // fen: Request fen and scan it
//        System.out.println(" ");
//        System.out.println("Enter the fen notation and press Enter");
//        Scanner sf = new Scanner(System.in);
//        String fen = sf.nextLine();
//
//        actualProblem = null;
//
//        if (!ctrlDomain.existsProblemInDB(name, Integer.parseInt(nPlays), fen))
//        {
//            actualProblem = new Problem(problemID, name, Integer.parseInt(nPlays), fen);
//            if (actualProblem.validateProblem())
//            {
//                if (ctrlDomain.insertProblemLine(problemID, name, Integer.parseInt(nPlays), fen))
//                {
//                    System.out.println(" ");
//                    System.out.println("Problem with id " + problemID + " and name " + name + " successfully created :)");
//                    System.out.println("Now, you can select your new Problem!");
//                    System.out.println(" ");
//                    problemID = ctrlDomain.getLastProblemID() + 1;
//                } else {
//                    System.out.println("Ups! The problem couldn't be inserted in the DB :$. Please, try again.");
//                }
//            } else {
//                System.out.println("Ups! Your problem fen is incorrect or");
//                System.out.println("it can't be resolved in the number of");
//                System.out.println("plays you indicated :$.\nPlease try again.");
//            }
//        } else {
//            System.out.println("Ups! The problem already exists :$. Please, try again.");
//        }
//        showMainMenu();     // go back anyway
//    }
//
//    /**
//     * PROBLEM MENU
//     * - Log Out                -> initial
//     * - Play problem           -> play
//     * - Delete problem         -> main
//     * - Show problem ranking
//     * - Go back                -> main
//     */
//    private void showProblemMenu()
//    {
//        // Problem menu options
//        System.out.println(" ");
//        System.out.println("Welcome to problem menu!");
//        System.out.println(" ");
//        System.out.println("Your selected problem is " + actualProblem.getProblemID());
//        System.out.println(" ");
//        System.out.println("What do you want to do?");
//        System.out.println("    1. Log out");
//        System.out.println("    2. Go back");
//        System.out.println("    3. Play problem");
//        System.out.println("    4. Delete problem");
//        System.out.println("    5. Show problem ranking");
//        System.out.println(" ");
//
//        // Scan stdin
//        Scanner sc = new Scanner(System.in);
//        int option = sc.nextInt();
//
//        // Option chooser
//        if      (option == 1)   logOut();
//        else if (option == 2)   showMainMenu();
//        else if (option == 3)   showPlayMenu();
//        else if (option == 4)   deleteProblem();
//        else if (option == 5)   problemRanking();
//        else {
//            System.out.println(" ");
//            System.out.println("You have to write 1..5");
//            System.out.println(" ");
//            showProblemMenu();  // go back
//        }
//    }
//    private void deleteProblem()
//    {
//        System.out.println("Esborrem problema");
//        int id = actualProblem.getProblemID();
//        System.out.println("ProblemID = " + id);
//        if (ctrlDomain.deleteProblem(id))
//        {
//            System.out.println(" ");
//            System.out.println("Problem " + id + " successfully deleted :)");
//            System.out.println(" ");
//            actualProblem = null;
//        } else {
//            System.out.println("Ups! Something went wrong :$. Please, try again.");
//        }
//        showMainMenu();     // go back anyway
//    }
//    private void problemRanking()
//    {
//        int id = actualProblem.getProblemID();
//
//        // Queue of rankingLines --> element format: String "problemID problemName position playerName numPlays matchTime"
//        Queue<String> rankingQueue = ctrlDomain.showProblemRanking(id);
//
//        if (rankingQueue == null)
//        {
//            System.out.println(" ");
//            System.out.println("Sorry, right now your problem has no ranking.");
//            System.out.println("You can play a match in order to be the first :)");
//            System.out.println(" ");
//        } else {
//            System.out.println(" ");
//            System.out.println("Here you have the ranking of the problem " + id + ".");
//            System.out.println(" ");
//            System.out.println("Ranking format: position playerName numPlays matchTime");
//            System.out.println(" ");
//
//            while (!rankingQueue.isEmpty())
//            {
//                String actualRankingLine = getRankingLine(rankingQueue.peek());
//                rankingQueue.remove();
//                System.out.println(actualRankingLine);
//            }
//        }
//        showProblemMenu();
//    }
//    private String getRankingLine(String rankingLineInfo)
//    {   // phase 2..final
//        String result = ""; int spaceCounter = 0;
//        for (int i = 0; i < rankingLineInfo.length(); i++)
//        {
//            char c = rankingLineInfo.charAt(i);
//            if (c == ' ')
//            {
//                spaceCounter++;
//                if (spaceCounter == 2) result = rankingLineInfo.substring(i + 1);
//            }
//        }
//        return result;
//    }
//
//    /**
//     * PLAY MENU
//     * - Play H1 vs H2
//     * - Play H1 vs M1
//     * - Play M1 vs M1
//     * - Go back                -> problem
//     */
//    private void showPlayMenu()
//    {
//        // Play menu options
//        System.out.println(" ");
//        System.out.println("Hey " + actualHuman.getUsername() + "!");
//        System.out.println("Welcome to your new match of the problem " + actualProblem.getProblemID() + ".");
//        System.out.println(" ");
//        System.out.println("First of all, what type of match do you want?");
//        System.out.println("    1. Human1 (you)             VS. Human2 (your non-logged friend)");
//        System.out.println("    2. Human1 (you)             VS. IA1 (minmax algorithm)");
//        System.out.println("    3. IA1 (minmax algorithm)   VS. IA1 (minmax algorithm)");
//        System.out.println("    4. Go back");
//
//        // Scan stdin
//        Scanner sc = new Scanner(System.in);
//        int option = sc.nextInt();
//
//        // Option chooser
//        if      (option == 1)   playHumanVsHuman();
//        else if (option == 2)   playHumanVsM1();
//        else if (option == 3)   playM1VsM1();
//        else if (option == 4)   showProblemMenu();
//        else {
//            System.out.println(" ");
//            System.out.println("You have to write 1..3");
//            System.out.println(" ");
//            showPlayMenu();      // go back
//        }
//    }
//    private void playHumanVsHuman()
//    {
//        //H1 = attacker <- actualHuman
//        boolean startsWhite = actualProblem.whoStarts();
//
//        if (startsWhite)    System.out.println(actualHuman.getUsername() + " you are the WHITE color");
//        else                System.out.println(actualHuman.getUsername() + " you are the BLACK color");
//        Match actualMatch = new Match(actualProblem, 0);
//        boolean won = actualMatch.playProblemH1H2(actualHuman, startsWhite);
//
//        if (won)
//        {
//            System.out.println("CONGRATULATIONS" + actualHuman.getUsername() + "! YOU WON!");
//            // problemID problemName position playerName numPlays matchTime
//            actualProblem.addNewRankingLine(actualHuman.getUsername(), actualMatch.getAttackerNPlays(), actualMatch.getTimer().getTime());
//            Vector<String> rankingLineVector = actualProblem.getRankingLines();
//            ctrlDomain.updateRanking(rankingLineVector, actualProblem.getProblemID());
//        } else System.out.println("OH" + actualHuman.getUsername() + "... YOUR FRIEND IS SMARTER THAN YOU D:<");
//        showPlayMenu();
//    }
//    private void playHumanVsM1()
//    {
//        //H1 = attacker <- actualHuman
//        boolean startsWhite = actualProblem.whoStarts();
//
//        if (startsWhite)    System.out.println(actualHuman.getUsername() + " you are the WHITE color");
//        else                System.out.println(actualHuman.getUsername() + " you are the BLACK color");
//        Match actualMatch = new Match(actualProblem, 0);
//        boolean won = actualMatch.playProblemH1M1(actualHuman, startsWhite);
//
//        if (won)
//        {
//            System.out.println("CONGRATULATIONS" + actualHuman.getUsername() + "! YOU WON!");
//            // problemID problemName position playerName numPlays matchTime
//            actualProblem.addNewRankingLine(actualHuman.getUsername(), actualMatch.getAttackerNPlays(), actualMatch.getTimer().getTime());
//            Vector<String> rankingLineVector = actualProblem.getRankingLines();
//            ctrlDomain.updateRanking(rankingLineVector, actualProblem.getProblemID());
//        } else System.out.println("OH" + actualHuman.getUsername() + "... IA WON. ROBOTS WILL RULE THE WORLD :D");
//        showPlayMenu();
//    }
//    private void playM1VsM1()
//    {
//        boolean startsWhite = actualProblem.whoStarts();
//        Match actualMatch = new Match(actualProblem, 0);
//        actualMatch.playProblemM1M1(startsWhite);
//        showPlayMenu();
//    }
//
//    private void testing()
//    {
//        System.out.println("Starting the testing...");
//        System.out.println("The test is follows a bottom-up structure, so, in order to do the testing properly");
//        System.out.println("the testing of the classes must follow a specific order.\n");
//        System.out.println("Choose which of the first level classes you want to test:");
//        System.out.println("    1. test Score");
//        System.out.println("    2. test Chrono");
//        System.out.println("    3. test Player");
//        System.out.println("    4. test Move");
//        System.out.println("    5. go to next level");
//        boolean chono = false,  player = false, score = false, move = false;
//        Scanner sc = new Scanner(System.in);
//        int option = sc.nextInt();
//        while ((! chono || ! player || ! score || !move) && option != 5){
//            switch (option){
//                case 1:
//                    DriverScore ds = new DriverScore();
//                    ds.main();
//                    score = true;
//                    break;
//                case 2:
//                    DriverChrono dc = new DriverChrono();
//                    dc.main();
//                    chono = true;
//                    break;
//                case 3:
//                    DriverPlayer dp = new DriverPlayer();
//                    dp.main();
//                    player = true;
//                    break;
//                case 4:
//                    DriverMove dm = new DriverMove();
//                    dm.main();
//                    move = true;
//                    break;
//                case 5:
//                    System.out.println("you must do all the test before go to the next level");
//                    break;
//                default:
//                    System.out.println("Introduce a number between 1...5");
//                    break;
//            }
//            System.out.println("Select a new option:");
//            System.out.println("    1. test Score");
//            System.out.println("    2. test Chrono");
//            System.out.println("    3. test Player");
//            System.out.println("    4. test Move");
//            System.out.println("    5. go to next level");
//            option = sc.nextInt();
//        }
//
//        System.out.println("Choose which of the second level classes you want to test:");
//        System.out.println("    1. test Ranking Line");
//        System.out.println("    2. test Human");
//        System.out.println("    3. test IA Player");
//        System.out.println("    4. go to next level");
//        boolean rankingLine = false,  human = false, IAPlayer = false;
//        option = sc.nextInt();
//        while ((! rankingLine || ! human || ! IAPlayer) && option != 4){
//            switch (option){
//                case 1:
//                    DriverRankingLine drk = new DriverRankingLine();
//                    drk.main();
//                    rankingLine = true;
//                    break;
//                case 2:
//                    DriverHuman dh = new DriverHuman();
//                    dh.main();
//                    human = true;
//                    break;
//                case 3:
//                    DriverIAPlayer diap = new DriverIAPlayer();
//                    diap.main();
//                    IAPlayer = true;
//                    break;
//                case 4:
//                    System.out.println("you must do all the test before go to the next level");
//                    break;
//                default:
//                    System.out.println("Introduce a number between 1...5");
//                    break;
//            }
//            System.out.println("Select a new option:");
//            System.out.println("    1. test Ranking Line");
//            System.out.println("    2. test Human");
//            System.out.println("    3. test IA Player");
//            System.out.println("    4. go to next level");
//            option = sc.nextInt();
//        }
//
//        System.out.println("Choose which of the third level classes you want to test:");
//        System.out.println("    1. test Problem");
//        System.out.println("    2. test Machine One");
//        System.out.println("    3. go to next level");
//        boolean problem = false,  machineone = false;
//        option = sc.nextInt();
//        while ((! problem || ! machineone ) && option != 3){
//            switch (option){
//                case 1:
//                    DriverProblem dp = new DriverProblem();
//                    dp.main();
//                    problem = true;
//                    break;
//                case 2:
//                    DriverMachineOne dmo = new DriverMachineOne();
//                    dmo.main();
//                    machineone = true;
//                    break;
//                case 3:
//                    System.out.println("you must do all the test before go to the next level");
//                    break;
//                default:
//                    System.out.println("Introduce a number between 1...3");
//                    break;
//            }
//            System.out.println("Select a new option:");
//            System.out.println("    1. test Problem");
//            System.out.println("    2. test Machine One");
//            System.out.println("    3. go to next level");
//            option = sc.nextInt();
//        }
//
//        System.out.println("Choose which of the fourth level classes you want to test:");
//        System.out.println("    1. test Match (Junit)");
//        System.out.println("    2. test Match (Junit)(Aggresive execution, it shows test passed, but it ends program");
//        System.out.println("    3. go to next level");
//        boolean match = false, match2 = false;
//        option = sc.nextInt();
//        while ((! match || !match2 ) && option != 3){
//            switch (option){
//                case 1:
//                    Result result = JUnitCore.runClasses(MatchTest.class);
//                    for (Failure failure : result.getFailures()) {
//                        System.out.println(failure.toString());
//                        System.out.println(result.toString());
//                    }
//                    match = true;
//                    break;
//                case 2:
//                    JUnitCore.main("testing.drivers.jUnit.main.domini.MatchTest");
//                    match2 = true;
//                    break;
//                case 3:
//                    System.out.println("you must do all the test before go to the next level");
//                    break;
//                default:
//                    System.out.println("Introduce a number between 1...2");
//                    break;
//            }
//            System.out.println("Select a new option:");
//            System.out.println("    2. test Match (Junit)(Aggresive execution, it shows test passed, but it ends program");
//            System.out.println("    3. go to next level");
//            option = sc.nextInt();
//        }
//
//        System.out.println("Choose which of the fifth level classes you want to test:");
//        System.out.println("    1. test Board");
//        System.out.println("    2. go to next level");
//        boolean board = false;
//        option = sc.nextInt();
//        while ((! board ) && option != 2){
//            switch (option){
//                case 1:
//                    DriverBoard db = new DriverBoard();
//                    db.main();
//                    board = true;
//                    break;
//                case 2:
//                    System.out.println("you must do all the test before go to the next level");
//                    break;
//                default:
//                    System.out.println("Introduce a number between 1...2");
//                    break;
//            }
//            System.out.println("Select a new option:");
//            System.out.println("    1. test Board");
//            System.out.println("    2. go to next level");
//            option = sc.nextInt();
//        }
//
//        System.out.println("Choose which of the fifth level classes you want to test:");
//        System.out.println("    1. test Piece");
//        System.out.println("    2. go to next level");
//        boolean piece = false;
//        option = sc.nextInt();
//        while ((! piece ) && option != 2){
//            switch (option){
//                case 1:
//                    DriverPiece dp = new DriverPiece();
//                    dp.main();
//                    piece = true;
//                    break;
//                case 2:
//                    System.out.println("you must do all the test before go back to the menu");
//                    break;
//                default:
//                    System.out.println("Introduce a number between 1...2");
//                    break;
//            }
//            System.out.println("Select a new option:");
//            System.out.println("    1. test Piece");
//            System.out.println("    2. go to next level");
//            option = sc.nextInt();
//        }
//
//        System.out.println("Choose which of the fifth level classes you want to test:");
//        System.out.println("    1. test CtrlDomain");
//        System.out.println("    2. go to main menu");
//        boolean ctrld = false;
//        option = sc.nextInt();
//        while ((!ctrld ) && option != 2){
//            switch (option){
//                case 1:
//                    DriverCtrlDomain ctrldm = new DriverCtrlDomain();
//                    ctrldm.main();
//                    ctrld = true;
//                    break;
//                case 2:
//                    System.out.println("you must do all the test before go back to the menu");
//                    break;
//                default:
//                    System.out.println("Introduce a number between 1...2");
//                    break;
//            }
//            System.out.println("Select a new option:");
//            System.out.println("    1. test CtrlDomain");
//            System.out.println("    2. go to main menu");
//            option = sc.nextInt();
//        }
//
//        showInitialMenu();
//    }
//}