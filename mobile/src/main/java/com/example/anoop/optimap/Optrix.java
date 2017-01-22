package com.example.anoop.optimap;

import java.util.ArrayList;


/**
 * Created by prashanthkoushik on 1/21/17.
 */
public class Optrix extends MapsActivity{

    public  ArrayList<Destination> destinations;

    public ArrayList<Path> paths;

    public ArrayList<Destination> fixedNodes;

    public ArrayList<Destination> unFixedNodes;

    public Optrix(Graph graph)
    {
        destinations = new ArrayList<Destination>(graph.getDestinations());
        paths = new ArrayList<Path>(graph.getPaths());
    }


    public ArrayList<Destination> execute() {
        fixedNodes = new ArrayList<Destination>();
        unFixedNodes = new ArrayList<Destination>();
        for (Destination d : destinations) {
            unFixedNodes.add(d);
        }
        while (unFixedNodes.size() > 0)
        {
            Destination node = getFastest(paths);
            fixedNodes.add(node);
            unFixedNodes.remove(node);
            if (unFixedNodes.size() > 0)
            {
                paths = updatePaths(unFixedNodes);
            }
        }
        return fixedNodes;
    }

    private Destination getFastest(ArrayList<Path> paths)
    {
        Path og = paths.get(0);
        for (Path p : paths) {
            if (p.getTime() < og.getTime()) {
                og = p;
            }
        }
        return og.getDestination();
    }

    public ArrayList<Path> updatePaths(ArrayList<Destination> dests) {
        ArrayList<Path> path = new ArrayList<Path>();
        for (Destination d : dests) {
           Path p = new Path(getCurrDest(), d, 0/*maketimecall*/);
            //path.add(p);
        }
        return path;
    }
}