package com.heiman.gateway;

import android.os.Bundle;
import android.view.View;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.Json.HeimanCom;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/5/9 13:57
 * @Description :
 * @Modify record :
 */

public class GwActivity extends GwBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gateway_activity_gw);

        List<String> OID = new ArrayList<String>();
        OID.add(HeimanCom.COM_GW_OID.GW_NAME);
        OID.add(HeimanCom.COM_GW_OID.DEVICE_BASIC_INFORMATION);
        String sb = HeimanCom.getOID(BaseApplication.getMyApplication().getUserInfo().getNickname(), BaseApplication.getMyApplication().getUserInfo().getId() + "", 1, OID);
        sendData(sb);
    }

    @Override
    public void onClickListener(View v) {

    }

    @Override
    public void deviceData(String dataString) {

    }
}
