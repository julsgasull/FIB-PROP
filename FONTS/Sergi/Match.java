package main.domini.model;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
* @author Sergi Sendrós
*/

public class Match
{

    /**
     * Attributes
     */
    private int         matchID;
    private Chrono      timer;
    private int         attackerNPlays;
    private Problem     problem;
    private Board       actualBoard;

    private boolean     turn;

    private String         matchType;

    /**
     * Constructor
     */
    public Match(Problem problem, int matchID, String matchType)
    {
        this.matchID        = matchID;
        this.timer          = new Chrono();
        this.attackerNPlays = 0;
        this.problem        = problem;
        this.actualBoard    = this.problem.getMatrix();
        this.matchType      =matchType;
        this.turn           =this.problem.whoStarts();
    }

    /**
     * Getters and setters
     */
    public int getMatchID()
    {
        return matchID;
    }
    public Chrono getTimer()
    {
        return timer;
    }
    public int getAttackerNPlays()
    {
        return attackerNPlays;
    }
    public Problem getProblem()
    {
        return problem;
    }
    public Board getActualBoard()
    {
        return actualBoard;
    }

    public void setAttackerNPlays(int attackerNPlays)
    {
        this.attackerNPlays = attackerNPlays;
    }

    public String getMatchType()
    {
        return matchType;
    }

    public void setMatchType(String matchType)
    {
        this.matchType = matchType;
    }

    public boolean isTurn()
    {
        return turn;
    }

    public void setTurn(boolean turn)
    {
        this.turn = turn;
    }

    /**
     * Public Functions
    */

    public boolean playProblemH1H2(Human h1, boolean startsWhite)
    {
        boolean itsACheckMate = false;          // return
        int n = 0; if (!startsWhite) n = 1;     // white%2==0, black%2!=0

        //loop
        while (n < problem.getnPlays()*2 && !itsACheckMate)
        {
            printBoard();
            if (n%2 == 0)
            {   // white
                System.out.println("White's turn:");
                if (startsWhite)
                {   // attacker(human1) white
                    System.out.println(h1.getUsername() + "'s turn");
                    movePieceHumanAttacker(true); attackerNPlays++;
                    itsACheckMate = actualBoard.isCheckMate(false);
                }
                else
                {
                    // defender(human2) white
                    System.out.println("Friend's turn");
                    movePieceHumanDefender(true);
                }
            }
            else
            {   // (n%2!=0)
                // black
                System.out.println("Black's turn:");
                if(!startsWhite)
                {   // attacker(human1) black
                    System.out.println(h1.getUsername() + "'s turn");
                    movePieceHumanAttacker(false); attackerNPlays++;
                    itsACheckMate = actualBoard.isCheckMate(true);
                }
                else
                {
                    // defender(human2) black
                    System.out.println("Friend's turn");
                    movePieceHumanDefender(true);
                }
            }
            n++;
        }
        return itsACheckMate;
    }

    public boolean playH1H2(Human h1, boolean startsWhite)
    {
        boolean itsACheckMate = false;
        int n=0;

        while (n < problem.getnPlays()*2  || !itsACheckMate)
        {
            System.out.println("entro al while");
            boolean aux = this.turn;
            if (startsWhite)
            {
                while (this.turn == aux);
                System.out.println("seguent jugador");
                itsACheckMate = actualBoard.isCheckMate(false);
            }
            else
            {
                while (this.turn == aux);
                itsACheckMate = actualBoard.isCheckMate(true);
            }
            n++;
        }
        return itsACheckMate;
    }

    public boolean playProblemH1M1(Human h1, boolean startsWhite)
    {
        boolean itsACheckMate = false;          // return
        int n = 0; if (!startsWhite) n = 1;     // white%2==0, black%2!=0

        //loop
        while (n < problem.getnPlays()*2 && !itsACheckMate)
        {
            printBoard();
            if (n%2 == 0)
            {   // white
                System.out.println("White's turn:");
                if (startsWhite)
                {   // attacker(human) white
                    System.out.println(h1.getUsername() + "'s turn");
                    movePieceHumanAttacker(true); attackerNPlays++;
                    itsACheckMate = actualBoard.isCheckMate(false);
                }
                else
                {
                    // defender(IA) white
                    System.out.println("IA's turn");
                    movePieceAI(true);
                }
            }
            else
            {   // (n%2!=0)
                // black
                System.out.println("Black's turn:");
                if(!startsWhite)
                {   // attacker(human) black
                    System.out.println(h1.getUsername() + "'s turn");
                    movePieceHumanAttacker(false); attackerNPlays++;
                    itsACheckMate = actualBoard.isCheckMate(true);
                }
                else
                {
                    // defender(IA) black
                    System.out.println("IA's turn");
                    movePieceAI(false);
                }
            }
            n++;
        }
        return itsACheckMate;
    }


    public boolean playProblemM1M1(boolean startsWhite)
    {
        int n;
        int kings;
        boolean itsACheckMate = false;
        for (n = 0; n < problem.getnPlays()*2; n++)
        {
            printBoard();
            if (n % 2 == 0)
            {
                movePieceAI(startsWhite);
                itsACheckMate = actualBoard.isCheckMate(!startsWhite);
            }
            else
            {
                movePieceAI(!startsWhite);
                itsACheckMate = actualBoard.isCheckMate(startsWhite);
            }

            if (itsACheckMate)
            {
                n=1000000;
                System.out.println("Attacker has won the problem!");
                System.out.println("  ");
                return true;
            }

            kings = 0;
            for (int i = 0 ; i < 8 ; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (actualBoard.hasPiece(i,j))
                    {
                        if (actualBoard.getCell(i, j).getName() == 'K' || actualBoard.getCell(i, j).getName() == 'k')
                        {
                            kings++;
                        }
                    }
                }
            }
            if (kings == 1) n = 1000000000;
        }
        printBoard();
        if (!itsACheckMate)
        {
            System.out.println("Defender has won the problem!");
            return false;
        }
        else return true;
    }

    public boolean playProblemM1M2(boolean startsWhite)
    {
        int n;
        int kings;
        boolean itsACheckMate = false;
        for (n = 0; n < problem.getnPlays()*2; n++)
        {
            printBoard();
            if (n % 2 == 0)
            {
                movePieceAI(startsWhite);
                itsACheckMate = actualBoard.isCheckMate(!startsWhite);
            }
            else
            {
                movePieceAI2(!startsWhite);
                itsACheckMate = actualBoard.isCheckMate(startsWhite);
            }

            if (itsACheckMate)
            {
                n=1000000;
                System.out.println("Attacker has won the problem!");
                System.out.println("  ");
                return true;
            }

            kings = 0;
            for (int i = 0 ; i < 8 ; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (actualBoard.hasPiece(i,j))
                    {
                        if (actualBoard.getCell(i, j).getName() == 'K' || actualBoard.getCell(i, j).getName() == 'k')
                        {
                            kings++;
                        }
                    }
                }
            }
            if (kings == 1) n = 1000000000;
        }
        printBoard();
        if (!itsACheckMate)
        {
            System.out.println("Defender has won the problem!");
            return false;
        }
        else return true;
    }

    public boolean playProblemM2M1(boolean startsWhite)
    {
        int n;
        int kings;
        boolean itsACheckMate = false;
        for (n = 0; n < problem.getnPlays()*2; n++)
        {
            printBoard();
            if (n % 2 == 0)
            {
                movePieceAI2(startsWhite);
                itsACheckMate = actualBoard.isCheckMate(!startsWhite);
            }
            else
            {
                movePieceAI(!startsWhite);
                itsACheckMate = actualBoard.isCheckMate(startsWhite);
            }

            if (itsACheckMate)
            {
                n=1000000;
                System.out.println("Attacker has won the problem!");
                System.out.println("  ");
                return true;
            }
            kings = 0;
            for (int i = 0 ; i < 8 ; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (actualBoard.hasPiece(i,j))
                    {
                        if (actualBoard.getCell(i, j).getName() == 'K' || actualBoard.getCell(i, j).getName() == 'k')
                        {
                            kings++;
                        }
                    }
                }
            }
            if (kings == 1) n = 1000000000;
        }
        printBoard();
        if (!itsACheckMate)
        {
            System.out.println("Defender has won the problem!");
            return false;
        }
        else return true;
    }

    public boolean playProblemM2M2(boolean startsWhite)
    {
        int n;
        int kings;
        boolean itsACheckMate = false;
        for (n = 0; n < problem.getnPlays()*2; n++)
        {
            printBoard();
            if (n % 2 == 0)
            {
                movePieceAI2(startsWhite);
                itsACheckMate = actualBoard.isCheckMate(!startsWhite);
            }
            else
            {
                movePieceAI2(!startsWhite);
                itsACheckMate = actualBoard.isCheckMate(startsWhite);
            }

            if (itsACheckMate)
            {
                n=1000000;
                System.out.println("Attacker has won the problem!");
                System.out.println("  ");
                return true;
            }

            kings = 0;
            for (int i = 0 ; i < 8 ; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (actualBoard.hasPiece(i,j))
                    {
                        if (actualBoard.getCell(i, j).getName() == 'K' || actualBoard.getCell(i, j).getName() == 'k')
                        {
                            kings++;
                        }
                    }
                }
            }
            if (kings == 1) n = 1000000000;
        }
        printBoard();
        if (!itsACheckMate)
        {
            System.out.println("Defender has won the problem!");
            return false;
        }
        else return true;
    }

    public void movePieceAI(boolean colour)
    {
        System.out.println("Moving piece");
        MachineOne a1 = new MachineOne();
        Move move = a1.minMax(actualBoard,colour,1);
        actualBoard.movePiece(move.getOldX(),move.getOldY(),move.getNewX(),move.getNewY());
        printBoard();
    }

    public void movePieceAI2(boolean colour)
    {
        System.out.println("Moving piece");
        MachineTwo a2 = new MachineTwo();
        Move move = a2.alphaBetaRoot(actualBoard,colour,4);
        actualBoard.movePiece(move.getOldX(),move.getOldY(),move.getNewX(),move.getNewY());
        printBoard();
    }


    public void movePieceHumanAttacker(boolean isWhite)
    {
        // amb timer
        //o means origin
        timer.start();
        int ox, oy, dx, dy;
        ox = oy = dx = dy = 0;
        boolean valid = false;
        while (!valid)
        {
            ox = selectCoordinate('x');
            oy = selectCoordinate('y');
            //d means destination
            dx = selectCoordinate('x');
            dy = selectCoordinate('y');
            if (this.actualBoard.hasPiece(ox, oy))
            {
                valid = this.actualBoard.getCell(ox, oy).checkMove(ox, oy, dx, dy, this.actualBoard);
            }
            if (!valid) System.out.println("Invalid move");
        }
        timer.stop();
        actualBoard.movePiece(ox,oy,dx,dy);
    }
    public void movePieceHumanDefender(boolean isWhite)
    {
        // sense timer
        //o means origin
        int ox, oy, dx, dy;
        ox = oy = dx = dy = 0;
        boolean valid = false;
        while (!valid)
        {
            ox = selectCoordinate('x');
            oy = selectCoordinate('y');
            //d means destination
            dx = selectCoordinate('x');
            dy = selectCoordinate('y');
            if (this.actualBoard.hasPiece(ox, oy))
            {
                valid = this.actualBoard.getCell(ox, oy).checkMove(ox, oy, dx, dy, this.actualBoard);
            }
            if (!valid) System.out.println("Invalid move");
        }
        actualBoard.movePiece(ox,oy,dx,dy);
    }


    public void printBoard() {
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        for (int j = 7; j >= 0; j--)
        {
            for (int i = 0; i < 8; i++)
            {

                System.out.print('|');

                if (actualBoard.getCell(i, j) != null)
                {
                    if      (i % 2 == 1 && j % 2 == 1)  System.out.print("##" + actualBoard.getCell(i, j).getName() + "##");
                    else if (i % 2 == 0 && j % 2 == 0)  System.out.print("##" + actualBoard.getCell(i, j).getName() + "##");
                    else                                System.out.print("  " + actualBoard.getCell(i, j).getName() + "  ");
                }
                else
                {
                    if      (i % 2 == 1 && j % 2 == 1)  System.out.print("#####");
                    else if (i % 2 == 0 && j % 2 == 0)  System.out.print("#####");
                    else                                System.out.print("     ");
                }

                if (i == 7)
                {
                    System.out.print("| " + (j + 1));
                }

            }
            System.out.println();
            System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        }

        System.out.print("   a   ");
        System.out.print("  b   ");
        System.out.print("  c   ");
        System.out.print("  d   ");
        System.out.print("  e   ");
        System.out.print("  f   ");
        System.out.print("  g   ");
        System.out.print("  h   ");
        System.out.println("");
    }

    /**
     * Private Functions
     */
    private int selectCoordinate(char coordinate)
    {
        int num = 0;

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a Coordinate " + coordinate + ":");
        try {
            char c = sc.next().charAt(0);
            switch (c) {
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                    return Character.getNumericValue(c) - 1;
                case 'a':
                    return 0;
                case 'b':
                    return 1;
                case 'c':
                    return 2;
                case 'd':
                    return 3;
                case 'e':
                    return 4;
                case 'f':
                    return 5;
                case 'g':
                    return 6;
                case 'h':
                    return 7;
                default:
                    return -1;
            }
        }
        catch (Exception e)
        {
            System.out.println("Error reading from user");
        }
        return num;
    }
}
