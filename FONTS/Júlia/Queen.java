package main.domini.model;

/**
* @author Júlia Gasull
*/

import java.util.*;

public class Queen extends Piece
{
    //private int pieceScore;

    public Queen(int pieceID, char name, Board board)
    {
        super(pieceID, name, board);
        if (name == 'q') super.setPieceScore(-90);
        else super.setPieceScore(90);
    }

    public boolean checkMove(int oldX, int oldY, int newX, int newY, Board board)
    {
        if (board.hasPiece(newX, newY))
        {
            if (board.getCell(newX, newY).isWhite() == super.isWhite()) return false;
        }
        //rook check Y
        if ((newX >= 0 & newX < 8) && (newY >= 0 & newY < 8) & (oldX == newX))
        {
            if (newY > oldY)
            {
                for (int i = 1 ; i < newY - oldY ; i++)
                {
                    if (board.hasPiece(oldX,oldY + i)) return false;
                }
                return true;
            }
            if (newY < oldY)
            {
                for (int i = 1 ; i < oldY - newY ; i++)
                {
                    if (board.hasPiece(oldX,newY + i)) return false;
                }
                return true;
            }
            else return false;
        }
        //rook check X
        if ((newX >= 0 && newX < 8) & (newY >= 0 && newY < 8) & (oldY == newY))
        {
            if (newX > oldX)
            {
                for (int i = 1 ; i < newX - oldX ; i++)
                {
                    if (board.hasPiece(oldX + i,oldY)) return false;
                }
                return true;
            }
            if (newX < oldX)
            {   //always true here, comparison just visual.
                for (int i = 1; i < oldX - newX ; i++)
                {
                    if (board.hasPiece(newX + i,oldY)) return false;
                }
                return true;
            }
            else return false;
        }

        //bishop check
        if ((Math.abs(oldX - newX) == Math.abs(oldY - newY)) & (newY >= 0 & newY < 8 & newX >= 0 & newX < 8))
        {
            if (newX - oldX > 0 & newY - oldY > 0)
            {
                for (int i= 1; i< newX-oldX; i++)
                {
                    if (board.hasPiece(oldX + i,oldY + i))
                    {
                        return false;
                    }
                }
                return true;
            }
            if (newX - oldX < 0 & newY - oldY < 0)
            {
                for (int i = 1; i< oldX - newX; i++)
                {
                    if (board.hasPiece(newX + i, newY + i))
                    {
                        return false;
                    }
                }
                return true;
            }
            if (((newX - oldX) > 0) & ((newY - oldY) < 0))
            {
                for (int i = 1; i< newX - oldX; i++)
                {
                    if(board.hasPiece(oldX + i, oldY - i))
                    {
                        return false;
                    }
                }
                return true;
            }
            if (newX - oldX < 0 & newY - oldY > 0)
            {
                for (int i = 1; i< Math.abs(newX - oldX); i++)
                {
                    if (board.hasPiece(oldX - i, oldY + i))
                    {
                        return false;
                    }
                }
                return true;
            }
            else return false;
        }
    return false;
    }
}
