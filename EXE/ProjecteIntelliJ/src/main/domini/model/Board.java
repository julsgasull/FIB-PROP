package main.domini.model;

import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

import static jdk.nashorn.internal.objects.NativeArray.push;

/**
 * @author Júlia Gasull
 */

public class Board
{

    /**
     * Attributes
     */
    private Piece[][] board;

    /**
     * Constructor
     */
    public Board() {
        this.board = new Piece[8][8];
        // Aquest loop itera per la taula i ho posa tot a null
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
    }

    public Board(Board b) {
        /**
         * @comment Problema: si creem una nova peça new
         * King, no sabem ni el nom ni el color. Hem de
         * parlar-ho.
         */
        board = new Piece[8][8];
        // Aquest loop itera per la taula i ho posa tot a null
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = null;
            }
        }
        // Aquest loop itera per la Board b (paràmetre) i la còpia
        int pieceID = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b.hasPiece(i, j)) {
                    // WHITE pieces
                    if (b.getCell(i, j).getName() == 'K') this.placePiece(i, j, pieceID, 'K', this);
                    else if (b.getCell(i, j).getName() == 'Q') this.placePiece(i, j, pieceID, 'Q', this);
                    else if (b.getCell(i, j).getName() == 'R') this.placePiece(i, j, pieceID, 'R', this);
                    else if (b.getCell(i, j).getName() == 'B') this.placePiece(i, j, pieceID, 'B', this);
                    else if (b.getCell(i, j).getName() == 'N') this.placePiece(i, j, pieceID, 'N', this);
                    else if (b.getCell(i, j).getName() == 'P') this.placePiece(i, j, pieceID, 'P', this);
                    // black pieces
                    if (b.getCell(i, j).getName() == 'k') this.placePiece(i, j, pieceID, 'k', this);
                    else if (b.getCell(i, j).getName() == 'q') this.placePiece(i, j, pieceID, 'q', this);
                    else if (b.getCell(i, j).getName() == 'r') this.placePiece(i, j, pieceID, 'r', this);
                    else if (b.getCell(i, j).getName() == 'b') this.placePiece(i, j, pieceID, 'b', this);
                    else if (b.getCell(i, j).getName() == 'n') this.placePiece(i, j, pieceID, 'n', this);
                    else if (b.getCell(i, j).getName() == 'p') this.placePiece(i, j, pieceID, 'p', this);
                    ++pieceID;
                }

            }
        }
    }

    /**
     * public functions
     */
    public Piece getCell(int i, int j) {
        return this.board[i][j];
    }

    public Piece getCellTile(int i){
        int row = i/8;
        int column = i-row*8;
        return getCell(row, column);
    }

    public void placePiece(int i, int j, int pieceID, char c, Board b) {
        switch (c) {
            case 'P':   // white pawn
            case 'p':   // black pawn
                board[i][j] = new Pawn(pieceID, c, b);
                break;
            case 'N':   // white knight
            case 'n':   // black knight
                board[i][j] = new Knight(pieceID, c, b);
                break;
            case 'B':   // white bishop
            case 'b':   // black bishop
                board[i][j] = new Bishop(pieceID, c, b);
                break;
            case 'R':   // white tower
            case 'r':   // black tower
                board[i][j] = new Rook(pieceID, c, b);
                break;
            case 'Q':   // white queen
            case 'q':   // black queen
                board[i][j] = new Queen(pieceID, c, b);
                break;
            case 'K':   // white king
            case 'k':   // black king
                board[i][j] = new King(pieceID, c, b);
                break;
            default:
                break;
        }

    }

    public boolean hasPieceTile(int i){
        int row = i/8;
        int column = i-row*8;
        return getCell(row, column) != null;
    }

    public boolean hasPiece(int i, int j) {
        return getCell(i, j) != null;
    }

    public void movePiece(int iOrigin, int jOrigin, int iDestiny, int jDestiny) {
        // tot està comprovat -> movem directament
        Piece p = getCell(iOrigin, jOrigin);
        setPiece(iDestiny, jDestiny, p);
        clearCell(iOrigin, jOrigin);
    }

    public void updateBoard(Move move) {
        setPiece(move.getNewX(), move.getNewY(), move.getPiece());
        clearCell(move.getOldX(), move.getOldY());
    }

    public boolean isCheckMate(boolean whiteKing)
    {
        int i = 0;
        int j = 0;
        boolean kingFound = false;
        ArrayList<Point> points = new ArrayList<>();


        if (whiteKing) {
            while (!kingFound && i < 8) {
                if (this.hasPiece(i, j) && this.getCell(i, j).getName() == 'K') {
                    kingFound = true;
                } else {
                    if (j == 7) {
                        i++;
                        j = 0;
                    } else j++;
                }
            }
            if (i==8)return false;
            Point pt = new Point(i,j);
            int originXKing = i;
            int originYKing = j;
            points.add(pt);

            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    if (i + dx >= 0 && j + dy >= 0 && i + dx < 8 && j + dy < 8) {
                        if(this.hasPiece(i+dx,j+dy) && this.getCell(i+dx,j+dy).isWhite());
                        else {
                            Point p = new Point(i + dx, j + dy);
                            points.add(p);
                        }
                    }
                }
            }
            int pointsIt = 0;
            while (pointsIt < points.size()) {
                Point p = new Point(points.get(pointsIt));
                int x = (int)p.getX();
                int y =(int) p.getY();
                boolean found = false;
                for ( i = 0; i < 8; i++) {
                    for ( j = 0; j < 8; j++) {
                        if (this.hasPiece(i, j) && !this.getCell(i, j).isWhite()) {
                            Move move = new Move(originXKing,originYKing,x,y,this.getCell(originXKing,originYKing));
                            Board aux = new Board(this);
                            if (!(originXKing == x && originYKing == y))aux.updateBoard(move);
                            found = this.getCell(i, j).checkMove(i, j, x, y, aux);
                        }
                        if (found) {
                            if (x == originXKing && y == originYKing){
                                boolean canBeKilled = false;
                                for (int it1 = 0; it1 < 8; it1++){
                                    for(int it2 = 0; it2 < 8; it2++){
                                    if (this.hasPiece(it1,it2) && this.getCell(it1,it2).isWhite() && this.getCell(it1,it2).getName() != 'K')canBeKilled = this.getCell(it1,it2).checkMove(it1,it2,i,j,this);
                                    if (canBeKilled) return false;
                                    }
                                }
                            }
                            i = 8;
                            j = 8;
                        }
                    }
                }
                if (!found) return false;
                else pointsIt++;
            }
        } else {
            while (!kingFound && i < 8) {
                if (this.hasPiece(i, j) && this.getCell(i, j).getName() == 'k') {
                    kingFound = true;
                } else {
                    if (j == 7) {
                        i++;
                        j = 0;
                    } else j++;
                }
            }
            if (i == 8) return false;
            Point pt = new Point(i,j);
            int originXKing = i;
            int originYKing = j;
            points.add(pt);

            for (int dx = -1; dx <= 1; ++dx) {
                for (int dy = -1; dy <= 1; ++dy) {
                    if (i + dx >= 0 && j + dy >= 0 && i + dx < 8 && j + dy < 8) {
                        if(this.hasPiece(i+dx,j+dy) && !this.getCell(i+dx,j+dy).isWhite());
                        else {
                            Point p = new Point(i + dx, j + dy);
                            points.add(p);
                        }
                    }
                }
            }
            int pointsIt = 0;
            while (pointsIt < points.size()) {
                Point p = new Point(points.get(pointsIt));
                int x = (int)p.getX();
                int y =(int) p.getY();
                boolean found = false;
                for ( i = 0; i < 8; i++) {
                    for ( j = 0; j < 8; j++) {
                        if (this.hasPiece(i, j) && this.getCell(i, j).isWhite()) {
                            Move move = new Move(originXKing,originYKing,x,y,this.getCell(originXKing,originYKing));
                            Board aux = new Board(this);
                            if (!(originXKing == x && originYKing == y))aux.updateBoard(move);
                            found = this.getCell(i, j).checkMove(i, j, x, y, aux);
                        }
                        if (found) {
                            if (x == originXKing && y == originYKing){
                                boolean canBeKilled = false;
                                for (int it1 = 0; it1 < 8; it1++){
                                    for(int it2 = 0; it2 < 8; it2++){
                                        if (this.hasPiece(it1,it2) && !this.getCell(it1,it2).isWhite() && this.getCell(it1,it2).getName() != 'k')canBeKilled = this.getCell(it1,it2).checkMove(it1,it2,i,j,this);
                                        if (canBeKilled) return false;
                                    }
                                }
                            }
                            i = 8;
                            j = 8;
                        }
                    }
                }
                if (!found) return false;
                else pointsIt++;
            }
        }
        return true;
    }

    /**
     * private functions
     */
    private void clearCell ( int i, int j){
        board[i][j] = null;
    }

    private void setPiece ( int i, int j, Piece p){
        board[i][j] = p;
    }

    public void printBoard ()
    {
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+");
        for (int j = 7; j >= 0; j--) {
            for (int i = 0; i < 8; i++) {

                System.out.print('|');

                if (this.getCell(i, j) != null) {
                    if (i % 2 == 1 && j % 2 == 1) System.out.print("##" + this.getCell(i, j).getName() + "##");
                    else if (i % 2 == 0 && j % 2 == 0)
                        System.out.print("##" + this.getCell(i, j).getName() + "##");
                    else System.out.print("  " + this.getCell(i, j).getName() + "  ");
                } else {
                    if (i % 2 == 1 && j % 2 == 1) System.out.print("#####");
                    else if (i % 2 == 0 && j % 2 == 0) System.out.print("#####");
                    else System.out.print("     ");
                }

                if (i == 7) {
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
}


