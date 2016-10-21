package unima.campus_navigation.model;

import android.location.Location;

import java.util.Date;

/**
 * Created by Marko on 21.10.16.
 */

public class Event {
    private Location location;
    private String title;
    private String description;
    private int    imagePath;
    private Date   date;
    private String url;

    public Event(Location location, String title, String description, int imagePath, Date date, String url) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.date = date;
        this.url = url;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
