package project.home.Floor;


import project.home.room.Room;
import java.util.ArrayList;

public class Floor {
    private int floorNumber;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }
    
    public void addRoom(Room room){
        rooms.add(room);
    }
}
