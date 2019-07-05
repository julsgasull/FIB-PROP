package main.domini.model;

/**
* @author Júlia Gasull
*/


public class Move
{
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;
    private Piece piece;

    public Move(int oldX, int oldY, int newX, int newY, Piece piece)
    {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.piece = piece;
    }

    public Move()
    {
        this.oldX = -1;
        this.oldY = -1;
        this.newX = -1;
        this.newY = -1;
        this.piece = null;
    }

    public int getOldX()
    {
        return oldX;
    }
    public int getOldY()
    {
        return oldY;
    }
    public int getNewX()
    {
        return newX;
    }
    public int getNewY()
    {
        return newY;
    }
    public Piece getPiece()
    {
        return piece;
    }
    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }
    public void setOldX(int oldX)
    {
        this.oldX = oldX;
    }
    public void setOldY(int oldY)
     {
        this.oldY = oldY;
    }
    public void setNewX(int newX)
    {
        this.newX = newX;
    }
    public void setNewY(int newY)
    {
        this.newY = newY;
    }
}
