package main.domini.model;

public class Score {

    /**
     * Atributes
     */

    private String playerName;
    private int numPlays;
    private long matchTime;


    /**
     * Constructor
     */

    public Score(String playerName, int numPlays, long matchTime) {
        this.playerName = playerName;
        this.numPlays = numPlays;
        this.matchTime = matchTime;
    }

    /**
     * Setters & Getters
     */

    public String getPlayerName() {
        return playerName;
    }

    public int getNumPlays() {
        return numPlays;
    }

    public long getMatchTime() {
        return matchTime;
    }

}
