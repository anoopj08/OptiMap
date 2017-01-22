package com.example.anoop.optimap;

/**
 * Created by anoop on 1/21/2017.
 */
public class Destination
{

    public String longi;

    public String lati;

    public Destination(String latitude, String longitude)
    {
        this.longi = longitude;
        this.lati = latitude;
    }

    public String getLong()
    {
        return longi;
    }


    public String getLat()
    {
        return lati;
    }
}