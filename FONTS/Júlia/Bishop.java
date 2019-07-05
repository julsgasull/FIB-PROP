package main.domini.model;

/**
* @author Júlia Gasull
*/

public class Bishop extends Piece
{

    public Bishop(int pieceID, char name, Board board)
    {
        super(pieceID, name, board);

        if (name == 'b') super.setPieceScore(-30);
        else super.setPieceScore(30);
    }

    public boolean checkMove(int oldX, int oldY, int newX, int newY, Board board)
    {

        if (board.hasPiece(newX, newY))
        {
            if (board.getCell(newX, newY).isWhite() == super.isWhite()) return false;
        }

        if ((Math.abs(oldX - newX) == Math.abs(oldY - newY)) & (newY >= 0 & newY < 8 & newX >= 0 & newX < 8))
        {
            if(newX - oldX > 0 & newY - oldY > 0)
            {
                for(int i= 1; i< newX-oldX; i++)
                {
                    if(board.hasPiece(oldX + i,oldY + i))
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
                    if (board.hasPiece(oldX + i, oldY - i))
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
