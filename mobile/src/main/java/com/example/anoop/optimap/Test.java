package com.example.anoop.optimap;

import android.location.Location;

import java.util.ArrayList;


/**
 * Created by prashanthkoushik on 1/21/17.
 */

public class Test extends MapsActivity
{
    public ArrayList<Destination> dests;

    public ArrayList<Path> paths;

    public void testExecute( ArrayList<Destination> a, Destination source)
    {
        dests = a;
        paths = new ArrayList<Path>();
        paths = createPaths(dests);
        Graph graph = new Graph( dests, paths );
        Optrix opalgorithm = new Optrix( graph );
        opalgorithm.execute();
        //LinkedList<Destination> path = opalgorithm.getPath( dests.get( 10 ) );

    }

    public ArrayList<Path> createPaths( ArrayList<Destination> dests )
    {
        ArrayList<Path> path = new ArrayList<Path>();

        for(Destination d : dests)
        {
           Path p = new Path(getCurrDest(), d,0/*maketimecall*/ );
            //path.add( p );
        }
        return path;
    }
}
