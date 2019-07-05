package main.domini.model;

/**
 * @whatis rey
 * */


import java.util.*;

public class King extends Piece {

    public King(int pieceID, char name, Board board) {
        super(pieceID, name, board);

        if (name == 'k') super.setPieceScore(-900);
        else super.setPieceScore(900);
    }

    public boolean checkMove(int oldX, int oldY, int newX, int newY, Board board) {

        if (board.hasPiece(newX, newY)) {
            if (board.getCell(newX, newY).isWhite() == super.isWhite()) return false;
        }

        return (Math.abs(newX - oldX) <= 1 & Math.abs(newY - oldY) <=1 & (newX >= 0 & newX <= 7) & (newY >= 0 & newY <= 7));
    }
}
