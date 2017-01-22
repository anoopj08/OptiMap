package com.example.anoop.optimap;
import java.util.Date;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anoop on 1/21/2017.
 */

public class PostModel {

    @SerializedName("duration_in_traffic")
    long duration_in_traffic;

    @SerializedName("duration")
    long duration;

    @SerializedName("distance")
    long distance;

}
