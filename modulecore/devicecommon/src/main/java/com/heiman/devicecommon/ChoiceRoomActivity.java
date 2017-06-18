package com.heiman.devicecommon;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heiman.baselibrary.BaseApplication;
import com.heiman.baselibrary.GwBaseActivity;
import com.heiman.baselibrary.manage.RoomManage;
import com.heiman.baselibrary.mode.Room;
import com.heiman.devicecommon.adapter.StringTagAdapter;
import com.heiman.widget.flexbox.interfaces.OnFlexboxSubscribeListener;
import com.heiman.widget.flexbox.widget.TagFlowLayout;

import java.util.List;

/**
 * @Author : 肖力
 * @Time :  2017/6/17 9:48
 * @Description :
 * @Modify record :
 */

public class ChoiceRoomActivity extends GwBaseActivity {

    private ImageView imageIsSuccess;
    private TextView tvIsSuccess;
    private TagFlowLayout flowLayout;
    private StringTagAdapter adapter;

    private List<Room> sourceData;

    //    private List<String> selectItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devicecommon_activity_choice_room);
        initData();
        initViews();

    }

    private void initData() {
        sourceData = RoomManage.getInstance().getHome();
    }

    private void initViews() {
        imageIsSuccess = (ImageView) findViewById(R.id.image_isSuccess);
        tvIsSuccess = (TextView) findViewById(R.id.tv_isSuccess);
        flowLayout = (TagFlowLayout) findViewById(R.id.flow_layout);
        adapter = new StringTagAdapter(this, sourceData);
        adapter.setOnSubscribeListener(new OnFlexboxSubscribeListener<Room>() {
            @Override
            public void onSubscribe(List<Room> selectedItem) {
                BaseApplication.getLogger().e("已选择" + selectedItem.size() + "个");
            }
        });
        adapter.getSelectedList();
        flowLayout.setMode(TagFlowLayout.MODE_SINGLE_SELECT);
        flowLayout.setAdapter(adapter);
        BaseApplication.getLogger().e("已选择" + adapter.getSelectedList().size() + "个");
    }

    @Override
    public void onClickListener(View v) {

    }

    @Override
    public void deviceData(String dataString) {

    }
}
