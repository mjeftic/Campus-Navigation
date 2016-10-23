package unima.campus_navigation.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
        for(Room room: rooms){
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    @Override
    public Room getRoomByName(String name) {
        List<Room> rooms = getRoomObjects();
        Room result = null;
        for(Room myRoom: rooms){
            if(myRoom.getName().equals(name)){
                result = myRoom;
            }
        }
        Log.d("ProviderService: RoomName: ", result.getName());
        return result;
    }


    public List<Room> generateMockData(){
        List<Room> rooms = new ArrayList<>();
        Room S108 = new Room("S108",49.481534, 8.465205);
        Room S107 = new Room("S107",49.482534, 8.466205);
        Room S106 = new Room("S106",49.482534, 8.464205);
        Room S105 = new Room("S105",49.482534, 8.463205);
        Room S104 = new Room("S104",49.482534, 8.462205);
        Room S103 = new Room("S103",49.484534, 8.465205);
        Room S102 = new Room("S102",49.483534, 8.465205);
        rooms.add(S102);
        rooms.add(S103);
        rooms.add(S104);
        rooms.add(S105);
        rooms.add(S106);
        rooms.add(S107);
        rooms.add(S108);
        return rooms;
    }
    public List<String> getBubbleStrings() {
        List<String> bubbles = generateMockDataBubble();
        return bubbles;
    }



    public List<String> generateMockDataBubble(){
        List<String> bubbles = new ArrayList<>();
        String Bubble1 = "Enter 1st door on the right";
        String Bubble2 = "Go upstairs to the 1st floor";
        String Bubble3 = "Enter door on the left";
        String Bubble4 = "Go straight through the corridor";
        String Bubble5 = "Continue to the end of the corridor";
        String Bubble6 = "Enter the door at the end of the corridor";
        String Bubble7 = "Enter door on the left. YOU REACHED THE LC! ";
        bubbles.add(Bubble1);
        bubbles.add(Bubble2);
        bubbles.add(Bubble3);
        bubbles.add(Bubble4);
        bubbles.add(Bubble5);
        bubbles.add(Bubble6);
        bubbles.add(Bubble7);
        return bubbles;
    }

}
