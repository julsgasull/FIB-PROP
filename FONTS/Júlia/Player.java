package main.domini.model;

/**
* @author Júlia Gasull
*/


public class Player
{
    /**
     * Attributes
     */
    protected int playerID;

    /**
     * Constructor
     */
    public Player(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * Getters
     */
    public int getPlayerID()
    {
        return playerID;
    }
}
