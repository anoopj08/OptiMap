package com.example.anoop.optimap;

//import android.location.Location;

//import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by prashanthkoushik on 1/21/17.
 */
public class Optrix {
    private final ArrayList<Destination> destinations;

    private ArrayList<Path> paths;

    private ArrayList<Destination> settledNodes;

    private ArrayList<Destination> unSettledNodes;

    private ArrayList<Path> unSettledPaths;

    //private Location mLastLocation;


    public Optrix(Graph graph) {
        // create a copy of the array so that we can operate on this array
        destinations = new ArrayList<Destination>(graph.getDestinations());
        paths = new ArrayList<Path>(graph.getPaths());
    }


    public void execute(Destination source) {
        settledNodes = new ArrayList<Destination>();
        unSettledNodes = new ArrayList<Destination>();
        for (Destination d : destinations) {
            unSettledNodes.add(d);
        }
        while (unSettledNodes.size() > 0) {
            Destination node = getFastest(paths);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            if (unSettledNodes.size() > 0) {
                paths = updatePaths(unSettledNodes);
                source = node;
            }
        }
    }

    private Destination getFastest(ArrayList<Path> paths) {
        //Destination fast = paths.get(0).getDestination();
        Path og = paths.get(0);
        for (Path p : paths) {
            if (p.getTime() < og.getTime()) {
                og = p;
            }
        }
        /*currLoc = og //sets the "current location" to the next waypoint, assuming that the user will have it as their current location once they get there*/
        return og.getDestination();
    }

    public ArrayList<Path> updatePaths(ArrayList<Destination> dests) {
        ArrayList<Path> path = new ArrayList<Path>();
        for (Destination d : dests) {
           // Path p = new Path("likeasomebody",/*currLoc*/, d, 0/*maketimecall*/);
            //path.add(p);
        }
        return path;
    }

   /* public Location getLoc()
    {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
    }*/
}