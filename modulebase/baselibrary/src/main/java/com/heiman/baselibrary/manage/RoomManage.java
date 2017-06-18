package com.heiman.baselibrary.manage;

import com.heiman.baselibrary.mode.Room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author : 肖力
 * @Time :  2017/6/16 15:00
 * @Description :
 * @Modify record :
 */

public class RoomManage {
    public static ConcurrentHashMap<String, Room> homeMap = new ConcurrentHashMap<String, Room>();

    public static ArrayList<Room> listHome = new ArrayList<Room>();
    private static RoomManage instance;

    public static RoomManage getInstance() {
        if (instance == null) {
            instance = new RoomManage();
        }
        return instance;
    }

    public synchronized ArrayList<Room> getHome() {
        listHome.clear();
        Iterator<Map.Entry<String, Room>> iter = homeMap.entrySet()
                .iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Room> entry = iter.next();
            listHome.add(entry.getValue());
        }
        return listHome;
    }

    public Room getHome(String homeid) {
        return homeMap.get(homeid);
    }

    public void addHome(Room room) {
        Room homes = homeMap.get(room.getObjectId());
        if (homes != null) { // 如果已经保存过设备，就不add
            homeMap.put(room.getObjectId(), room);
//             room.save();
            return;
        }
        homeMap.put(room.getObjectId(), room);

    }

    public void removeHome(String id) {
        homeMap.remove(id);
    }

    public void removeHome(Room room) {
        homeMap.remove(room.getObjectId());
    }

    public void updateHome(Room room) {
        homeMap.remove(room.getObjectId());
        homeMap.put(room.getObjectId(), room);
        // Room.save();
    }
}
