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

        List<Integer> images_o151 = new ArrayList<>();
        images_o151.add(R.drawable.o151_1);
        images_o151.add(R.drawable.o151_2);
        images_o151.add(R.drawable.o151_3);
        images_o151.add(R.drawable.o151_4);
        images_o151.add(R.drawable.o151_5);
        images_o151.add(R.drawable.o151_6);
        images_o151.add(R.drawable.o151_7);
        images_o151.add(R.drawable.o151_8);
        images_o151.add(R.drawable.o151_9);
        images_o151.add(R.drawable.o151_10);
        images_o151.add(R.drawable.o151_11);

        List<Integer> images_o148 = new ArrayList<>();
        images_o148.add(R.drawable.o148_1);
        images_o148.add(R.drawable.o148_2);
        images_o148.add(R.drawable.o148_3);
        images_o148.add(R.drawable.o148_4);
        images_o148.add(R.drawable.o148_5);
        images_o148.add(R.drawable.o148_6);
        images_o148.add(R.drawable.o148_7);
        images_o148.add(R.drawable.o148_8);
        images_o148.add(R.drawable.o148_9);
        images_o148.add(R.drawable.o148_10);

        List<Integer> images_o142 = new ArrayList<>();
        images_o142.add(R.drawable.o142_1);
        images_o142.add(R.drawable.o142_2);
        images_o142.add(R.drawable.o142_3);
        images_o142.add(R.drawable.o142_4);
        images_o142.add(R.drawable.o142_5);
        images_o142.add(R.drawable.o142_6);
        images_o142.add(R.drawable.o142_7);
        images_o142.add(R.drawable.o142_8);
        images_o142.add(R.drawable.o142_9);

        List<Integer> images_o138 = new ArrayList<>();
        images_o138.add(R.drawable.o138_1);
        images_o138.add(R.drawable.o138_2);
        images_o138.add(R.drawable.o138_3);
        images_o138.add(R.drawable.o138_4);
        images_o138.add(R.drawable.o138_5);
        images_o138.add(R.drawable.o138_6);
        images_o138.add(R.drawable.o138_7);


        List<Integer> images_o135 = new ArrayList<>();
        images_o135.add(R.drawable.o135_1);
        images_o135.add(R.drawable.o135_2);
        images_o135.add(R.drawable.o135_3);
        images_o135.add(R.drawable.o135_4);
        images_o135.add(R.drawable.o135_5);
        images_o135.add(R.drawable.o135_6);
        images_o135.add(R.drawable.o135_7);
        images_o135.add(R.drawable.o135_8);


        List<Integer> images_o133 = new ArrayList<>();
        images_o133.add(R.drawable.o133_1);
        images_o133.add(R.drawable.o133_2);
        images_o133.add(R.drawable.o133_3);
        images_o133.add(R.drawable.o133_4);
        images_o133.add(R.drawable.o133_5);
        images_o133.add(R.drawable.o133_6);
        images_o133.add(R.drawable.o133_7);
        images_o133.add(R.drawable.o133_8);
        images_o133.add(R.drawable.o133_9);

        List<Integer> images_o131 = new ArrayList<>();
        images_o131.add(R.drawable.o131_1);
        images_o131.add(R.drawable.o131_2);
        images_o131.add(R.drawable.o131_3);
        images_o131.add(R.drawable.o131_4);
        images_o131.add(R.drawable.o131_5);
        images_o131.add(R.drawable.o131_6);
        images_o131.add(R.drawable.o131_7);
        images_o131.add(R.drawable.o131_8);
        images_o131.add(R.drawable.o131_9);
        images_o131.add(R.drawable.o131_10);
        images_o131.add(R.drawable.o131_11);

        List<Integer> images_o129 = new ArrayList<>();
        images_o129.add(R.drawable.o129_1);
        images_o129.add(R.drawable.o129_2);
        images_o129.add(R.drawable.o129_3);
        images_o129.add(R.drawable.o129_4);
        images_o129.add(R.drawable.o129_5);
        images_o129.add(R.drawable.o129_6);
        images_o129.add(R.drawable.o129_7);
        images_o129.add(R.drawable.o129_8);
        images_o129.add(R.drawable.o129_9);
        images_o129.add(R.drawable.o129_10);
        images_o129.add(R.drawable.o129_11);
        images_o129.add(R.drawable.o129_12);

        List<Integer> images_o128 = new ArrayList<>();
        images_o128.add(R.drawable.o128_1);
        images_o128.add(R.drawable.o128_2);
        images_o128.add(R.drawable.o128_3);
        images_o128.add(R.drawable.o128_4);
        images_o128.add(R.drawable.o128_5);
        images_o128.add(R.drawable.o128_6);
        images_o128.add(R.drawable.o128_7);
        images_o128.add(R.drawable.o128_8);
        images_o128.add(R.drawable.o128_9);
        images_o128.add(R.drawable.o128_10);
        images_o128.add(R.drawable.o128_11);
        images_o128.add(R.drawable.o128_12);
        images_o128.add(R.drawable.o128_13);

        List<Integer> images_o126 = new ArrayList<>();
        images_o126.add(R.drawable.o126_1);
        images_o126.add(R.drawable.o126_2);
        images_o126.add(R.drawable.o126_3);
        images_o126.add(R.drawable.o126_4);
        images_o126.add(R.drawable.o126_5);
        images_o126.add(R.drawable.o126_6);
        images_o126.add(R.drawable.o126_7);
        images_o126.add(R.drawable.o126_8);
        images_o126.add(R.drawable.o126_9);
        images_o126.add(R.drawable.o126_10);
        images_o126.add(R.drawable.o126_11);
        images_o126.add(R.drawable.o126_12);
        images_o126.add(R.drawable.o126_13);

        List<Integer> images_röchling = new ArrayList<>();
        images_röchling.add(R.drawable.h_pic0);
        images_röchling.add(R.drawable.h_pic1);
        images_röchling.add(R.drawable.h_pic2);
        images_röchling.add(R.drawable.h_pic3);
        images_röchling.add(R.drawable.h_pic4);
        images_röchling.add(R.drawable.h_pic5);

        List<Integer> images_manfred = new ArrayList<>();
        images_manfred.add(R.drawable.sn163_1);
        images_manfred.add(R.drawable.sn163_2);
        images_manfred.add(R.drawable.sn163_3);
        images_manfred.add(R.drawable.sn163_4);
        images_manfred.add(R.drawable.sn163_5);



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

        List<String> bubbles_manfred = new ArrayList<>();
        bubbles_manfred.add("Go to the stairs");
        bubbles_manfred.add("Go upstairs to the 1st floor");
        bubbles_manfred.add("Go left");
        bubbles_manfred.add("Go to the door");
        bubbles_manfred.add("You have arrived to the room!");

        List<String> bubbles_o151 = new ArrayList<>();
        bubbles_o151.add("Go to the stairs");
        bubbles_o151.add("Go upstairs to the 1st floor");
        bubbles_o151.add("Go left");
        bubbles_o151.add("Go upstairs");
        bubbles_o151.add("Enter door on the right");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("You have arrived to the room!");

        List<String> bubbles_o138 = new ArrayList<>();
        bubbles_o138.add("Go to the stairs");
        bubbles_o138.add("Go upstairs to the 1st floor");
        bubbles_o138.add("Go left");
        bubbles_o138.add("Go upstairs");
        bubbles_o138.add("Turn left");
        bubbles_o138.add("Go straight through the corridor");
        bubbles_o138.add("You have arrived to the room!");

        List<String> bubbles_o135 = new ArrayList<>();
        bubbles_o135.add("Go to the stairs");
        bubbles_o135.add("Go upstairs to the 1st floor");
        bubbles_o135.add("Go right");
        bubbles_o135.add("Go upstairs");
        bubbles_o151.add("Go left");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("Go straight through the corridor");
        bubbles_o151.add("You have arrived to the room!");

        //Here you create the indoornavigations
        //Carefull: Indoornavigation name and room name must be same
        IndoorNavigation indoornavigation_o145 = new IndoorNavigation("O145", getEntranceByName("Haupteingang"), getRoomByName("O145"),
                                                                      images_o145, bubbles_o145);
        IndoorNavigation indoorNavigation_röchling = new IndoorNavigation("Röchling Hörsaal", getEntranceByName("Haupteingang"),
                                                                          getRoomByName("Röchling Hörsaal"), images_röchling,
                                                                          bubbles_röchling);

        IndoorNavigation indoorNavigation_saphaus = new IndoorNavigation("SAP Apphaus", getEntranceByName("SAP Apphaus"),
                                                                         getRoomByName("SAP Apphaus"), images_sap, bubbles_sap);

        IndoorNavigation indoorNavigation_manfred = new IndoorNavigation("Manfred Lautenschläger Hörsaal", getEntranceByName("Haupteingang"), getRoomByName("Manfred Lautenschläger Hörsaal"),images_manfred,bubbles_manfred);

        IndoorNavigation indoorNavigation_o151 = new IndoorNavigation("O151",getEntranceByName("Haupteingang"), getRoomByName("O151"),images_o151,bubbles_o151);
        IndoorNavigation indoorNavigation_o135 = new IndoorNavigation("O135",getEntranceByName("Haupteingang"), getRoomByName("O135"),images_o151,bubbles_o135);
        IndoorNavigation indoorNavigation_o138 = new IndoorNavigation("O138",getEntranceByName("Haupteingang"), getRoomByName("O138"),images_o151,bubbles_o138);
        IndoorNavigation indoorNavigation_o155 = new IndoorNavigation("O145",getEntranceByName("Haupteingang"), getRoomByName("O145"),images_o145,bubbles_o145);

        //Finally add all indoornavigations here
        list.add(indoornavigation_o145);
        list.add(indoorNavigation_röchling);
        list.add(indoorNavigation_saphaus);
        list.add(indoorNavigation_manfred);
        list.add(indoorNavigation_o151);
        list.add(indoorNavigation_o135);
        list.add(indoorNavigation_o138);
        list.add(indoorNavigation_o155);
        return list;
    }


}
