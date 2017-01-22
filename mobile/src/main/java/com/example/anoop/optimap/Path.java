package com.example.anoop.optimap;

/**
 * Created by anoop on 1/21/2017.
 */
public class Path
{
    private final String id;

    private final Destination source;

    private final Destination destination;

    private final int time;


    public Path( String id, Destination source, Destination destination, int time )
    {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.time = time;
    }


    public String getId()
    {
        return id;
    }


    public Destination getDestination()
    {
        return destination;
    }


    public Destination getSource()
    {
        return source;
    }


    public int getTime()
    {
        return time;
    }


    @Override
    public String toString()
    {
        return source + " " + destination;
    }
}