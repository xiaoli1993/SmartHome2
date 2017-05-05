package com.heiman.smarthome.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import com.heiman.smarthome.R;
import com.heiman.baselibrary.back.ViewCommunitor;
import com.longthink.api.LTLink;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * @Author : 肖力
 * @Time :  2017/5/3 15:14
 * @Description :
 * @Modify record :
 */

public class LTLinkActivity extends ActionBarActivity implements ViewCommunitor {
    private static int RECV_PORT = 8001;
    LTLink	 mLink;
    private RecvThread mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltlink);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment()).commit();
        }

        mLink = LTLink.getInstance();
        mLink.setDebugStatue(true);
        mLink.setDelay(15, 100);//ms

        mReceiver = new RecvThread();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void DoLink(int type){
        if (type == 1) {
            Log.d("TEST", "Start G");
            Fragment fragment = (getSupportFragmentManager().findFragmentById(R.id.container)); // 获取FragmentA的视图
            TextView tview = (TextView)fragment.getView().findViewById(R.id.textViewMsg) ;
            tview.setText("发送前导\n");
            Chronometer timer = (Chronometer)fragment.getView().findViewById(R.id.chronometer);
            // 将计时器清零
            timer.setBase(SystemClock.elapsedRealtime());
            //开始计时
            timer.start();
            mLink.startGuidance(LTLinkActivity.this);
        }
        else if (type == 0) {
            Log.d("TEST", "Stop");
            Fragment fragment = (getSupportFragmentManager().findFragmentById(R.id.container)); // 获取FragmentA的视图
            TextView tview = (TextView)fragment.getView().findViewById(R.id.textViewMsg) ;
            tview.setText("停止发送\n");
            Chronometer timer = (Chronometer)fragment.getView().findViewById(R.id.chronometer);
            timer.stop();
            mLink.stopLink();
            mReceiver.runStop();
        }
        else if (type == 2)
        {
            Log.d("TEST", "Start S");
            Fragment fragment = (getSupportFragmentManager().findFragmentById(R.id.container)); // 获取FragmentA的视图
            EditText editTextPW = (EditText)fragment.getView().findViewById(R.id.editTextPW) ;
            EditText editTextKey = (EditText)fragment.getView().findViewById(R.id.editTextKey) ;

            Chronometer timer = (Chronometer)fragment.getView().findViewById(R.id.chronometer);
            // 将计时器清零
            timer.setBase(SystemClock.elapsedRealtime());
            //开始计时
            timer.start();
            String password = editTextPW.getText().toString();
            String key = editTextKey.getText().toString();
            if (key.length() == 0)
                key = null;
            //mLink.startLink(password, key, MainActivity.this);
            mLink.startLink(null, password, key, LTLinkActivity.this);
            switch (mReceiver.getState()) {
                case TERMINATED:
                    mReceiver = new RecvThread();
                case NEW: {
                    mReceiver.start();
                    break;
                }
            }
        }
    }

    @Override
    public void onViewMessage(int type) {
        //
        mHandler.sendEmptyMessage(type);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        TextView textViewMsg;

        private ViewCommunitor sendMessage;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);

            textViewMsg = (TextView)rootView.findViewById(R.id.textViewMsg);
            textViewMsg.setText("Test");

            EditText editTextPW = (EditText)rootView.findViewById(R.id.editTextPW);
            editTextPW.setHint("Password");
            EditText editTextKey = (EditText)rootView.findViewById(R.id.editTextKey);
            editTextKey.setHint("key");

            Button btnStartG = (Button)rootView.findViewById(R.id.buttonStartG);
            btnStartG.setOnClickListener(new BtnStartGListener());

            Button btnStartS = (Button)rootView.findViewById(R.id.buttonStartS);
            btnStartS.setOnClickListener(new BtnStartSListener());

            Button btnStop = (Button)rootView.findViewById(R.id.buttonStop);
            btnStop.setOnClickListener(new BtnStopListener());

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            sendMessage = (ViewCommunitor) activity;
        }

        //创建监听器
        class BtnStartGListener implements  View.OnClickListener{
            public void onClick(View v){
                Log.d("TEST", "Button Start G");
                sendMessage.onViewMessage(1);
            }
        }

        class BtnStartSListener implements  View.OnClickListener{
            public void onClick(View v){
                Log.d("TEST", "Button Start S");
                sendMessage.onViewMessage(2);
            }
        }

        class BtnStopListener implements  View.OnClickListener{
            public void onClick(View v){
                Log.d("TEST", "Button Stop");
                sendMessage.onViewMessage(0);
            }
        }

    }

    //收数据
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Fragment fragment = (getSupportFragmentManager().findFragmentById(R.id.container)); // 获取FragmentA的视图
            TextView tview = (TextView)fragment.getView().findViewById(R.id.textViewMsg) ;
            tview.setText("收到数据\n");
            switch(msg.what) {
                case 3: {
                    DatagramPacket packet = (DatagramPacket)msg.obj;
                    tview.append(packet.getAddress().toString());
                    tview.append(packet.getData().toString());
                    Log.d("TEST", "IP:"+ packet.getAddress().toString());
                    int  offset=tview.getLineCount()*tview.getLineHeight();
                    if(offset>tview.getHeight()){
                        tview.scrollTo(0,offset-tview.getHeight());
                    }
                }
                break;
                default:
                    DoLink(msg.what);
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

            if(mSocket == null) {
                try {

                    mSocket = new DatagramSocket(RECV_PORT);

                    mSocket.setSoTimeout(1000);

                } catch (SocketException e) {
                    Log.e("TEST", e.getMessage());
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

                    mHandler.sendMessage(mHandler.obtainMessage(3, packet));

                } catch (SocketTimeoutException e) {

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        }
    }
}

