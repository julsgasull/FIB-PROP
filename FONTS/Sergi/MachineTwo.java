package main.domini.model;

import java.util.ArrayList;

/**
* @author Sergi Sendrós
*/

public class MachineTwo extends IAPlayer
{

    /**
    * Constructor
    */
    public MachineTwo()
    {
        super(1);
    }

    /**
    * Public functions
    */
    public Move alphaBetaRoot(Board b,boolean white, int depht )
    {

        ArrayList<Move> possibleMoves = new ArrayList<>();
        Move bestMoveFound = new Move();
        for (int i = 0; i < 8 ; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (b.hasPiece(i,j) && b.getCell(i,j).isWhite() == white)
                {
                    for (int k = 0; k < 8; k++)
                    {
                        for (int l = 0; l < 8; l++)
                        {
                            if (b.getCell(i,j).checkMove(i,j,k,l,b))
                            {
                                Move move   = new Move(i,j,k,l,b.getCell(i,j));
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
            }
        }
        if (white)
        {
            int bestMove = -10000;
            for (int it = 0; it < possibleMoves.size(); it++)
            {
                Board auxBoard = new Board(b);
                Move newMove = possibleMoves.get(it);
                auxBoard.updateBoard(newMove);
                int value = alphaBeta(auxBoard, !white, depht - 1, -1000000, 1000000);
                if (value >= bestMove)
                {
                    bestMove = value;
                    bestMoveFound = newMove;
                }
            }
            return bestMoveFound;
        }
        else
        {
            int bestMove = 10000;
            for (int it = 0; it < possibleMoves.size(); it++)
            {
                Board auxBoard = new Board(b);
                Move newMove = possibleMoves.get(it);
                auxBoard.updateBoard(newMove);
                int value = alphaBeta(auxBoard, !white, depht - 1, -1000000, 1000000);
                if (value <= bestMove)
                {
                    bestMove = value;
                    bestMoveFound = newMove;
                }
            }
            return bestMoveFound;
        }
    }

    public int alphaBeta(Board b,boolean white,int depht, int alpha, int beta)
    {
        //colour = true -> white positive
        //colour = false -> black negative

        if(depht == 0)
        {    // || b.isCheckMate(!white)
            if (b.isCheckMate(!white)) return evaluate(b)*10000;
            else return evaluate(b);
        }

        ArrayList<Move> possibleMoves = new ArrayList<>();

        for (int i = 0; i < 8 ; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (b.hasPiece(i,j) && b.getCell(i,j).isWhite() == white)
                {
                    for (int k = 0; k < 8; k++)
                    {
                        for (int l = 0; l < 8; l++)
                        {
                            if (b.getCell(i,j).checkMove(i,j,k,l,b))
                            {
                                Move move   = new Move(i,j,k,l,b.getCell(i,j));
                                possibleMoves.add(move);
                            }
                        }
                    }
                }
            }
        }
        if (white)
        {
            int value = -100000000;
            for (int it = 0; it < possibleMoves.size(); it++)
            {
                Board auxBoard = new Board(b);
                auxBoard.updateBoard(possibleMoves.get(it));
                value = Math.max(value,alphaBeta(auxBoard,false,depht-1,alpha,beta));
                alpha = Math.max(alpha,value);
                if (alpha >= beta)
                {
                    return value;
                }
            }
            return value;
        }
        else
        {
            int value = 100000000;
            for (int it = 0; it < possibleMoves.size(); it++)
            {
                Board auxBoard = new Board(b);
                auxBoard.updateBoard(possibleMoves.get(it));
                value = Math.min(value,alphaBeta(auxBoard,true,depht-1,alpha,beta));
                beta = Math.min(beta,value);

                if (alpha >= beta)
                {
                    return value;
                }
            }
            return value;
        }
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
