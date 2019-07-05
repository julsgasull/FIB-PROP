package main.domini.model;

public class RankingLine implements Comparable<RankingLine>
{
    /**
     * Atributes
     */
    private Problem problem;
    private int position;
    private Score information;

    /**
     * Constructor
     */
    public RankingLine(Problem problem, int position, String playerName, int numPlays, long matchTime)
    {
        this.problem = problem;
        this.position = position;
        this.information = new Score(playerName, numPlays, matchTime);
    }

    public RankingLine(Problem problem, String playerName, int numPlays, long matchTime)
    {
        this.problem = problem;
        this.information = new Score(playerName, numPlays, matchTime);
    }

    /**
     * Getters & Setters
     */

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Score getInformation() {
        return information;
    }

    public long getMatchTime(){
        return this.information.getMatchTime();
    }


    public int compareTo(RankingLine compareFruit) {

        long compareQuantity = ((RankingLine) compareFruit).getMatchTime();

        //ascending order
        return (int) (this.information.getMatchTime() - compareQuantity);

        //descending order
        //return compareQuantity - this.quantity;

    }
}
