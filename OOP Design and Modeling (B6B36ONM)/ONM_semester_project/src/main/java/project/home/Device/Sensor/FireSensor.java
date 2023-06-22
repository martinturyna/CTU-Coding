package project.home.Device.Sensor;

import project.home.Floor.Floor;
import project.home.room.Room;

import java.util.ArrayList;
import java.util.Random;

/**
 * FireSensor try to set up fire every cycle with very small
 * probability. If it happens, choose random room, where the fire
 * will start.
 */

public class FireSensor {

    ArrayList<Room> rooms = new ArrayList<Room>();
    String room = "null";

    public FireSensor(ArrayList<Floor> floors) {
        for (Floor f: floors) {
            rooms.addAll(f.getRooms());
        }

    }

    /**
     * Method that randomly generate fire in the particular room
     * The probability is 1:10000. Fire is set up in case of
     * devils number 666. Creepy, isn't it?
     */
    public boolean generateFire(){
        Random rn = new Random();
        int fire = rn.nextInt(10000);
        if (fire == 666) {
            room = rooms.get(rn.nextInt(rooms.size())).getRoomType();
            System.out.println("FIREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            return true;
        }
        return false;
    }

    public String getRoom() {
        return room;
    }


}
