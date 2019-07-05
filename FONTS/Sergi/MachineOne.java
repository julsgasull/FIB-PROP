package main.domini.model;

import java.util.ArrayList;

/**
* @author Sergi Sendrós
*/

public class MachineOne extends IAPlayer
{


    /**
    * Constructor
    */
    public MachineOne()
    {
        super(1);
    }

    /**
    * Public functions
    */
    public Move minMax(Board b,boolean colour,int depht)
    {
        //colour = true -> white positive
        //colour = false -> black negative
        ArrayList<Move> possibleMoves = new ArrayList<>();
        ArrayList<Board> possibleBoards = new ArrayList<>();
        Move bestMove = new Move();

        for (int i = 0; i < 8 ; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (b.hasPiece(i,j) && b.getCell(i,j).isWhite() == colour)
                {
                    for (int k = 0; k < 8; k++)
                    {
                        for (int l = 0; l < 8; l++)
                        {
                            if (b.getCell(i,j).checkMove(i,j,k,l,b))
                            {
                                Move move   = new Move(i,j,k,l,b.getCell(i,j));
                                possibleMoves.add(move);
                                Board temp  = new Board(b);
                                temp.updateBoard(move);
                                possibleBoards.add(temp);
                            }
                        }
                    }
                }
            }
        }
        if (colour)
        {
            int min = 100000;
            for (int it = 0; it < possibleMoves.size(); it++)
            {
                int score = -max(possibleBoards.get(it), depht, false);
                if (score < min)
                {
                    min = score;
                    bestMove = possibleMoves.get(it);
                }

            }
        }
        else
        {
            int min = 100000;
            for (int it = 0; it < possibleMoves.size(); it++)
            {
                int score = max(possibleBoards.get(it), depht, true);
                if (score < min )
                {
                    min = score;
                    bestMove = possibleMoves.get(it);
                }
            }
        }
        return bestMove;
    }

    private int min(Board board, int depht, boolean colour)
    {
        if (depht == 0)
        {
            return evaluate(board);
        }
        int min = +100000;

        ArrayList<Move>     possibleMoves   = new ArrayList<>();
        ArrayList<Board>    possibleBoards  = new ArrayList<>();

        for (int i = 0; i < 8 ; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.hasPiece(i,j) && board.getCell(i,j).isWhite() == colour)
                {
                    for (int k = 0; k < 8; k++)
                    {
                        for (int l = 0; l < 8; l++)
                        {
                            if (board.getCell(i,j).checkMove(i,j,k,l,board))
                            {
                                Move move   = new Move(i,j,k,l,board.getCell(i,j));
                                possibleMoves.add(move);
                                Board temp  = new Board(board);
                                temp.updateBoard(move);
                                possibleBoards.add(temp);
                            }
                        }
                    }
                }
            }
        }

        for (int it = 0; it < possibleMoves.size(); it++)
        {
            int score = max(possibleBoards.get(it), depht - 1, !colour);
            if (score < min) min = score;
        }
        return min;
    }
    private int max(Board board, int depht, boolean colour)
    {
        if (depht == 0)
        {
            return evaluate(board);
        }
        int max = -100000;

        ArrayList<Move>     possibleMoves   = new ArrayList<>();
        ArrayList<Board>    possibleBoards  = new ArrayList<>();

        for (int i = 0; i < 8 ; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.hasPiece(i,j) && board.getCell(i,j).isWhite() == colour)
                {
                    for (int k = 0; k < 8; k++)
                    {
                        for (int l = 0; l < 8; l++)
                        {
                            if (board.getCell(i,j).checkMove(i,j,k,l,board))
                            {
                                Move move   = new Move(i,j,k,l,board.getCell(i,j));
                                possibleMoves.add(move);
                                Board temp  = new Board(board);
                                temp.updateBoard(move);
                                possibleBoards.add(temp);
                            }
                        }
                    }
                }
            }
        }

        for (int it = 0; it < possibleMoves.size(); it++)
        {
            int score = min(possibleBoards.get(it), depht - 1, !colour);
            if (score > max) max = score;
        }
        return max;

    }
    private int evaluate(Board board)
    {
        int value = 0;
        for (int i = 0; i < 8 ; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (board.hasPiece(i,j)) value += board.getCell(i,j).getPieceScore();
            }
        }
        return value;
    }
}
