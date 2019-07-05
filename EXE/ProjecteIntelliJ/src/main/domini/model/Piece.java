package main.domini.model;

/**
 * @author Aarón García Sánchez
 */

public abstract class Piece {

    /**
     * Attributes
     */
    private     int     pieceID;
    protected   char    name;
    private     Board   actualBoard;
    private     int     pieceScore;

    public void setPieceScore(int pieceScore) {
        this.pieceScore = pieceScore;
    }

    /**
     * Constructor
     */
    public Piece(int pieceID, char name, Board actualBoard)
    {
        this.pieceID        = pieceID;
        this.name           = name;
        this.actualBoard    = actualBoard;
    }

    public Piece(int pieceID, char name)
    {
        this.pieceID        = pieceID;
        this.name           = name;
        this.actualBoard    = null;
    }

    /**
     * Getters and setters
     */
    public int getPieceID() { return pieceID; }
    public char getName() { return name; }
    public Board getBoard() { return actualBoard; }
    public int getPieceScore() { return pieceScore; }

    public void setPieceID(int pieceID) { this.pieceID = pieceID;}
    public void setName(char name) { this.name = name;}


    /**
     * Public Functions
     */
    public boolean isWhite() {
        int ascii = (int) name;
        if      (ascii >= 65 & ascii <= 90)  return true;
        else if (ascii >= 97 & ascii <= 122) return false;
        return true;
    }
    public abstract boolean checkMove(int oldX, int oldY, int newX, int newY, Board board);
}

