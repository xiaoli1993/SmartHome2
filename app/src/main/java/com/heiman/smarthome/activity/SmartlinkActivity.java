package com.heiman.smarthome.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.heiman.baselibrary.BaseActivity;
import com.heiman.smarthome.MyApplication;
import com.heiman.smarthome.R;
import com.heiman.smarthome.smarthomesdk.utils.Utils;
import com.heiman.widget.button.FButton;
import com.heiman.widget.edittext.PasswordEditText;
import com.longthink.api.LTLink;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class SmartlinkActivity extends BaseActivity {
    private static int RECV_PORT = 8001;

    private TextView tvSsid;
    private FButton btnPeizhi;
    private boolean isStart = true;
    private RecvThread mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.activity_smartlink);
//        showContenView(false);
//        showLoadView(true);
        setTitle("SMLINKF");
        tvSsid = (TextView) findViewById(R.id.tv_ssid);
        btnPeizhi = (FButton) findViewById(R.id.btn_peizhi);
        tvSsid.setText(Utils.getSSid(SmartlinkActivity.this) + "");
        btnPeizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStart) {
                    MyApplication.getLogger().i("密码:" + getPwdSmartlink().getText().toString());
                    LTLink.getInstance().startLink(null, getPwdSmartlink().getText().toString(), null, SmartlinkActivity.this);
                    isStart = false;
                } else {
                    LTLink.getInstance().stopLink();
                    isStart = true;
                }
            }
        });
        getPwdSmartlink().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                LTLink.getInstance().startGuidance(SmartlinkActivity.this);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mReceiver = new RecvThread();
        mReceiver.start();
    }

    private PasswordEditText getPwdSmartlink() {
        return (PasswordEditText) findViewById(R.id.pwd_smartlink);
    }

    //收数据
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            MyApplication.getLogger().i("收到数据\\n");
            switch (msg.what) {
                case 3: {
                    DatagramPacket packet = (DatagramPacket) msg.obj;
                    String res = null;
                    try {
                        res = new String(packet.getData(), "UTF-8");
                        MyApplication.getLogger().d("TEST", "IP:" + packet.getAddress().toString() + "MAC:" + res);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

//                    int  offset=tview.getLineCount()*tview.getLineHeight();
//                    if(offset>tview.getHeight()){
//                        tview.scrollTo(0,offset-tview.getHeight());
//                    }
                }
                break;
                default:
//                    DoLink(msg.what);
                    break;
            }
        }
    };

    private class RecvThread extends Thread {
        private boolean threadFlag;
        private DatagramSocket mSocket;

        public RecvThread() {

        }

        public void start() {
            threadFlag = true;

            if (mSocket == null) {
                try {

                    mSocket = new DatagramSocket(RECV_PORT);

                    mSocket.setSoTimeout(1000);

                } catch (SocketException e) {
                    MyApplication.getLogger().e("TEST", e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }
            super.start();
        }

        public void runStop() {
            threadFlag = false;
        }

        public void run() {
            byte[] buf = new byte[1024];

            while (threadFlag) {
                try {

                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    mSocket.receive(packet);
                    MyApplication.getLogger().i("OK：" + packet);
                    mHandler.sendMessage(mHandler.obtainMessage(3, packet));

                } catch (SocketTimeoutException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        }
    }
}
