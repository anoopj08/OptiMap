package com.example.anoop.optimap;

/**
 * Created by anoop on 1/21/2017.
 */
public class Destination
{

    public String address;

    //public String lati;

    public Destination(String address)//, String longitude)
    {
        this.address = address;
        //this.lati = latitude;
    }

    public String getAddress()
    {
        return address;
    }
    /*
    public String getLong()
    {
        return longi;
    }


    public String getLat()
    {
        return lati;
    }*/
}