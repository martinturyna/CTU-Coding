package project.home.room;

import project.home.Device.Device;
import project.home.Window.Window;
import java.util.ArrayList;

public class Room {

    private String roomType;
    private ArrayList<Device> devices;
    private ArrayList<Window> windows;

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public void setWindows(ArrayList<Window> windows) {
        this.windows = windows;
    }

    public String getRoomType() {
        return roomType;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }
}
