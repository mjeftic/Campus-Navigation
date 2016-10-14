package unima.campus_navigation.model;

import java.util.List;

/**
 * Created by Marko on 14.10.16.
 */

public class IndoorNavigation {
    private String        id;
    private Entrance      entrance;
    private Room          targetRoom;
    private List<Integer> imagePaths;
    private List<String>  textBubbles;

    public IndoorNavigation(String id, Entrance entrance, Room targetRoom, List<Integer> imagePaths, List<String> textBubbles) {
        this.id = id;
        this.entrance = entrance;
        this.targetRoom = targetRoom;
        this.imagePaths = imagePaths;
        this.textBubbles = textBubbles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    public Room getTargetRoom() {
        return targetRoom;
    }

    public void setTargetRoom(Room targetRoom) {
        this.targetRoom = targetRoom;
    }

    public List<Integer> getImagePaths() {
        return imagePaths;
    }

    public void setImagePaths(List<Integer> imagePaths) {
        this.imagePaths = imagePaths;
    }

    public List<String> getTextBubbles() {
        return textBubbles;
    }

    public void setTextBubbles(List<String> textBubbles) {
        this.textBubbles = textBubbles;
    }
}
