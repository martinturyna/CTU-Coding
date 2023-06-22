package project.home.room;


import project.home.Device.*;
import project.home.Window.Window;

import java.util.ArrayList;

public class RoomCreator {

    public Room createKitchen(){
        Room kitchen = new Room();
        kitchen.setRoomType("Kitchen");

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new Cooker(kitchen));
        devices.add(new Fridge(kitchen));
        devices.add(new Freezer(kitchen));
        devices.add(new Microwave(kitchen));
        kitchen.setDevices(devices);

        ArrayList<Window> windows = new ArrayList<Window>();
        windows.add(new Window());
        kitchen.setWindows(windows);
        return kitchen;
    }

    public Room createLivingRoom(){
        Room livingRoom = new Room();
        livingRoom.setRoomType("Living Room");

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new Television(livingRoom));
        livingRoom.setDevices(devices);
        
        ArrayList<Window> windows = new ArrayList<Window>();
        windows.add(new Window());
        livingRoom.setWindows(windows);
        return livingRoom;
    }

    public Room createBathroom(){
        Room bathroom = new Room();
        bathroom.setRoomType("Bathroom");

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new WashingMachine(bathroom));
        devices.add(new VacuumCleaner(bathroom));
        bathroom.setDevices(devices);

        ArrayList<Window> windows = new ArrayList<Window>();
        bathroom.setWindows(windows);
        return bathroom;
    }

    public Room createBedroom(){
        Room bedroom = new Room();
        bedroom.setRoomType("Bedroom");

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new AudioPlayer(bedroom));
        bedroom.setDevices(devices);

        ArrayList<Window> windows = new ArrayList<Window>();
        windows.add(new Window()); windows.add(new Window());
        bedroom.setWindows(windows);
        return bedroom;
    }

    public Room createWorkroom(){
        Room workroom = new Room();
        workroom.setRoomType("Workroom");

        ArrayList<Device> devices = new ArrayList<Device>();
        devices.add(new AudioPlayer(workroom));
        devices.add(new Computer(workroom));
        workroom.setDevices(devices);

        ArrayList<Window> windows = new ArrayList<Window>();
        windows.add(new Window());
        workroom.setWindows(windows);
        return workroom;
    }
}
