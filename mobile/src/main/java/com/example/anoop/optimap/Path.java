package com.example.anoop.optimap;

/**
 * Created by anoop on 1/21/2017.
 */
public class Path
{
    public  Destination source;

    public  Destination destination;

    public long time;


    public Path( Destination source, Destination destination, long time )
    {
        this.source = source;
        this.destination = destination;
        this.time = time;
    }

    public Destination getDestination()
    {
        return destination;
    }


    public Destination getSource()
    {
        return source;
    }


    public long getDuration()
    {
        return time;
    }


    @Override
    public String toString()
    {
        return source + " " + destination;
    }
}