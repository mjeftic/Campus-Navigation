package unima.campus_navigation.service;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import unima.campus_navigation.R;
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
        Room O145 = new Room("O145",49.483534, 8.465205);
        Room RöchlingHörsaal = new Room("Röchling Hörsaal",49.483534, 8.465205);
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

    public List<String> getBubbleStrings(String room) {
        List<String> bubbles = generateMockDataBubble(room);
        return bubbles;
    }

    public List<String> getBubbleStringsNoStairs(String room) {
        List<String> bubbles = generateMockDataBubbleNoStairs(room);
        return bubbles;
    }



    public List<String> generateMockDataBubble(String room){
        List<String> bubbles = new ArrayList<>();
        if (room.equals("O145")) {
            String Bubble1 = "Entrance";
            String Bubble2 = "Go upstairs to the 1st floor";
            String Bubble3 = "Go upstairs to the 1st floor";
            String Bubble4 = "Go left";
            String Bubble5 = "Go upstairs";
            String Bubble6 = "Go upstairs";
            String Bubble7 = "Enter door on the left.";
            String Bubble8 = "Straight through the corridor";
            String Bubble9 = "Straight through the corridor";
            String Bubble10 = "Search room O145";
            bubbles.add(Bubble1);
            bubbles.add(Bubble2);
            bubbles.add(Bubble3);
            bubbles.add(Bubble4);
            bubbles.add(Bubble5);
            bubbles.add(Bubble6);
            bubbles.add(Bubble7);
            bubbles.add(Bubble8);
            bubbles.add(Bubble9);
            bubbles.add(Bubble10);
            return bubbles;
        }else if(room.equals("Röchling Hörsaal")){
            String Bubble1 = "Entrance";
            String Bubble2 = "Go upstairs to the 1st floor";
            String Bubble3 = "Go upstairs to the 1st floor";
            String Bubble4 = "Turn right";
            String Bubble5 = "Go to the door";
            String Bubble6 = "You reached your room!";
            bubbles.add(Bubble1);
            bubbles.add(Bubble2);
            bubbles.add(Bubble3);
            bubbles.add(Bubble4);
            bubbles.add(Bubble5);
            bubbles.add(Bubble6);
            return bubbles;
        }else{
            return bubbles;
        }
    }

    public List<String> generateMockDataBubbleNoStairs(String room){
        List<String> bubbles = new ArrayList<>();
        if (room.equals("O145")) {
            String Bubble1 = "No avoiding stairs path available.";
            bubbles.add(Bubble1);
            return bubbles;
        }else{
            String Bubble1 = "No avoiding stairs path available.";
            bubbles.add(Bubble1);
            return bubbles;
        }
    }

    public Integer[] generateMockDataImages(String room){
        if (room.equals("O145")) {
            Integer pic0 = R.drawable.o145_pic0;
            Integer pic1 = R.drawable.o145_pic1;
            Integer pic2 = R.drawable.o145_pic2;
            Integer pic3 = R.drawable.o145_pic3;
            Integer pic4 = R.drawable.o145_pic4;
            Integer pic5 = R.drawable.o145_pic5;
            Integer pic6 = R.drawable.o145_pic6;
            Integer pic7 = R.drawable.o145_pic7;
            Integer pic8 = R.drawable.o145_pic8;
            Integer pic9 = R.drawable.o145_pic9;
            Integer[] IMAGES = {pic0, pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8, pic9};
            return IMAGES;
        }else if (room.equals("Röchling Hörsaal")){
            Integer pic0 = R.drawable.h_pic0;
            Integer pic1 = R.drawable.h_pic1;
            Integer pic2 = R.drawable.h_pic2;
            Integer pic3 = R.drawable.h_pic3;
            Integer pic4 = R.drawable.h_pic4;
            Integer pic5 = R.drawable.h_pic5;
            Integer[] IMAGES = {pic0, pic1, pic2, pic3, pic4, pic5};
            return IMAGES;
        }else{
            Integer[] IMAGES = {0};
            return IMAGES;
        }
    }

    public Integer[] generateMockDataImagesNoStairs (String room){
        if (room.equals("O145")){
            Integer pic0 = R.drawable.o145_pic0;
            Integer[] IMAGES = {pic0};
            return IMAGES;
        }else{
            Integer pic0 = R.drawable.o145_pic0;
            Integer[] IMAGES = {pic0};
            return IMAGES;
        }
    }


}
