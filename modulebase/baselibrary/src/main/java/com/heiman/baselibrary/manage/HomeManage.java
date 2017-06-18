package com.heiman.baselibrary.manage;
/**
 * Copyright ©深圳市海曼科技有限公司
 */


import com.heiman.baselibrary.mode.Home;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2017/2/21 14:16
 * @Description : 家庭管理
 */
public class HomeManage {
    public static ConcurrentHashMap<String, Home> homeMap = new ConcurrentHashMap<String, Home>();

    public static ArrayList<Home> listHome = new ArrayList<Home>();
    private static HomeManage instance;

    public static HomeManage getInstance() {
        if (instance == null) {
            instance = new HomeManage();
        }
        return instance;
    }

    public synchronized ArrayList<Home> getHome() {
        listHome.clear();
        Iterator<Map.Entry<String, Home>> iter = homeMap.entrySet()
                .iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Home> entry = iter.next();
            listHome.add(entry.getValue());
        }
        return listHome;
    }

    public Home getHome(String homeid) {
        return homeMap.get(homeid);
    }

    public void addHome(Home home) {
        Home homes = homeMap.get(home.getId());
        if (homes != null) { // 如果已经保存过设备，就不add
            homeMap.put(home.getId(), home);
            // home.save();
            return;
        }
        homeMap.put(home.getId(), home);

    }

    public void removeHome(String id) {
        homeMap.remove(id);
    }

    public void removeHome(Home home) {
        homeMap.remove(home.getId());
    }

    public void updateHome(Home home) {
        homeMap.remove(home.getId());
        homeMap.put(home.getId(), home);
        // home.save();
    }


}
