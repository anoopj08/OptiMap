package com.example.anoop.optimap;

/**
 * Created by anoop on 1/21/2017.
 */

import java.util.ArrayList;



public class Graph //extends Optrix
{
    private final ArrayList<Destination> destinations;

    private final ArrayList<Path> paths;


    public Graph( ArrayList<Destination> destinations, ArrayList<Path> paths )
    {
        this.destinations = destinations;
        this.paths = paths;
    }


    public ArrayList<Destination> getDestinations()
    {
        return destinations;
    }


    public ArrayList<Path> getPaths()
    {
        return paths;
    }

}
//i have a VERY VERY VERY big space penis! A tremendous space penis. The best space penis. It's almost Presidential!