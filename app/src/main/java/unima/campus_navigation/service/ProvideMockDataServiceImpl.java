package unima.campus_navigation.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;
import unima.campus_navigation.model.Entrance;
import unima.campus_navigation.model.IndoorNavigation;
import unima.campus_navigation.model.Room;

/**
 * Created by Marko on 15.10.16.
 */

public class ProvideMockDataServiceImpl implements ProvideMockDataService {

    @Override
    public List<Room> getRoomObjects() {
        return generateMockData();
    }

    @Override
    public List<String> getRoomStrings() {
        List<Room> rooms = generateMockData();
        List<String> roomNames = new ArrayList<>();
        for (Room room : rooms) {
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    @Override
    public Room getRoomByName(String name) {
        List<Room> rooms = getRoomObjects();
        Room result = null;
        for (Room myRoom : rooms) {
            if (myRoom.getName().equals(name)) {
                result = myRoom;
            }
        }
        Log.d("ProviderService: RoomName: ", result.getName());
        return result;
    }


    public List<Room> generateMockData() {
        List<Room> rooms = new ArrayList<>();
        Room S108 = new Room("S108", 49.481534, 8.465205, 1);
        Room S107 = new Room("S107", 49.482534, 8.466205, 1);
        Room S106 = new Room("S106", 49.482534, 8.464205, 0);
        Room S105 = new Room("S105", 49.482534, 8.463205, 0);
        Room S104 = new Room("S104", 49.482534, 8.462205, 0);
        Room S103 = new Room("S103", 49.484534, 8.465205, 1);
        Room S102 = new Room("S102", 49.483534, 8.465205, 5);


        Room O145 = new Room("O145", 49.483534, 8.465205, 2);
        Room RöchlingHörsaal = new Room("Röchling Hörsaal", 49.483534, 8.465205, 2);
        rooms.add(S102);
        rooms.add(S103);
        rooms.add(S104);
        rooms.add(S105);
        rooms.add(S106);
        rooms.add(S107);
        rooms.add(S108);
        rooms.add(O145);
        rooms.add(RöchlingHörsaal);
        return rooms;
    }

    public IndoorNavigation getIndoornavigationByRoom(String room) {
        List<IndoorNavigation> list = generateIndoorNavigationData();
        IndoorNavigation result = null;
        for (IndoorNavigation indoorNavigation : list) {
            if (indoorNavigation.getTargetRoom().getName().equals(room)) {
                result = indoorNavigation;
            }
        }
        return result;
    }

    public List<IndoorNavigation> generateIndoorNavigationData() {
        List<IndoorNavigation> list = new ArrayList<>();

        Entrance haupteingang = new Entrance("Haupteingang", 49.482534, 8.464205);

        Room room_o145 = new Room("O145", 49.483534, 8.464235, 3);
        Room room_röchling = new Room("Röchling Hörsaal", 49.483534, 8.464235, 1);

        List<Integer> images_o145 = new ArrayList<>();
        images_o145.add(R.drawable.o145_pic0);
        images_o145.add(R.drawable.o145_pic1);
        images_o145.add(R.drawable.o145_pic2);
        images_o145.add(R.drawable.o145_pic3);
        images_o145.add(R.drawable.o145_pic4);
        images_o145.add(R.drawable.o145_pic5);
        images_o145.add(R.drawable.o145_pic6);
        images_o145.add(R.drawable.o145_pic7);
        images_o145.add(R.drawable.o145_pic8);
        images_o145.add(R.drawable.o145_pic9);

        List<Integer> images_röchling = new ArrayList<>();
        images_röchling.add(R.drawable.h_pic0);
        images_röchling.add(R.drawable.h_pic1);
        images_röchling.add(R.drawable.h_pic2);
        images_röchling.add(R.drawable.h_pic3);
        images_röchling.add(R.drawable.h_pic4);
        images_röchling.add(R.drawable.h_pic5);

        List<String> bubbles_o145 = new ArrayList<>();
        bubbles_o145.add("Entrance");
        bubbles_o145.add("Go upstairs to the 1st floor");
        bubbles_o145.add("Go upstairs to the 1st floor");
        bubbles_o145.add("Go left");
        bubbles_o145.add("Go upstairs");
        bubbles_o145.add("Go upstairs");
        bubbles_o145.add("Enter door on the left.");
        bubbles_o145.add("Straight through the corridor");
        bubbles_o145.add("Straight through the corridor");
        bubbles_o145.add("Search room O145");

        List<String> bubbles_röchling = new ArrayList<>();
        bubbles_röchling.add("Entrance");
        bubbles_röchling.add("Go upstairs to the 1st floor");
        bubbles_röchling.add("Go upstairs to the 1st floor");
        bubbles_röchling.add("Turn right");
        bubbles_röchling.add("Go to the door");
        bubbles_röchling.add("You reached your room!");


        IndoorNavigation indoornavigation_o145 = new IndoorNavigation("O145", haupteingang, room_o145, images_o145, bubbles_o145);
        IndoorNavigation indoorNavigation_röchling = new IndoorNavigation("Röchling Hörsaal",haupteingang,room_röchling, images_röchling, bubbles_röchling);


        list.add(indoornavigation_o145);
        list.add(indoorNavigation_röchling);
        return list;
    }




}
