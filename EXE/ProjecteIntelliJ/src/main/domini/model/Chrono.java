package main.domini.model;

/**
 * @author Sergi Sendrós Plana
 */


public final class Chrono
{

    /**
     * Attributes
     */
    private long begin;
    private long end;
    private long current;

    /**
     * Constructor
     */
    public Chrono()
    { this.current = 0; }

    /**
     * Getters and setters
     */
    public long getTime()
    {
        return current;
    }

    /**
     * Public functions
     */
    public void start()
    {
        begin = System.currentTimeMillis();
    }
    public void stop()
    {
        end = System.currentTimeMillis();
        current += end - begin;
    }
}