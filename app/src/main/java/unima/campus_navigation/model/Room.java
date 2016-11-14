package unima.campus_navigation.model;

import java.io.Serializable;

import unima.campus_navigation.R;

/**
 * Created by Marko on 12.10.16.
 */

public class Room implements Serializable{
    private double longitude;
    private double latitude;
    private String name;
    //
    //private static final Integer[] imgsNoStairs = {R.drawable.image3,R.drawable.image4};
    //private static final Integer[] imgsStairs = {R.drawable.image1,R.drawable.image2};
    //


    public Room(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //public Integer[] getImgsNoStairs(){return imgsNoStairs;}

    //public Integer[] getImgsStairs(){return imgsStairs;}

}
