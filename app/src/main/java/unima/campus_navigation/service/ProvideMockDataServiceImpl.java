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
        return generateMockRoomData();
    }

    @Override
    public List<String> getRoomStrings() {
        List<Room> rooms = generateMockRoomData();
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

    public List<String> getIndoorStrings() {
        List<IndoorNavigation> indoorNavigations = generateIndoorNavigationData();
        List<String> indoorNames = new ArrayList<>();
        for (IndoorNavigation nav : indoorNavigations) {
            indoorNames.add(nav.getTargetRoom().getName());
        }
        return indoorNames;
    }


    public List<Room> generateMockRoomData() {
        List<Room> rooms = new ArrayList<>();
        Room O145 = new Room("O145", 49.483143, 8.464862, 1);
        Room RöchlingHörsaal = new Room("Röchling Hörsaal", 49.483164, 8.46452, 2);
        Room room_sap = new Room("SAP Apphaus", 49.406861, 8.675556, 2);
        Room room_o142 = new Room("O142", 49.482683, 8.463662, 1);
        Room room_o148 = new Room("O145", 49.483122, 8.464966, 1);
        Room room_o151 = new Room("O151", 49.483067, 8.465080, 1);
        Room room_o138 = new Room("O138", 49.482683, 8.463662, 1);
        Room room_o135 = new Room("O135", 49.483295, 8.464502, 1);
        Room room_o133 = new Room("O133", 49.483350, 8.464351, 1);
        Room room_o131 = new Room("O131", 49.483391, 8.464242, 1);
        Room room_o129 = new Room("O129", 49.483439, 8.464123, 1);
        Room room_o128 = new Room("O128", 49.483487, 8.463959, 1);
        Room room_o126 = new Room("O126", 49.483570, 8.463787, 1);
        Room room_sn163 = new Room("Manfred Lautenschläger Hörsaal", 49.483093, 8.464727, 1);



        rooms.add(O145);
        rooms.add(room_o142);
        rooms.add(room_o148);
        rooms.add(room_o151);
        rooms.add(room_o138);
        rooms.add(room_o135);
        rooms.add(room_o133);
        rooms.add(room_o131);
        rooms.add(room_o129);
        rooms.add(room_o128);
        rooms.add(room_o126);
        rooms.add(room_sap);
        rooms.add(RöchlingHörsaal);
        rooms.add(room_sn163);
        return rooms;
    }

    public List<Entrance> generateMockEntranceData() {
        List<Entrance> entrances = new ArrayList<>();
        Entrance haupteingang = new Entrance("Haupteingang", 49.483325, 8.464715);
        Entrance entrance_unibib = new Entrance("Learning Center BWL", 49.482683, 8.463662);
        Entrance entrance_sap = new Entrance("SAP Apphaus", 49.406562, 8.676843);
        entrances.add(haupteingang);
        entrances.add(entrance_unibib);
        entrances.add(entrance_sap);
        return entrances;
    }

    public Entrance getEntranceByName(String name) {
        List<Entrance> list = generateMockEntranceData();
        Entrance result = null;
        for (Entrance entrance : list) {
            if (entrance.getName().equalsIgnoreCase(name)) {
                result = entrance;
            }
        }
        return result;
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

        //Here you add list of images of an indoornavigation
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

        List<Integer> images_sap = new ArrayList<>();
        images_sap.add(R.drawable.sap_0);
        images_sap.add(R.drawable.sap_1);
        images_sap.add(R.drawable.sap_2);
        images_sap.add(R.drawable.sap_3);
        images_sap.add(R.drawable.sap_4);
        images_sap.add(R.drawable.sap_5);

        //Here you add the bubbles of indoor navigation
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

        List<String> bubbles_sap = new ArrayList<>();
        bubbles_sap.add("Entrance");
        bubbles_sap.add("Go up the stairs");
        bubbles_sap.add("It's in the 2nd floor");
        bubbles_sap.add("Open the door");
        bubbles_sap.add("Go left");
        bubbles_sap.add("Welcome to Sap Apphaus Room");


        //Here you create the indoornavigations
        //Carefull: Indoornavigation name and room name must be same
        IndoorNavigation indoornavigation_o145 = new IndoorNavigation("O145", getEntranceByName("Haupteingang"), getRoomByName("O145"),
                                                                      images_o145, bubbles_o145);
        IndoorNavigation indoorNavigation_röchling = new IndoorNavigation("Röchling Hörsaal", getEntranceByName("Haupteingang"),
                                                                          getRoomByName("Röchling Hörsaal"), images_röchling,
                                                                          bubbles_röchling);

        IndoorNavigation indoorNavigation_saphaus = new IndoorNavigation("SAP Apphaus", getEntranceByName("SAP Apphaus"),
                                                                         getRoomByName("SAP Apphaus"), images_sap, bubbles_sap);


        //Finally add all indoornavigations here
        list.add(indoornavigation_o145);
        list.add(indoorNavigation_röchling);
        list.add(indoorNavigation_saphaus);
        return list;
    }


}
