package unima.campus_navigation.service;

import java.util.List;

import unima.campus_navigation.model.Room;

/**
 * Created by Marko on 15.10.16.
 */

public interface ProvideMockDataService {
    /**
     * This method provides you all Room object from our generated mock data.
     * @return
     */
    List<Room> getRoomObjects();

    /**
     * This method provides you all Roomnames as String format from our generated mock data.
     * @return
     */
    List<String> getRoomStrings();

    /**
     * To get a specific room by its name you need to provide the search result string
     * to that method. The outcome is a room object of that room if the string was correct.
     * Else you get null as response
     * @param name
     * @return
     */
    Room getRoomByName(String name);

}
