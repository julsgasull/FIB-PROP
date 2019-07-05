package main.domini.model;

public class Human extends Player {
    /**
     * Attributes
     */
    private String username;
    private String password;

    /**
     * Constructor
     */
    public Human(int playerID, String username, String password) {
        super(playerID);
        this.username = username;
        this.password = password;
    }

    /**
     * Getters
     */
    public String getUsername() {
        return username;
    }
}
