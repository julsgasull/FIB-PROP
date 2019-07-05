package main.domini.model;

/**
 * @whatis peón
 * */


public class Pawn extends Piece
{

    public Pawn(int pieceID, char name, Board board)
    {
        super(pieceID, name, board);

        if (name == 'p') super.setPieceScore(10);
        else super.setPieceScore(10);
    }

    public boolean checkMove(int oldX, int oldY, int newX, int newY, Board board)
    {

        if (board.hasPiece(newX, newY))
        {
            if (board.getCell(newX, newY).isWhite() == super.isWhite()) return false;
        }

        if (!super.isWhite())
        {
            if (oldX == 1)
            {//black pawn
                if (Math.abs(oldY - newY) == 1 && (newX - oldX) == 1 && board.hasPiece(newX, newY)) return true;
                else if (newY == oldY && (newX - oldX) == 1 && !board.hasPiece(newX, newY)) return true;
                return (newY == oldY && (newX - oldX) == 2 && !board.hasPiece(newX, newY) & !board.hasPiece(newX - 1, newY));
            } else {
                if (Math.abs(oldY - newY) == 1 && (newX - oldX) == 1 && board.hasPiece(newX, newY)) return true;
                return (newY == oldY && (newX - oldX) == 1 && !board.hasPiece(newX, newY));
            }
        } else {
            //white pawn
            if (oldX == 6)
            {
                //System.out.println("1r block");
                if (Math.abs(oldY - newY) == 1 && (newX - oldX) == -1 && board.hasPiece(newX, newY)) return true;
                else if (newY == oldY && (newX - oldX) == -1 && !board.hasPiece(newX, newY)) return true;
                return (newY == oldY && (newX - oldX) == -2 && !board.hasPiece(newX, newY) & !board.hasPiece(newX + 1, newY));
            } else {
                //System.out.println("2nd block");
                if (Math.abs(oldY - newY) == 1 && (newX - oldX) == -1 && board.hasPiece(newX, newY)) return true;
                return (newY == oldY & (newX - oldX) == -1 && !board.hasPiece(newX, newY));
            }
        }
    }
}
