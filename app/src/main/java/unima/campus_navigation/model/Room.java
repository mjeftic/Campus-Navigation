package unima.campus_navigation.model;

/**
 * Created by Marko on 12.10.16.
 */

public class Room {
    private double longitude;
    private double latitude;
    private String name;
    private int floor;


    public Room(String name, double longitude, double latitude, int floor) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.floor = floor;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
