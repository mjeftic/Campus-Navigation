package unima.campus_navigation.model;

import android.location.Location;

/**
 * Created by Marko on 21.10.16.
 */

public class Event {
    private String id;
    private Location location;
    private String title;
    private String description;
    private int    imagePath;
    private long   timestamp;
    private String url;

    public Event(String id, Location location, String title, String description, int imagePath, long timestamp, String url) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.timestamp = timestamp;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
