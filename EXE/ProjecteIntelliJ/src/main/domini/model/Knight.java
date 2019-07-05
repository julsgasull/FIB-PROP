package main.domini.model;

/**
 * @whatis caballo
 * */


import java.util.*;

public class Knight extends Piece {
    private int pieceScore;

    public Knight(int pieceID, char name, Board board) {
        super(pieceID, name, board);

        if (name == 'n') this.pieceScore = -30;
        else this.pieceScore = 30;
    }

    public boolean checkMove(int oldX, int oldY, int newX, int newY, Board board) {

        if (board.hasPiece(newX, newY)) {
            if (board.getCell(newX, newY).isWhite() == super.isWhite()) return false;
        }
         return (((oldX - newX) * (oldX - newX) + (oldY - newY) * (oldY - newY) == 5) & (newY >= 0 & newY < 8 & newX >= 0 & newX < 8));
    }
}
