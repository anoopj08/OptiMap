package com.example.anoop.optimap;

import java.util.ArrayList;
/**
 * Created by prashanthkoushik on 1/21/17.
 */

public class Test
{

    private ArrayList<Destination> dests;

    private ArrayList<Path> paths;


    public void testExcute( ArrayList<Destination> a, Destination source)
    {
        dests = a;
        paths = new ArrayList<Path>();
        paths = createPaths(dests);
        Graph graph = new Graph( dests, paths );
        Optrix opalgorithm = new Optrix( graph );
        //opalgorithm.execute( "source" );
        //LinkedList<Destination> path = opalgorithm.getPath( dests.get( 10 ) );

    }

    public ArrayList<Path> createPaths( ArrayList<Destination> dests )
    {
        ArrayList<Path> path = new ArrayList<Path>();
        //will have a global variable called currLoc (or something) that is the user's current location

        for(Destination d : dests)
        {
           // Path p = new Path("likeasomebody",/*currLoc*/, d,0/*maketimecall*/ );
            //path.add( p );
        }
        return path;
    }
}
