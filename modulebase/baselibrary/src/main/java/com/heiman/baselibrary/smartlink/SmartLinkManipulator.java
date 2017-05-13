package com.heiman.network.smarthomesdk.smartlink;/**
 * Created by hp on 2016/12/19.
 */

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.heiman.baselibrary.ModuleInfo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright ©深圳市海曼科技有限公司
 *
 * @Author :    肖力
 * @Email : 554674787@qq.com
 * @Phone : 18312377810
 * @Time :  2016/12/19 13:58
 * @Description : SmartLink类
 */
public class SmartLinkManipulator {
    private String TAG = "HEIMANdebug";
    private String ssid;
    private String pswd;
    private String broadCastIP;
    private String gateWay;
    private static SmartLinkManipulator me = null;
    private Set<String> successMacSet = new HashSet();
    private int HEADER_COUNT = 200;
    private int HEADER_PACKAGE_DELAY_TIME = 10;
    private int HEADER_CAPACITY = 76;
    private ConnectCallBack callback;
    private int CONTENT_COUNT = 5;
    private int CONTENT_PACKAGE_DELAY_TIME = 50;
    private int CONTENT_CHECKSUM_BEFORE_DELAY_TIME = 100;
    private int CONTENT_GROUP_DELAY_TIME = 500;
    private final String RET_KEY = "smart_config";
    private int port = '썏';
    private byte[] receiveByte = new byte[512];
    public static final int DEVICE_COUNT_ONE = 1;
    public static final int DEVICE_COUNT_MULTIPLE = -1;
    public boolean isConnecting = false;
    private InetAddress inetAddressbroadcast;
    private DatagramSocket socket;
    private DatagramPacket packetToSendbroadcast;
    private DatagramPacket packetToSendgateway;
    private DatagramPacket dataPacket;
    private boolean isfinding = false;
    Runnable findThread = new Runnable() {
        public void run() {
            try {
                Thread.sleep(10000L);
            } catch (InterruptedException var4) {
                ;
            }

            for (int i = 0; i < 20 && SmartLinkManipulator.this.isConnecting; ++i) {
                SmartLinkManipulator.this.sendFindCmd();
                if (SmartLinkManipulator.this.isConnecting) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException var3) {
                        ;
                    }
                }
            }

            if (SmartLinkManipulator.this.isConnecting) {
                if (SmartLinkManipulator.this.successMacSet.size() <= 0) {
                    SmartLinkManipulator.this.callback.onConnectTimeOut();
                } else if (SmartLinkManipulator.this.successMacSet.size() > 0) {
                    SmartLinkManipulator.this.callback.onConnectOk();
                }
            }

            Log.e(SmartLinkManipulator.this.TAG, "stop find");
            SmartLinkManipulator.this.isfinding = false;
            SmartLinkManipulator.this.StopConnection();
        }
    };

    private SmartLinkManipulator() {
        this.isConnecting = false;
        this.isfinding = false;
    }

    public static SmartLinkManipulator getInstence() {
        if (me == null) {
            me = new SmartLinkManipulator();
        }

        return me;
    }

    public void setConnection(String ssid, String password, Context ctx) throws SocketException, UnknownHostException {
        Log.e(this.TAG, ssid + ":" + password);
        this.ssid = ssid;
        this.pswd = password;
        this.broadCastIP = this.getBroadcastAddress(ctx);
        this.socket = new DatagramSocket(this.port);
        this.socket.setBroadcast(true);
        this.inetAddressbroadcast = InetAddress.getByName(this.broadCastIP);
    }

    public void Startconnection(ConnectCallBack callback) {
        Log.e(this.TAG, "Startconnection");
        this.callback = callback;
        this.isConnecting = true;
        this.receive();
        this.successMacSet.clear();
        (new Thread(new Runnable() {
            public void run() {
                while (SmartLinkManipulator.this.isConnecting) {
                    SmartLinkManipulator.this.connect();
                }

                Log.e(SmartLinkManipulator.this.TAG, "StopConnet");
                SmartLinkManipulator.this.StopConnection();
            }
        })).start();
        if (!this.isfinding) {
            this.isfinding = true;
            (new Thread(this.findThread)).start();
        }

    }

    public void StopConnection() {
        this.isConnecting = false;
        if (this.socket != null) {
            this.socket.close();
        }

    }

    public String getBroadcastAddress(Context ctx) {
        WifiManager cm = (WifiManager) ctx.getSystemService("wifi");
        DhcpInfo myDhcpInfo = cm.getDhcpInfo();
        if (myDhcpInfo == null) {
            return "255.255.255.255";
        } else {
            int broadcast = myDhcpInfo.ipAddress & myDhcpInfo.netmask | ~myDhcpInfo.netmask;
            byte[] quads = new byte[4];

            for (int e = 0; e < 4; ++e) {
                quads[e] = (byte) (broadcast >> e * 8 & 255);
            }

            try {
                return InetAddress.getByAddress(quads).getHostAddress();
            } catch (Exception var7) {
                return "255.255.255.255";
            }
        }
    }

    private void connect() {
        Log.e(this.TAG, "connect");
        int count = 1;

        for (byte[] header = this.getBytes(this.HEADER_CAPACITY); count <= this.HEADER_COUNT && this.isConnecting; ++count) {
            this.send(header);

            try {
                Thread.sleep((long) this.HEADER_PACKAGE_DELAY_TIME);
            } catch (InterruptedException var15) {
                var15.printStackTrace();
            }
        }

        String pwd = this.pswd;
        int[] content = new int[pwd.length() + 2];
        content[0] = 89;
        int j = 1;

        int checkLength;
        for (checkLength = 0; checkLength < pwd.length(); ++checkLength) {
            content[j] = pwd.charAt(checkLength) + 76;
            ++j;
        }

        content[content.length - 1] = 86;

        for (count = 1; count <= this.CONTENT_COUNT && this.isConnecting; ++count) {
            for (checkLength = 0; checkLength < content.length; ++checkLength) {
                byte t = 1;
                if (checkLength == 0 || checkLength == content.length - 1) {
                    t = 3;
                }

                for (int e = 1; e <= t && this.isConnecting; ++e) {
                    this.send(this.getBytes(content[checkLength]));
                    if (checkLength != content.length) {
                        try {
                            Thread.sleep((long) this.CONTENT_PACKAGE_DELAY_TIME);
                        } catch (InterruptedException var14) {
                            var14.printStackTrace();
                        }
                    }
                }

                if (checkLength != content.length) {
                    try {
                        Thread.sleep((long) this.CONTENT_PACKAGE_DELAY_TIME);
                    } catch (InterruptedException var13) {
                        var13.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep((long) this.CONTENT_CHECKSUM_BEFORE_DELAY_TIME);
            } catch (InterruptedException var12) {
                var12.printStackTrace();
            }

            checkLength = pwd.length() + 256 + 76;

            for (int var16 = 1; var16 <= 3 && this.isConnecting; ++var16) {
                this.send(this.getBytes(checkLength));
                if (var16 < 3) {
                    try {
                        Thread.sleep((long) this.CONTENT_PACKAGE_DELAY_TIME);
                    } catch (InterruptedException var11) {
                        var11.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep((long) this.CONTENT_GROUP_DELAY_TIME);
            } catch (InterruptedException var10) {
                var10.printStackTrace();
            }
        }

        Log.e(this.TAG, "connect END");
    }

    private byte[] getBytes(int capacity) {
        byte[] data = new byte[capacity];

        for (int i = 0; i < capacity; ++i) {
            data[i] = 5;
        }

        return data;
    }

    public char byteToChar(byte[] b) {
        char c = (char) ((b[0] & 255) << 8 | b[1] & 255);
        return c;
    }

    public void sendFindCmd() {
        System.out.println("smartlinkfind");
        this.packetToSendbroadcast = new DatagramPacket("smartlinkfind".getBytes(), "smartlinkfind".getBytes().length, this.inetAddressbroadcast, '뼃');

        try {
            this.socket.send(this.packetToSendbroadcast);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void sendHFA11Cmd() {
        System.out.println("smartlinkfind");
        this.packetToSendbroadcast = new DatagramPacket("HF-A11ASSISTHREAD".getBytes(), "HF-A11ASSISTHREAD".getBytes().length, this.inetAddressbroadcast, '뼃');

        try {
            this.socket.send(this.packetToSendbroadcast);
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public void send(byte[] data) {
        this.packetToSendbroadcast = new DatagramPacket(data, data.length, this.inetAddressbroadcast, this.port);

        try {
            this.socket.send(this.packetToSendbroadcast);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public void receive() {
        Log.e(this.TAG, "start RECV");
        this.dataPacket = new DatagramPacket(this.receiveByte, this.receiveByte.length);
        (new Thread() {
            public void run() {
                while (SmartLinkManipulator.this.isConnecting) {
                    try {
                        SmartLinkManipulator.this.socket.receive(SmartLinkManipulator.this.dataPacket);
                        int e = SmartLinkManipulator.this.dataPacket.getLength();
                        if (e > 0) {
                            String receiveStr = new String(SmartLinkManipulator.this.receiveByte, 0, e, "UTF-8");
                            if (receiveStr.contains("smart_config")) {
                                Log.e("RECV", "smart_config");
                                ModuleInfo mi = new ModuleInfo();
                                mi.setMac(receiveStr.replace("smart_config", "").trim());
                                String ip = SmartLinkManipulator.this.dataPacket.getAddress().getHostAddress();
                                if (ip.equalsIgnoreCase("0.0.0.0") || ip.contains(":")) {
                                    return;
                                }

                                mi.setModuleIP(ip);
                                if (!SmartLinkManipulator.this.successMacSet.contains(mi.getMac())) {
                                    SmartLinkManipulator.this.successMacSet.add(mi.getMac());
                                    SmartLinkManipulator.this.callback.onConnect(mi);
                                }
                            }
                        }
                    } catch (IOException var5) {
                        var5.printStackTrace();
                    }
                }

                Log.e(SmartLinkManipulator.this.TAG, "end RECV");
                SmartLinkManipulator.this.StopConnection();
            }
        }).start();
    }

    public interface ConnectCallBack {
        void onConnect(ModuleInfo var1);

        void onConnectTimeOut();

        void onConnectOk();
    }
}
