package main.domini.model;

import main.domini.controladorsDeDomini.CtrlDomain;


/**
 * @author X
 */

import java.util.*;

public class Problem
{

    private CtrlDomain ctrlDomain = new CtrlDomain();

    /**
     * Attributes
     */
    private int problemID;
    private String name;
    private int nPlays;    // No definitiu
    private String fen;
    private Vector<RankingLine> ranking;
    private String difficulty;

    /**
     * Constructor
     */
    public Problem(int problemID, String name, int nPlays, String fen)
    {
        this.problemID  = problemID;
        this.name       = name;
        this.nPlays     = nPlays;
        this.fen        = fen;
        /*this.ranking  =*/ getRankingFromDB();
        this.difficulty = setDifficulty();
    }

    /**
     * Getters and setters
     */
    public int      getProblemID() {
        return problemID;
    }
    public String   getName() {
        return name;
    }
    public String   getDificculty() { return difficulty; }
    public String   getFen() {
        return fen;
    }
    public int      getnPlays() {
        return nPlays;
    }

    public void     setName(String name) {
        this.name = name;
    }



    /**
     * Public Functions
     */
    public boolean validateProblem()
    {
        if (validFEN() && validateSolution(whoStarts())) return true;
        return false;
    }
    public Vector<String> getRankingLines()
    {
        /* converteix el vector ranking de la classe
         * en un vector de strings amb el format de
         * la BD -> problemID problemName position
         * playerName numPlays matchTime
         */
        Vector<String> result = new Vector<>();
        for (int i = 0; i < this.ranking.size(); i++)
        {
            String actual =
                    this.problemID                                              + " " +
                            this.name                                                   + " " +
                            this.ranking.elementAt(i).getPosition()                     + " " +
                            this.ranking.elementAt(i).getInformation().getPlayerName()  + " " +
                            this.ranking.elementAt(i).getInformation().getNumPlays()    + " " +
                            this.ranking.elementAt(i).getInformation().getMatchTime();
            result.add(actual);
        }
        return result;
    }
    public void addNewRankingLine(String playerName, int numPlays, long matchTime)
    {
        ranking.addElement(new RankingLine(this, playerName, numPlays, matchTime));
        Collections.sort(ranking);
        setPositions();
    }
    public Board getMatrix()
    {
        Board b = new Board();
        String fen = this.getFen();
        int i = 0;
        int j = 0;
        int pieceID = 0;

        for (int fenIterator = 0; fenIterator < fen.length(); fenIterator++)
        {
            char c = fen.charAt(fenIterator);
            switch (c)
            {
                case ' ':
                    fenIterator = fen.length(); /* Not important for this */
                    /* Empty cells */
                case '1': ++j; break;
                case '2': j = j + 2; break;
                case '3': j = j + 3; break;
                case '4': j = j + 4; break;
                case '5': j = j + 5; break;
                case '6': j = j + 6; break;
                case '7': j = j + 7; break;
                case '8': j = j + 8; break;
                /* Full cells */
                case 'P':   // white pawn
                case 'p':   // black pawn
                case 'N':   // white knight
                case 'n':   // black knight
                case 'B':   // white bishop
                case 'b':   // black bishop
                case 'R':   // white tower
                case 'r':   // black tower
                case 'Q':   // white queen
                case 'q':   // black queen
                case 'K':   // white king
                case 'k':   // black king
                    b.placePiece(i, j, pieceID, c, b); j++; pieceID++;
                    break;
                /* Next row */
                case '/': i++; j = 0; break;
                /* Default */
                default: break;
            }
        }
        return b;
    }

    public boolean whoStarts()
    {   //phase 1..2
        int spaceCounter = 0;
        for (int i = 0; i < this.fen.length(); i++)
        {
            char c = this.fen.charAt(i);
            if (c == ' ')
            {
                spaceCounter++;
                if (spaceCounter == 1 && this.fen.charAt(i + 1) == 'w') return true;
            }
        }
        return false;
    }

    /**
     * Private functions
     */
    private boolean dfs(int nPlays, boolean starter, Board b)
    {
        boolean solved;
        if (nPlays == 0)
        {
            if (starter) {
                return b.isCheckMate(!starter);
            } else {
                return b.isCheckMate(starter);
            }
        } else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (b.hasPiece(i, j) && b.getCell(i, j).isWhite() == starter)
                    {
                        for (int k = 0; k < 8; k++) {
                            for (int l = 0; l < 8; l++) {
                                if (b.getCell(i, j).checkMove(i, j, k, l, b))
                                {
                                    Move move = new Move(i, j, k, l, b.getCell(i, j));
                                    Board temp = new Board(b);
                                    temp.updateBoard(move);
                                    solved = dfs(nPlays - 1, !starter, temp);
                                    if (solved) return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private boolean validFEN()
    {
        /* Bucle per vaildar si en algun cas no es
         * correcte, retorna false finalment retorna
         * true si acaba el bucle.
         */
        int validLine = 0;
        int P, p, N, n, B, b, R, r, Q, q, K, k;
        P = p = N = n = B = b = R = r = Q = q = K = k = 0;

        /* Comprovació final correcte */
        String lastFen = fen.substring(fen.length() - 7);
        if (!lastFen.equals("- - 0 1")) return false;

        for (int loop = 0; loop < (fen.length() - 8); loop++)
        {
            char c = fen.charAt(loop);
            switch (c)
            {
                case '/':
                case ' ': if (validLine != 8) return false; validLine = 0; break;
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': validLine += Character.getNumericValue(c); break;
                case 'P': P++; validLine++; break;
                case 'p': p++; validLine++; break;
                case 'N': N++; validLine++; break;
                case 'n': n++; validLine++; break;
                case 'B': B++; validLine++; break;
                case 'b':
                    if (loop != 0)
                    {
                        if (fen.charAt(loop - 1) == ' ') break;
                        else {
                            b++;
                            validLine++;
                        }
                    } else {
                        b++;
                        validLine++;
                    }
                    break;
                case 'R': R++; validLine++; break;
                case 'r': r++; validLine++; break;
                case 'Q': Q++; validLine++; break;
                case 'q': q++; validLine++; break;
                case 'K': K++; validLine++; break;
                case 'k': k++; validLine++; break;
                case 'w': break;
                default: return false;
            }
            if (P > 8 | p > 8 | N > 2 | n > 2 | B > 2 | b > 2 | R > 2 | r > 2 | Q > 1 | q > 1 | K > 1 | k > 1) return false;
        }
        if (K != 1 | k != 1) return false;
        return true;
    }
    private boolean validateSolution(boolean starter)
    {
        Board b = this.getMatrix();
        boolean solved = false;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b.hasPiece(i, j) && b.getCell(i, j).isWhite() == starter)
                {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (b.getCell(i, j).checkMove(i, j, k, l, b))
                            {
                                Move move = new Move(i, j, k, l, b.getCell(i, j));
                                Board temp = new Board(b);
                                temp.updateBoard(move);
                                solved = dfs(this.nPlays * (2) - 1, !starter, temp);
                                if (solved) return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    private void setPositions()
    {
        for (int i = 0; i < ranking.size(); ++i)
        {
            ranking.elementAt(i).setPosition(i + 1);
        }
    }
    private void getRankingFromDB()
    {
        Queue<String> q = ctrlDomain.showProblemRanking(problemID, difficulty);
        if (q != null)
        {
            ranking = new Vector<>();
            while (!q.isEmpty())
            {
                // actual item
                String actualRankingLine = q.peek();
                // get attributes to add ranking lines to ranking
                int position = Integer.parseInt(getRankingLinePosition(actualRankingLine));
                String username = getRankingLinePlayerName(actualRankingLine);
                int numPlays = Integer.parseInt(getRankingLineNumplays(actualRankingLine));
                long matchTime = Long.parseLong(getRankingLineMatchTime(actualRankingLine));

                // insert ranking line to this.ranking
                addNewRankingLine(username, numPlays, matchTime);

                // next item
                q.remove();
            }
        } else ranking = new Vector<>();
    }
    private String getRankingLinePosition(String actualRankingLine)
    {   // phase 2..3
        String result = "";
        int spaceCounter = 0;
        for (int i = 0; spaceCounter < 3 && i < actualRankingLine.length(); i++)
        {
            char c = actualRankingLine.charAt(i);
            if (c == ' ')                   spaceCounter++;
            else if (spaceCounter == 2)     result += c;
        }
        return result;
    }
    private String getRankingLinePlayerName(String actualRankingLine)
    {   // phase 3..4
        String result = "";
        int spaceCounter = 0;
        for (int i = 0; spaceCounter < 4 && i < actualRankingLine.length(); i++)
        {
            char c = actualRankingLine.charAt(i);
            if (c == ' ')                   spaceCounter++;
            else if (spaceCounter == 3)     result += c;
        }
        return result;
    }
    private String getRankingLineNumplays(String actualRankingLine)
    {   // phase 4..5
        String result = "";
        int spaceCounter = 0;
        for (int i = 0; spaceCounter < 5 && i < actualRankingLine.length(); i++)
        {
            char c = actualRankingLine.charAt(i);
            if (c == ' ')                   spaceCounter++;
            else if (spaceCounter == 4)     result += c;
        }
        return result;
    }
    private String getRankingLineMatchTime(String actualRankingLine)
    {
        // phase 5..final
        String result = "";
        int spaceCounter = 0;
        for (int i = 0; i < actualRankingLine.length() && (spaceCounter != 5); i++)
        {
            char c = actualRankingLine.charAt(i);
            if (c == ' ')           spaceCounter++;
            if (spaceCounter == 5)  result = actualRankingLine.substring(i + 1);
        }
        return result;
    }

    private String setDifficulty()
    {
        int dif = this.nPlays * this.countPieces();
        if ( 0 <= dif && dif <= 16) return "VeryEasy";
        else if (17 <= dif && dif <= 48) return "Easy";
        else if (49 <= dif && dif <= 80) return "Medium";
        else if (81 <= dif && dif <= 112) return "Hard";
        else return "VeryHard";
    }

    private int countPieces()
    {
        int npieces = 0;
        for (int loop = 0; loop < (fen.length() - 8); loop++)
        {
            char c = fen.charAt(loop);
            switch (c) {
                case '/':
                case ' ':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':break;
                case 'P':
                case 'p':
                case 'N':
                case 'n':
                case 'B':
                    npieces++;
                    break;
                case 'b':
                    if (loop != 0) {
                        if (fen.charAt(loop - 1) == ' ') break;
                        else {
                            npieces++;
                        }
                    } else {
                        npieces++;
                    }
                    break;
                case 'R':
                case 'r':
                case 'Q':
                case 'q':
                case 'K':
                case 'k':
                    npieces++;
                    break;
                case 'w':
                    break;
                default:
                    return 0;
            }
        }
        return npieces;
    }

    public boolean update(String probName, int nPlays, String fen) {
        String auxFen = this.fen;
        this.fen = fen;
        if (validateProblem()) {
            this.name = probName;
            this.nPlays = nPlays;
            this.difficulty = setDifficulty();
            return true;
        }
        else {
            this.fen = auxFen;
            return false;
        }
    }
/*
    public boolean update(String probName, int nPlays, String fen, CtrlDomain ctrlDomain) {
        this.
        return
    }
    */
}